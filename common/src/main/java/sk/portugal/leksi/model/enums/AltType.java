package sk.portugal.leksi.model.enums;

import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public enum AltType  {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    ALTERNATIVE (Integer.valueOf(1), "alt", "", ""),
    GRAFANT(Integer.valueOf(2), "ort", StringHelper.GRAFANT, StringHelper.STPRAV),
    GRAFDUPL(Integer.valueOf(3), "dupl", StringHelper.GRAFDUPL, StringHelper.DVOJPRAV),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private AltType(Integer id, String key, String print_pt, String print_sk) {
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
        if (lang == Lang.NONE) return key;
        if (lang == Lang.SK) return print_sk;
        return print_pt;
    }

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static AltType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        AltType[] values = values();
        for (AltType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static AltType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        AltType[] values = values();
        for (AltType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
