package sk.portugal.leksi.util.helper;

import sk.portugal.leksi.model.enums.Lang;

/**
 */
public class LangHelper {

    private static final String ANDSK = "+";
    private static final String ANDPT = "e";

    private static final String DE = "de";
    private static final String OD = "od";
    private static final String SLOV = "slov.";

    private static final String VERBFORMPT = "forma do verbo";
    private static final String VERBFORMSK = "tvar slovesa";

    public static String getAnd(Lang explang) {
        return explang == Lang.PT ? ANDPT : ANDSK;
    }

    public static String getOfVerb(Lang explang) {
        return explang == Lang.PT ? DE : SLOV;
    }

    public static String getOf(Lang explang) {
        return explang == Lang.PT ? DE : OD;
    }

    public static String getFormOfVerb(Lang explang) {
        return explang == Lang.PT ? VERBFORMPT : VERBFORMSK;
    }

}
