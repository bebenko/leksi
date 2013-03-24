package sk.portugal.leksi.model.enums;

import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public enum FormType {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    PP (Integer.valueOf(1), "pp", "particip. pass.", "trp. príč."),
    P (Integer.valueOf(2), "p", "particip.", "príč."),
    F (Integer.valueOf(3), "f", "f.", "ž."),
    PL (Integer.valueOf(4), "pl", "pl.", "mn."),
    PLINV (Integer.valueOf(5), "pl inv", "pl. inv.", "jedn./mn."),

    PARTVERB (Integer.valueOf(20), "partverb", "v.", "slov."),
    VERBFORM (Integer.valueOf(21), "forma", "forma do verbo", "tvar slovesa"),

    LINK (Integer.valueOf(90), StringHelper.LINK, StringHelper.LINK, StringHelper.LINK),
    LINK_ORT (Integer.valueOf(91), StringHelper.LINK,
            StringHelper.GRAFANT + StringHelper.SPACE + StringHelper.LINK,
            StringHelper.STPRAV + StringHelper.SPACE + StringHelper.LINK),
    LINK_SK_VERB_IMP (Integer.valueOf(92), StringHelper.LINK, StringHelper.LINK, StringHelper.LINK),

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
