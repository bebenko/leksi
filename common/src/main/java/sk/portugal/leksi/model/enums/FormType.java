package sk.portugal.leksi.model.enums;

import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public enum FormType {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    PP (Integer.valueOf(1), "pp", "particip. pass.", "trp. príč."),
    P (Integer.valueOf(2), "p", "particip.", "príč."),
    M (Integer.valueOf(3), "m", "m.", "m."),
    F (Integer.valueOf(4), "f", "f.", "ž."),
    N (Integer.valueOf(5), "n", "nt.", "s."),
    PL (Integer.valueOf(6), "pl", "pl.", "mn."),
    PLINV (Integer.valueOf(7), "pl inv", "pl. inv.", "jedn./mn."),
    CF (Integer.valueOf(9), "cf", "cf.", "cf."),

    PARTVERB (Integer.valueOf(20), "partverb", "v.", "slov."),
    VERBFORM (Integer.valueOf(21), "forma", "forma do verbo", "tvar slovesa"),
    VREFLSA (Integer.valueOf(22), "vrefl-sa", "refl.", "zvrat."),
    VREFLSI (Integer.valueOf(23), "vrefl-si", "refl.", "zvrat."),

    LINK (Integer.valueOf(90), StringHelper.LINK, StringHelper.LINK, StringHelper.LINK),
    LINK_GRAFANT (Integer.valueOf(91), StringHelper.LINK,
            StringHelper.GRAFANT + StringHelper.SPACE + StringHelper.LINK,
            StringHelper.STPRAV + StringHelper.SPACE + StringHelper.LINK),
    LINK_GRAFDUPL(Integer.valueOf(92), StringHelper.LINK,
            StringHelper.GRAFDUPL + StringHelper.SPACE + StringHelper.LINK,
            StringHelper.DVOJPRAV + StringHelper.SPACE + StringHelper.LINK),
    LINK_SK_VERB_IMP (Integer.valueOf(93), StringHelper.LINK, StringHelper.LINK, StringHelper.LINK),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private FormType(Integer id, String key, String print_pt, String print_sk) {
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
    public static FormType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        FormType[] values = values();
        for (FormType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static FormType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        FormType[] values = values();
        for (FormType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
