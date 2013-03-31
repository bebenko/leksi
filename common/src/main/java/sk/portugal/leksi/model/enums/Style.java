package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum Style implements Serializable {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    HOVOR (Integer.valueOf(1), "col", "col.", "hovor."),
    COL (Integer.valueOf(2), "col(!)", "col.(!)", "col.(!)"),
    ZVOL (Integer.valueOf(3), "zvol", "zvol.", "zvol."),
    PREN (Integer.valueOf(4), "pren", "fig.", "pren."),
    PEJOR (Integer.valueOf(5), "pejor", "pej.", "pejor."),
    FAM (Integer.valueOf(6), "fam", "fam.", "fam."),
    VULG (Integer.valueOf(7), "vulg", "vulg.", "vulg."),
    EXPR (Integer.valueOf(8), "expr", "expr.", "expr."),
    DETS (Integer.valueOf(9), "dets", "infant.", "dets."),

    SLANG (Integer.valueOf(51), "slang", "calão", "slang"),
    HRUB (Integer.valueOf(52), "hrub", "gross.", "hrub."),
    ODB (Integer.valueOf(53), "odb", "tec.", "odb."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private Style(Integer id, String key, String print_pt, String print_sk) {
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
    public static Style valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        Style[] values = values();
        for (Style value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static Style valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        Style[] values = values();
        for (Style value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
