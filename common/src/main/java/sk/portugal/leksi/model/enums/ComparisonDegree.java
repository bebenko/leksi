package sk.portugal.leksi.model.enums;

/**
 */
public enum ComparisonDegree {

    POS (Integer.valueOf(0), "pos", "", ""),
    COMP (Integer.valueOf(1), "comp", "comp.", "druhý st."),
    SUP (Integer.valueOf(2), "sup", "sup.", "tretí st."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private ComparisonDegree(Integer id, String key, String print_pt, String print_sk) {
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
    public static ComparisonDegree valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        ComparisonDegree[] values = values();
        for (ComparisonDegree value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static ComparisonDegree valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        ComparisonDegree[] values = values();
        for (ComparisonDegree value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
