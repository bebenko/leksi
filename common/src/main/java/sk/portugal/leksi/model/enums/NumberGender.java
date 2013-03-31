package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum NumberGender implements Serializable {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    F (Integer.valueOf(1), "f", "f.", "ž."),
    FPL (Integer.valueOf(2), "f/pl", "f. pl.", "ž. mn."),
    M (Integer.valueOf(3), "m", "m.", "m."),
    MPL (Integer.valueOf(4), "m/pl", "m. pl.", "m. mn."),
    MF (Integer.valueOf(5), "m/f", "m./f.", "m./ž."),
    MFPL (Integer.valueOf(6), "m/f/pl", "m./f. pl.", "m./ž. mn."),
    PL (Integer.valueOf(7), "pl", "pl.", "mn."),
    N (Integer.valueOf(8), "n", "nt.", "s.", "nt"),
    NPL (Integer.valueOf(9), "n/pl", "nt./pl.", "s. mn.", "nt/pl"),

    SG (Integer.valueOf(50), "sg", "sing.", "jedn."),
    MSG (Integer.valueOf(51), "m/sg", "m. sing.", "m. jedn."),
    FSG (Integer.valueOf(52), "m/sg", "f. sing.", "ž. jedn."),
    NSG (Integer.valueOf(53), "n/sg", "nt. sing.", "s. jedn."),
    MFSG (Integer.valueOf(54), "m/f/sg", "m./f. sing.", "m./ž. jedn."),
    MNSG (Integer.valueOf(55), "m/n/sg", "m./nt. sing.", "m./s. jedn."),
    FNSG (Integer.valueOf(56), "f/n/sg", "f./nt. sing.", "ž./s. jedn."),
    SGPL (Integer.valueOf(59), "sg/pl", "sing./pl.", "jedn./mn."),

    INV (Integer.valueOf(60), "inv", "inv.", ""),

    CF (Integer.valueOf(70), "cf.", "cf.", "cf."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;
    private String altKey;

    private NumberGender(Integer id, String key, String print_pt, String print_sk) {
        this.id = id;
        this.key = key;
        this.print_pt = print_pt;
        this.print_sk = print_sk;
    }

    private NumberGender(Integer id, String key, String print_pt, String print_sk, String altKey) {
        this(id, key, print_pt, print_sk);
        this.altKey = altKey;
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
    public static NumberGender valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        NumberGender[] values = values();
        for (NumberGender value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static NumberGender valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        NumberGender[] values = values();
        for (NumberGender value: values) {
            if (value.key.equals(strValue) || (value.altKey != null && value.altKey.equals(strValue))) { return value; }
        }

        return UNDEF;
    }

}
