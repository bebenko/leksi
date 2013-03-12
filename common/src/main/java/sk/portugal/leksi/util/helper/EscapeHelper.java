package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 */
public class EscapeHelper {

    public static final String NBSP = "&nbsp;";

    private static String escapeDiacritics(String str) {
        return str.replaceAll("ť", "&#357;").replaceAll("ň", "&#328;").replaceAll("ľ", "&#318;")
                .replaceAll("ĺ", "&#314;").replaceAll("č", "&#269;").replaceAll("ď", "&#271;")
                .replaceAll("ŕ", "&#341;").replaceAll("Č", "&#268;");
    }

    public static String escapeSql(String str) {
        return escapeDiacritics(str);
    }

    public static String escapeHtml(String str) {
        return escapeDiacritics(StringEscapeUtils.escapeHtml4(str)).replaceAll("ž", "&#382;");
    }
}
