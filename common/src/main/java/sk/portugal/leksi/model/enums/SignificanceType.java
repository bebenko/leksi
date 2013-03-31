package sk.portugal.leksi.model.enums;

/**
 */
public enum SignificanceType {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    MASS (Integer.valueOf(1), "mass", "mass.", "nep."),
    PERF (Integer.valueOf(2), "perf", "perf.", "dok."),
    IMP (Integer.valueOf(3), "imp", "imp.", "nedok."),
    IMPPERF (Integer.valueOf(4), "imp/perf", "imp./perf.", "nedok./dok."),
    FIG (Integer.valueOf(5), "fig", "fig.", "pren."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private SignificanceType(Integer id, String key, String print_pt, String print_sk) {
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
    public static SignificanceType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        SignificanceType[] values = values();
        for (SignificanceType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static SignificanceType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        SignificanceType[] values = values();
        for (SignificanceType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }

}
