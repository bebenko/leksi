package sk.portugal.leksi.util.helper;

import sk.portugal.leksi.model.enums.Lang;

/**
 */
public class LangHelper {

    private static final String ANDSK = "+";
    private static final String ANDPT = "e";

    private static final String DEPT = "de";
    private static final String DESK = "slov.";

    public static String getAnd(Lang explang) {
        return explang == Lang.PT ? ANDPT : ANDSK;
    }

    public static String getOf(Lang explang) {
        return explang == Lang.PT ? DEPT : DESK;
    }
}
