package sk.portugal.leksi.model.enums;

import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public enum PhrasemeType {

    NONE (Integer.valueOf(0), "", "", ""),
    FIX (Integer.valueOf(1), StringHelper.EXPRFIX, StringHelper.EXPRFIX, "ust. sl. sp."),
    SEMIFIX (Integer.valueOf(2), StringHelper.EXPRSEMIFIX, StringHelper.EXPRSEMIFIX, "poloust. sl. sp."),

    CONTR (Integer.valueOf(50), "contr.", "contr.", "kontr."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private PhrasemeType(Integer id, String key, String print_pt, String print_sk) {
        this.id = id;
        this.key = key;
        this.print_pt = print_pt;
        this.print_sk = print_sk;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getPrint(Lang lang) {
        if (lang == Lang.SK) return print_sk;
        return print_pt;
    }

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static PhrasemeType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        PhrasemeType[] values = values();
        for (PhrasemeType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static PhrasemeType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        PhrasemeType[] values = values();
        for (PhrasemeType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
