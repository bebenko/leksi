package sk.portugal.leksi.kskimp.service.impl;

import sk.portugal.leksi.kskimp.service.KskImportService;
import sk.portugal.leksi.util.helper.EscapeHelper;

import java.util.List;

/**
 */
public class KskImportServiceImpl implements KskImportService {

    private void createWordImport(String table, String word) {

        String sql = "UPDATE " + table + " SET portugal = replace(portugal, 'KSK ', 'SK ') where portugal = '" + EscapeHelper.escapeSql(word) + "';";
        System.out.println(sql);

    }

    private String ksk(String word) {
        if (word.startsWith("KSK ")) return word;
        return "KSK " + word;
    }


    @Override
    public void generateImport(List<String> wordList) {
        int counter = 0;
        for  (String word: wordList) {
            createWordImport("prtbl", ksk(word));
            counter++;
        }
        for  (String word: wordList) {
            createWordImport("prvvzn", word);
            counter++;
        }
        for  (String word: wordList) {
            createWordImport("drhvzn", word);
            counter++;
        }
        for  (String word: wordList) {
            createWordImport("frazi", word);
            counter++;
        }
        System.out.println("counter: " + counter);
    }

}
