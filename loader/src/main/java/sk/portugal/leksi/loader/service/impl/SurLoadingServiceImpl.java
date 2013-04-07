package sk.portugal.leksi.loader.service.impl;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.model.extra.Alternative;
import sk.portugal.leksi.util.PostProcessor;
import sk.portugal.leksi.util.helper.StringHelper;
import sk.portugal.leksi.util.helper.VariantHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class SurLoadingServiceImpl implements LoadingService {

    private static final String FILTER = ""; // " AND portugal in ('outrem')";
    private static final String SKPREFIX = "SK ";

    private static final boolean ALTERNATIVESASEXTRAWORDS = true;

    private JdbcTemplate jdbcTemplate = null;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Homonym> getWords(final Lang lang) {
        List<Homonym> result = null;

        String sqlw = "SELECT portugal, popis, popis2, slovak1, skratka1, skratka1b, slovak2, skratka2, skratka2b, " +
                "slovak3, skratka3, skratka3b, slovak4, skratka4, skratka4b, slovak5, skratka5, skratka5b, " +
                "slovak6, skratka6, skratka6b, slovak7, skratka7, skratka7b, slovak8, skratka8, skratka8b, " +
                "slovak9, skratka9, skratka9b, slovak10, skratka10, skratka10b, tvar FROM ";

        RowMapper<Homonym> wRowMapper = new RowMapper<Homonym>() {
            public Homonym mapRow(ResultSet rs, int rowNum) throws SQLException {
                Homonym tran = new Homonym();
                Word word = new Word();
                //tran.setId(rs.getInt("id"));
                tran.setLang(lang);
                String orig = StringEscapeUtils.unescapeHtml4(rs.getString("portugal").trim());
                tran.setOrig(lang == Lang.SK ? StringUtils.removeStart(orig, SKPREFIX) : orig);
                //System.out.print(tran.getOrig());

                String wc = rs.getString("popis");
                if (!isEmpty(wc)) {
                    word.setWordClass(PostProcessor.updateWordClass(tran, word, WordClass.valueOf(Integer.valueOf(wc.trim()))));
                } else {
                    word.setWordClass(WordClass.NONE);
                }
                String ng = rs.getString("popis2");
                if (!isEmpty(ng)) word.setNumberGender(NumberGender.valueOf(Integer.valueOf(ng.trim())));
                String va = rs.getString("tvar");
                if (!isEmpty(va)) VariantHelper.processVariants(tran, word, va);

                List<Meaning> meanings = new ArrayList<>();
                Meaning s;
                String fi, st, sy;
                for (int i = 1; i <= 10; i++) {
                    s = new Meaning();
                    fi = rs.getString("skratka" + i);
                    if (!isEmpty(fi)) s.setFieldType(FieldType.valueOf(Integer.valueOf(fi.trim())));
                    st = rs.getString("skratka" + i + "b");
                    if (!isEmpty(st)) s.setStyle(Style.valueOf(Integer.valueOf(st.trim())));
                    sy = rs.getString("slovak" + i);
                    if (!isEmpty(sy)) {
                        //System.out.println(": " + sy.trim());
                        s.setSynonyms(StringEscapeUtils.unescapeHtml4(sy.trim()));
                        meanings.add(s);
                    }
                }
                word.setMeanings(meanings);
                tran.addWordType(word);
                return tran;
            }
        };

        String cond = "", cond2 = "";
        if (lang == Lang.PT) {
            cond = " AND chliev = 0 AND portugal NOT LIKE 'SK %' " + FILTER;
            cond2 = " AND portugal NOT LIKE 'SK %' " + FILTER;
        } else if (lang == Lang.SK) {
            cond = "AND chliev = 1 AND portugal LIKE 'SK %' " + FILTER;
            cond2 = " AND portugal LIKE 'SK %' " + FILTER;
        }

        result = jdbcTemplate.query(sqlw + "prtbl WHERE 1 " + cond, wRowMapper);
        result = cleanse(result, 0);

        List<Homonym> result2 = jdbcTemplate.query(sqlw + "prvvzn WHERE 1 " + cond2, wRowMapper);
        result = mergeWordTypes(result, cleanse(result2, 0));

        result2 = jdbcTemplate.query(sqlw + "drhvzn WHERE 1 " + cond2, wRowMapper);
        result = mergeWordTypes(result, cleanse(result2, 0));

        return result;
    }

    private List<List<Phraseme>> getPhrasemes(final Lang lang) {

        String sqlf = "SELECT portugal, frazp1, frazs1, frazp2, frazs2, frazp3, frazs3, frazp4, frazs4, " +
                "frazp5, frazs5, frazp6, frazs6, frazp7, frazs7, frazp8, frazs8, frazp9, frazs9, " +
                "frazp10, frazs10, frazp11, frazs11, frazp12, frazs12, " +
                "skr1, skr2, skr3, skr4, skr5, skr6, skr7, skr8, skr9, skr10, skr11, skr12, " +
                "2skr1, 2skr2, 2skr3, 2skr4, 2skr5, 2skr6, 2skr7, 2skr8, 2skr9, 2skr10, 2skr11, 2skr12 " +
                "FROM frazi";

        RowMapper<List<Phraseme>> pRowMapper = new RowMapper<List<Phraseme>>() {
            public List<Phraseme> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Phraseme> phrasemes = new ArrayList<>();

                //first phraseme will be original homonym
                Phraseme ph1 = new Phraseme();

                String orig = StringEscapeUtils.unescapeHtml4(rs.getString("portugal").trim());
                ph1.setOrig(lang == Lang.SK ? StringUtils.removeStart(orig, SKPREFIX) : orig);
                phrasemes.add(ph1);

                Phraseme ph;
                String or, tr, fi, st;
                for (int i = 1; i<=12; i++) {
                    ph = new Phraseme();
                    or = rs.getString("frazp" + i);
                    if (!isEmpty(or)) ph.setOrig(StringEscapeUtils.unescapeHtml4(or.trim()));
                    tr = rs.getString("frazs" + i);
                    if (!isEmpty(tr)) ph.setTran(StringEscapeUtils.unescapeHtml4(tr.trim()));
                    st = rs.getString("skr" + i);
                    if (!isEmpty(st)) ph.setStyle(Style.valueOf(Integer.valueOf(st.trim())));
                    fi = rs.getString("2skr" + i);
                    if (!isEmpty(fi)) ph.setFieldType(FieldType.valueOf(Integer.valueOf(fi.trim())));

                    if (!isEmpty(ph)) {
                        phrasemes.add(ph);
                    }
                }

                if (phrasemes.size() == 1) return null; //only original homonym added, nothing but empty phrases
                return phrasemes;
            }
        };

        String cond = "";
        if (lang == Lang.PT) {
            cond = " AND portugal NOT LIKE 'SK %' " + FILTER;
        } else if (lang == Lang.SK) {
            cond = " AND portugal LIKE 'SK %' " + FILTER;
        }

        return jdbcTemplate.query(sqlf + " WHERE 1 " + cond, pRowMapper);
    }

    public List<Homonym> loadAll(final Lang lang) {

        List<Homonym> result = getWords(lang);

        mergeAlternatives(result);

        mergePhrasemes(result, getPhrasemes(lang));

        result.addAll(PostProcessor.addExtraWords(lang));

        if (lang == Lang.PT) {
            PostProcessor.updatePtWords(result);
        } else {
            PostProcessor.updateSkWords(result);
        }

        return result;
    }

    private void mergeAlternatives(List<Homonym> homonymList) {
        List<Homonym> wordsToRemove = new ArrayList<>();
        for (Homonym homonym : homonymList) {
            //identify alternatives
            if (homonym.getWords().get(0).getMeanings().get(0).getSynonyms().startsWith(StringHelper.LINK)) {

                if (homonym.getWords().get(0).getForms() == null) { //only old orthography satisfies
                    if (homonym.getLang() == Lang.PT) {
                        homonym.getWords().get(0).addForm(new Form(FormType.LINK_ORT, ""));

                        Alternative alt = new Alternative();
                        alt.setValue(homonym.getOrig());
                        alt.setNumberGender(homonym.getWords().get(0).getNumberGender());
                        alt.setWordClass(homonym.getWords().get(0).getWordClass());
                        alt.setType(homonym.getLang() == Lang.PT ? AltType.OLD_ORTHOGRAPHY : AltType.UNDEF);
                        Homonym ww = getWord(homonymList, StringUtils.removeStart(homonym.getWords().get(0).getMeanings().get(0).getSynonyms(),
                                StringHelper.LINK + StringHelper.SPACE));
                        if (ww != null) {
                            ww.addAlternative(alt);
                        }
                        wordsToRemove.add(homonym);
                    } else if (homonym.getLang() == Lang.SK && homonym.getWords().get(0).isVerb()) {
                        homonym.getWords().get(0).addForm(new Form(FormType.LINK_SK_VERB_IMP, ""));
                        homonym.getWords().get(0).getMeanings().get(0).setSynonyms( //hack to add PERF info to the verb in link
                                homonym.getWords().get(0).getMeanings().get(0).getSynonyms() + StringHelper.SPACE + StringHelper.PERF);
                    }
                }
            }
        }
        if (!ALTERNATIVESASEXTRAWORDS) {
            homonymList.removeAll(wordsToRemove);
        }
    }

    private Homonym getWord(List<Homonym> homonymList, String wrd) {
        for (Homonym w: homonymList) {
            if (w.getOrig().equals(wrd)) return w;
        }
        return null;
    }

    private List<Homonym> mergeWordTypes(List<Homonym> homonymList, List<Homonym> otherHomonymList) {
        List<Homonym> result = new ArrayList<>();
        for (Homonym homonym : homonymList) {
            for (Homonym homonym2 : otherHomonymList) {
                if (homonym.getOrig().equals(homonym2.getOrig())) {
                    if (homonym2.getWords().get(0).getMeanings() != null
                            && !homonym2.getWords().get(0).getMeanings().isEmpty()
                            && homonym2.getWords().get(0).getMeanings().get(0).getSynonyms() != null
                            && homonym2.getWords().get(0).getMeanings().get(0).getSynonyms().trim().equals("#")) {

                        //add alternative 'spelling' to homonym
                        Alternative alt = new Alternative();
                        alt.setValue(homonym2.getWords().get(0).getForms().get(0).getValues());
                        alt.setNumberGender(homonym2.getWords().get(0).getNumberGender());
                        alt.setWordClass(homonym2.getWords().get(0).getWordClass());
                        alt.setType(AltType.ALTERNATIVE);
                        homonym.addAlternative(alt);
                        if (ALTERNATIVESASEXTRAWORDS) {
                            //add alternative as new homonym
                            result.add(Homonym.createLinkedCopy(homonym2, homonym));
                        }
                    } else {
                        //EXCEPTIONS to accommodate for extra meanings of few homonyms listed
                        if (homonym.getWords().size() > 1
                                && (homonym2.getOrig().equals("tal") || homonym2.getOrig().equals("todo") || homonym2.getOrig().equals("segundo")
                                || homonym2.getOrig().equals("certo") || homonym2.getOrig().equals("que") || homonym2.getOrig().equals("a")
                                || homonym2.getOrig().equals("um"))) {
                            homonym.addWordType(homonym2.getWords().get(0));
                            List<Meaning> ms = homonym2.getWords().get(0).getMeanings();
                            List<Integer> listToRemove = new ArrayList<>();
                            for (int i = 1; i < ms.size(); i++) {
                                String s = ms.get(i).getSynonyms();

                                Word wt = new Word();
                                wt.setWordClass(WordClass.valueOfKey(StringUtils.substringBefore(s, StringHelper.SPACE)));
                                Meaning m = new Meaning();
                                m.setSynonyms(StringUtils.substringAfter(s, StringHelper.SPACE));
                                List<Meaning> mm = new ArrayList<>();
                                mm.add(m);
                                wt.setMeanings(mm);

                                listToRemove.add(i);
                                homonym.addWordType(wt);
                            }
                            removeMarkedLines2(ms, listToRemove);
                        } else {
                            homonym.addWordType(homonym2.getWords().get(0));
                        }
                    }
                }
            }
            result.add(homonym);
        }
        return result;
    }

    private void mergePhrasemes(List<Homonym> result, List<List<Phraseme>> phrasemes) {
        for (Homonym homonym : result) {
            //System.out.println(homonym.getOrig());
            for (List<Phraseme> phs: phrasemes) {
                if (phs != null) {
                    if (homonym.getOrig().equals(phs.get(0).getOrig())) { //1st contains the homonym itself
                        for (int i = 1; i<phs.size(); i++) {
                            Phraseme ph = phs.get(i);

                            ph.setType(VariantHelper.getPhrasemeType(ph.getOrig()));
                            ph.setOrig(StringHelper.removeExpr(ph.getOrig()));  //remove expr. semi-/fix. from orig

                            if (ph.getOrig().matches("^[IV]+[a-j]{1}\\s.*$")) { //is expression
                                String mloc = ph.getOrig().substring(0, ph.getOrig().indexOf(" ")).trim();
                                //System.out.println("XXX mergePhrasemes for: " + homonym.getOrig() + " with: " + ph.getOrig() + " at loc: " + mloc);
                                if (getWTIndex(mloc) < homonym.getWords().size()
                                        && getMnngIndex(mloc) < homonym.getWords().get(getWTIndex(mloc)).getMeanings().size()) {
                                    ph.setOrig(StringUtils.removeStart(ph.getOrig(), mloc).trim());
                                    homonym.getWords().get(getWTIndex(mloc)).getMeanings().get(getMnngIndex(mloc)).addExpression(ph);
                                }
                            } else {
                                homonym.addIdiom(ph);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getMnngIndex(String s) {
        String rom = s.replaceAll("[IV]", "");
        switch (rom) {
            case "a" : return 0;
            case "b" : return 1;
            case "c" : return 2;
            case "d" : return 3;
            case "e" : return 4;
            case "f" : return 5;
            case "g" : return 6;
            case "h" : return 7;
            case "i" : return 8;
            case "j" : return 9;
        }
        return 0;
    }

    private int getWTIndex(String s) {
        String rom = s.replaceAll("[a-j]", "");
        switch (rom) {
            case "I"   : return 0;
            case "II"  : return 1;
            case "III" : return 2;
            case "IV"  : return 3;
            case "V"   : return 4;
            case "VI"  : return 5;
        }
        return 0;
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

    private boolean isEmpty(Phraseme ph) {
        if (ph == null) return true;
        if (ph.getOrig() != null || ph.getTran() != null) return false;
        return true;
    }

    private List<Homonym> cleanse(List<Homonym> homonymList, int wType) {
        List<Integer> linesToRemove = new ArrayList<>();

        //combine split lines
        for (Homonym tr: homonymList) {
            for (int i =  tr.getWords().get(wType).getMeanings().size() - 1; i >= 0; i--) {
                Meaning se = tr.getWords().get(wType).getMeanings().get(i);
                if (se.getSynonyms().startsWith("##")) {
                    Meaning prev = tr.getWords().get(wType).getMeanings().get(i - 1);

                    //combine, but remove starting "##"
                    prev.setSynonyms(StringUtils.stripEnd(prev.getSynonyms(), null)
                            + (prev.getSynonyms().endsWith(StringHelper.SLASH) ? "" : StringHelper.SPACE)
                            + StringUtils.stripStart(se.getSynonyms().substring(2), null));

                    //mark "line" to be removed
                    linesToRemove.add(i);
                }
                removeMarkedLines(tr, wType, linesToRemove);
            }
        }

        //remove homonyms with empty meanings
        for (int i = homonymList.size() - 1; i >= 0 ; i--) {
            Homonym tr = homonymList.get(i);
            if (tr.getWords().get(wType).getMeanings().isEmpty() || !tr.getWords().get(wType).hasNonEmptyMeanings()) {
                linesToRemove.add(i);
            }
        }
        removeMarkedLines(homonymList, linesToRemove);

        return homonymList;
    }

    private static void removeMarkedLines(Homonym tr, int wType, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                tr.getWords().get(wType).getMeanings().remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    private static void removeMarkedLines(List<Homonym> homonyms, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                homonyms.remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    private static void removeMarkedLines2(List<Meaning> meanings, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (int a = linesToRemove.size(); a > 0; a--) {
                meanings.remove((int)linesToRemove.get(a - 1));
            }
            linesToRemove.clear();
        }
    }

}
