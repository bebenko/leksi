package sk.portugal.leksi.model.enums;

/**
 */
public enum CaseType {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    NOM (Integer.valueOf(1), "nom", "nom.", "N"),
    GEN (Integer.valueOf(2), "gen", "gen.", "G"),
    DAT (Integer.valueOf(3), "dat", "dat.", "D"),
    ACC (Integer.valueOf(4), "acc", "ac.", "A"),
    LOC (Integer.valueOf(6), "loc", "loc.", "L"),
    INST (Integer.valueOf(7), "inst", "inst.", "I"),

    GAL (Integer.valueOf(10), "gal", "gen., acc., loc.", "G, A, L"),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private CaseType(Integer id, String key, String print_pt, String print_sk) {
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
    public static CaseType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        CaseType[] values = values();
        for (CaseType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static CaseType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        CaseType[] values = values();
        for (CaseType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
