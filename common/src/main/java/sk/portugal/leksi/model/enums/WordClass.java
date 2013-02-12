package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum WordClass implements Serializable {

    NONE (Integer.valueOf(0), ""),
    ABR (Integer.valueOf(1), "abr"),
    ADJ (Integer.valueOf(2), "adj"),
    ADV (Integer.valueOf(3), "adv"),
    ARTDEF (Integer.valueOf(4), "artdef"),
    ARTINDEF (Integer.valueOf(5), "artindef"),
    ATR (Integer.valueOf(6), "atr"),
    AUX (Integer.valueOf(7), "aux"),
    COMPAR (Integer.valueOf(8), "compar"),
    CONJ (Integer.valueOf(9), "conj"),
    ESP (Integer.valueOf(10), "esp"),
    EXCL (Integer.valueOf(11), "excl"),
    GER (Integer.valueOf(12), "ger"),
    IMPESS (Integer.valueOf(13), "impess"),
    INFIN (Integer.valueOf(14), "infin"),
    INV (Integer.valueOf(15), "inv"),
    IRREG (Integer.valueOf(16), "irreg"),
    N (Integer.valueOf(17), "n"),
    NUM (Integer.valueOf(18), "num"),
    PP (Integer.valueOf(19), "pp"),
    PREP (Integer.valueOf(20), "prep"),
    PRON (Integer.valueOf(21), "pron"),
    PT (Integer.valueOf(22), "pt"),
    SUJ (Integer.valueOf(23), "suj"),
    SUB (Integer.valueOf(24), "sub"),
    SUPERL (Integer.valueOf(25), "superl"),
    TB (Integer.valueOf(26), "tb"),
    VB (Integer.valueOf(27), "v"), //vb
    VI (Integer.valueOf(28), "v"), //vi
    VR (Integer.valueOf(29), "v"), //vr
    VT (Integer.valueOf(30), "v"), //vt
    INTER (Integer.valueOf(31), "inter"),
    BRAZ (Integer.valueOf(32), "braz"),

    UNDEF (Integer.valueOf(99), "undef");


    private Integer id;
    private String key;

    private WordClass(Integer id, String key) {
        this.id = id;
        this.key = key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isNoun() {
        return this.equals(N);
    }

    public boolean isVerb() {
        return this.equals(VB) || this.equals(VI) || this.equals(VR) || this.equals(VR);
    }

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static WordClass valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        WordClass[] values = values();
        for (WordClass value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static WordClass valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        WordClass[] values = values();
        for (WordClass value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
