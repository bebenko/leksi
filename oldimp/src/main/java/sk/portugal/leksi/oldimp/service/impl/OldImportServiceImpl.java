package sk.portugal.leksi.oldimp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.portugal.leksi.oldimp.model.Frazi;
import sk.portugal.leksi.oldimp.model.Prtbl;
import sk.portugal.leksi.oldimp.model.Triple;
import sk.portugal.leksi.oldimp.service.OldImportService;
import sk.portugal.leksi.util.helper.StringHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juraj.benko
 * Date: 28/01/13
 * Time: 22:03
 */
public class OldImportServiceImpl implements OldImportService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void mergeVPron(String word) {
        String sqlPrtbl = "SELECT portugal, popis, popis2, slovak1, skratka1, skratka1b, slovak2, skratka2, skratka2b, " +
                "slovak3, skratka3, skratka3b, slovak4, skratka4, skratka4b, slovak5, skratka5, skratka5b, " +
                "slovak6, skratka6, skratka6b, slovak7, skratka7, skratka7b, slovak8, skratka8, skratka8b, " +
                "slovak9, skratka9, skratka9b, slovak10, skratka10, skratka10b, tvar " +
                "FROM prtbl WHERE portugal = 'XXX'";

        RowMapper<Prtbl> prtblRowMapper = new RowMapper<Prtbl>() {
            public Prtbl mapRow(ResultSet rs, int rowNum) throws SQLException {
                Prtbl prtbl = new Prtbl();

                prtbl.setPortugal(rs.getString("portugal"));
                prtbl.setPopis(rs.getInt("popis"));
                prtbl.setPopis2(rs.getInt("popis2"));
                prtbl.setTvar(rs.getString("tvar"));

                for (int i = 1; i <= 10; i++) {
                    prtbl.addVyznamy(rs.getString("slovak" + i), rs.getString("skratka" + i),
                            rs.getString("skratka" + i + "b"));
                }

                return prtbl;
            }
        };

        List<Prtbl> prtblList = jdbcTemplate.query(StringUtils.replace(sqlPrtbl, "XXX", word), prtblRowMapper);

        if (!prtblList.isEmpty()) {
            //printInsert("prvvzn", prtblList.get(0));
            //printDelete("prtbl", prtblList.get(0));
            //printUpdate("prtbl", prtblList.get(0));
            printDelete("prvvzn", prtblList.get(0));
        }
    }

    private void importWord(String word) {
        List<Prtbl> prtblList = null, prvvznList = null, drhvznList = null;
        List<Frazi> fraziList = null;

        String sqlPrtbl = "SELECT portugal, popis, popis2, slovak1, skratka1, skratka1b, slovak2, skratka2, skratka2b, " +
                "slovak3, skratka3, skratka3b, slovak4, skratka4, skratka4b, slovak5, skratka5, skratka5b, " +
                "slovak6, skratka6, skratka6b, slovak7, skratka7, skratka7b, slovak8, skratka8, skratka8b, " +
                "slovak9, skratka9, skratka9b, slovak10, skratka10, skratka10b, tvar " +
                "FROM ";

        String sqlFrazi = "SELECT portugal, frazp1, frazs1, frazp2, frazs2, frazp3, frazs3, frazp4, frazs4, " +
                "frazp5, frazs5, frazp6, frazs6, frazp7, frazs7, frazp8, frazs8, frazp9, frazs9, " +
                "frazp10, frazs10, frazp11, frazs11, frazp12, frazs12, " +
                "skr1, skr2, skr3, skr4, skr5, skr6, skr7, skr8, skr9, skr10, skr11, skr12, " +
                "2skr1, 2skr2, 2skr3, 2skr4, 2skr5, 2skr6, 2skr7, 2skr8, 2skr9, 2skr10, 2skr11, 2skr12 " +
                "FROM frazi ";


        RowMapper<Prtbl> prtblRowMapper = new RowMapper<Prtbl>() {
            public Prtbl mapRow(ResultSet rs, int rowNum) throws SQLException {
                Prtbl prtbl = new Prtbl();

                prtbl.setPortugal(rs.getString("portugal"));
                prtbl.setPopis(rs.getInt("popis"));
                prtbl.setPopis2(rs.getInt("popis2"));
                prtbl.setTvar(rs.getString("tvar"));

                for (int i = 1; i <= 10; i++) {
                    prtbl.addVyznamy(rs.getString("slovak" + i), rs.getString("skratka" + i),
                            rs.getString("skratka" + i + "b"));
                }

                return prtbl;
            }
        };

        RowMapper<Frazi> fraziRowMapper = new RowMapper<Frazi>() {
            public Frazi mapRow(ResultSet rs, int rowNum) throws SQLException {
                Frazi frazi = new Frazi();

                frazi.setPortugal(rs.getString("portugal"));

                for (int i = 1; i <= 12; i++) {
                    frazi.addFrazy(rs.getString("frazp" + i), rs.getString("frazs" + i),
                            rs.getString("skr" + i), rs.getString("2skr" + i));
                }

                return frazi;
            }
        };

        prtblList = jdbcTemplate.query(sqlPrtbl + " prtbl WHERE portugal = '" + word + "'", prtblRowMapper);
        prvvznList = jdbcTemplate.query(sqlPrtbl + " prvvzn WHERE portugal = '" + word + "'", prtblRowMapper);
        drhvznList = jdbcTemplate.query(sqlPrtbl + " drhvzn WHERE portugal = '" + word + "'", prtblRowMapper);
        fraziList = jdbcTemplate.query(sqlFrazi + " prtbl WHERE portugal = '" + word + "'", fraziRowMapper);

        if (!prtblList.isEmpty()) printInsert("prtbl", prtblList.get(0));
        if (!prvvznList.isEmpty()) printInsert("prvvzn", prvvznList.get(0));
        if (!drhvznList.isEmpty()) printInsert("drhvzn", drhvznList.get(0));
        if (!fraziList.isEmpty()) printInsertF(fraziList.get(0));
    }

    private void printInsert(String table, Prtbl r) {
        String sql = "INSERT INTO `" + table + "` (`Portugal`, `Popis`, `Popis2`, ";
        for (int i = 1; i <= 10; i++) {
            sql += "`Slovak" + i + "`, `Skratka" + i + "`, `Skratka" + i + "b`, ";
        }
        sql += "`Tvar`) VALUES (" +
                "'" + r.getPortugal() + "', " +
                r.getPopis() + ", " +
                r.getPopis2() + ", ";
        for (int i = 0; i < 10; i++) {
            sql += "'" + r.getVyznamy().get(i).getSlovak() + "', " +
                    "'" + r.getVyznamy().get(i).getSkratka() + "', " +
                    "'" + r.getVyznamy().get(i).getSkratkab() + "', ";
        }
        sql += "'" + r.getTvar() + "');";

        System.out.println(sql);
    }

    private void printUpdate(String table, Prtbl r) {
        String sql = "UPDATE `" + table + "` SET `Tvar` = ";
        sql += "'" + r.getTvar() + "' ";
        sql += "WHERE `portugal` = '" + r.getPortugal() + "';";

        System.out.println(sql);
    }

    private void printDelete(String table, Prtbl r) {
        String sql = "DELETE FROM `" + table + "` ";
        sql += "WHERE `portugal` = '" + r.getPortugal() + "';";

        System.out.println(sql);

    }
    private void printInsertF(Frazi r) {
        String sql = "INSERT INTO `frazi` (`Portugal`, ";
        for (int i = 1; i <= 12; i++) {
            sql += "`Frazp" + i + "`, `Frazs" + i + "`, `skr" + i + "`, `2skr" + i + "`, ";
        }
        sql = StringUtils.removeEnd(sql, ", ") + ") VALUES ('" + r.getPortugal() + "', ";

        for (int i = 0; i < 12; i++) {
            sql += "'" + r.getFrazy().get(i).getP() + "', " +
                    "'" + r.getFrazy().get(i).getS() + "', " +
                    "'" + r.getFrazy().get(i).getSkr() + "', " +
                    "'" + r.getFrazy().get(i).getSkr2() + "', ";
        }
        sql = StringUtils.removeEnd(sql, ", ") + ");";

        System.out.println(sql);
    }

    @Override
    public void generateImport(List<String> wordList) {
        for (String word: wordList) {
            importWord(word);
        }
    }

    @Override
    public void generateVPronMerge(List<String> wordList) {
        for (String word: wordList) {
            //mergeVPron(word);
            String sql = "DELETE FROM `prvvzn` ";
            sql += "WHERE `portugal` = '" + StringUtils.substringBeforeLast(word, StringHelper.SPACE) + "';";

            System.out.println(sql);

            sql = "UPDATE `prtbl` SET `tvar` = ";
            sql += "'vrefl-" + StringUtils.substringAfterLast(word, StringHelper.SPACE) + "' ";
            sql += "WHERE `portugal` = '" + StringUtils.substringBeforeLast(word, StringHelper.SPACE) + "';";

            //System.out.println(sql);

        }
    }
}
