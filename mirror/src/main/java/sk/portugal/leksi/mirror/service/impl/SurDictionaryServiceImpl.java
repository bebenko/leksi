package sk.portugal.leksi.mirror.service.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.util.helper.EscapeHelper;
import sk.portugal.leksi.mirror.service.DictionaryService;
import sk.portugal.leksi.model.Meaning;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.util.helper.VariantHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
public class SurDictionaryServiceImpl implements DictionaryService {

    public static final String PREFIX = "KSK ";

    private JdbcTemplate jdbcTemplate = null;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Word> getAllPTWords() {
        List<Word> result = null;

        String sql = "SELECT portugal, popis, popis2, slovak1, skratka1, skratka1b, slovak2, skratka2, skratka2b, " +
                "slovak3, skratka3, skratka3b, slovak4, skratka4, skratka4b, slovak5, skratka5, skratka5b, " +
                "slovak6, skratka6, skratka6b, slovak7, skratka7, skratka7b, slovak8, skratka8, skratka8b, " +
                "slovak9, skratka9, skratka9b, slovak10, skratka10, skratka10b, tvar " +
                "FROM ";

        RowMapper<Word> rowMapper = new RowMapper<Word>() {
            public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
                Word tran = new Word();
                //tran.setId(rs.getInt("id"));
                tran.setLang(Lang.PT);
                tran.setOrig(rs.getString("portugal").trim());
                WordType wordType = new WordType();

                String wc = rs.getString("popis");
                if (!isEmpty(wc)) wordType.setWordClass(WordClass.valueOf(Integer.valueOf(wc.trim())));
                String ng = rs.getString("popis2");
                if (!isEmpty(ng)) wordType.setNumGend(NumberGender.valueOf(Integer.valueOf(ng.trim())));
                String va = rs.getString("tvar");
                if (!isEmpty(va)) VariantHelper.processVariants(tran, wordType, va);

                List<Meaning> meanings = new ArrayList<>();
                Meaning s;
                String fi, st, sy;
                for (int i = 1; i <= 10; i++) {
                    s = new Meaning();
                    fi = rs.getString("skratka" + i);
                    if (!isEmpty(fi)) s.setField(Field.valueOf(Integer.valueOf(fi.trim())));
                    st = rs.getString("skratka" + i + "b");
                    if (!isEmpty(st)) s.setStyle(Style.valueOf(Integer.valueOf(st.trim())));
                    sy = rs.getString("slovak" + i);
                    if (!isEmpty(sy)) {
                        s.setSynonyms(sy.trim());
                        meanings.add(s);
                    }
                }
                wordType.setMeanings(meanings);
                tran.addWordType(wordType);
                return tran;
            }
        };
        result = jdbcTemplate.query(sql + "prtbl where chliev = 0", rowMapper);
        result.addAll(jdbcTemplate.query(sql + "prvvzn", rowMapper));
        result.addAll(jdbcTemplate.query(sql + "drhvzn", rowMapper));

        return result;
    }

    private boolean isEmpty(String str) {
        if (str == null) return true;
        str = str.trim();
        if (str.equals("")) return true;
        if (str.equals("0")) return true;
        if (str.equals("-1")) return true;
        if (str.replaceAll("[?]", "").trim().equals("")) return true;
        return false;
    }

    @Override
    public void saveAllSKWords(List<Word> words) {
        String sql = "insert into prtbl "+
                "(id, portugal, popis, popis2, tvar, chliev, "+
                "slovak1, skratka1, skratka1b, slovak2, skratka2, skratka2b, " +
                "slovak3, skratka3, skratka3b, slovak4, skratka4, skratka4b, slovak5, skratka5, skratka5b, " +
                "slovak6, skratka6, skratka6b, slovak7, skratka7, skratka7b, slovak8, skratka8, skratka8b, " +
                "slovak9, skratka9, skratka9b, slovak10, skratka10, skratka10b) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] args = new Object[36];

        int tranCounter = 0;
        for (Word tr: words) {
            args[0] = ++tranCounter + 50000; //id
            String orig = PREFIX + EscapeHelper.escapeSql(tr.getOrig());
            args[1] = orig.substring(0, orig.length() < 40 ? orig.length() : 40); //portugal ~ orig (slovak)
            args[2] = tr.getWordTypes().get(0).getWordClass() != null ? tr.getWordTypes().get(0).getWordClass().getId() : "0"; //popis ~ word class
            args[3] = tr.getWordTypes().get(0).getNumGend() != null ? tr.getWordTypes().get(0).getNumGend().getId() : "0"; //popis2 ~ number/gender
            args[4] = tr.getWordTypes().get(0).getForms() != null ? tr.getWordTypes().get(0).getForms() : ""; //tvar
            args[5] = 1; //chliev - sk = 1
            for (int i = 0; i < 10; i++) {
                if ((i < 9 && i < tr.getWordTypes().get(0).getMeanings().size()) || (i == 9 && i < tr.getWordTypes().get(0).getMeanings().size() && tr.getWordTypes().get(0).getMeanings().size() <= 10)) {
                    Meaning me = tr.getWordTypes().get(0).getMeanings().get(i);
                    args[6 + i*3] = me.getSynonyms(); //slovakX
                    args[7 + i*3] = me.getField() != null ? me.getField().getId() : "0"; // skratkaX
                    args[8 + i*3] = me.getStyle() != null ? me.getStyle().getId() : "0"; // skratkaXb
                } else if (i == 9 && tr.getWordTypes().get(0).getMeanings().size() > 10) { //for all the rest of translations, combine them
                    String syns = "";
                    for (int j = 9; j < tr.getWordTypes().get(0).getMeanings().size(); j++) {
                        Meaning me = tr.getWordTypes().get(0).getMeanings().get(j);
                        syns += me.getSynonyms();
                    }
                    args[33] = syns; //slovakX
                    args[34] = "0"; // skratkaX
                    args[35] = "0"; // skratkaXb
                } else {
                    args[6 + i*3] = ""; args[7 + i*3] = "0"; args[8 + i*3] = "0";
                }
            }
            jdbcTemplate.update(sql, args);
        }
    }


}
