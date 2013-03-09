package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum WordClass implements Serializable {

    NONE (Integer.valueOf(0), "n/a", "", ""),    //
    ABR (Integer.valueOf(1), "abr", "abr.", ""),
    ADJ (Integer.valueOf(2), "adj", "adj.", "príd."),        //
    ADV (Integer.valueOf(3), "adv", "adv.", "prísl."),       //
    ARTDEF (Integer.valueOf(4), "artdef", "art def.", "urč. člen"), //
    ARTINDEF (Integer.valueOf(5), "artindef", "art indef.", "neurč. člen"),
    ATR (Integer.valueOf(6), "atr", "atr.", ""),
    AUX (Integer.valueOf(7), "aux", "aux.", ""),
    COMPAR (Integer.valueOf(8), "compar", "comp.", "komp."),
    CONJ (Integer.valueOf(9), "conj", "conj.", "spoj."),      //
    ESP (Integer.valueOf(10), "esp", "esp.", ""),
    EXCL (Integer.valueOf(11), "excl", "excl.", "excl."),     //
    GER (Integer.valueOf(12), "ger", "ger.", ""),
    IMPESS (Integer.valueOf(13), "impess", "impess.", "neos."),
    INFIN (Integer.valueOf(14), "infin", "infin.", ""),
    INV (Integer.valueOf(15), "inv", "inv.", "inv."),        //
    IRREG (Integer.valueOf(16), "irreg", "irreg.", "nepr."),
    N (Integer.valueOf(17), "n", "n.", "podst. m."),              //
    NUM (Integer.valueOf(18), "num", "num.", "čísl."),       //
    PP (Integer.valueOf(19), "pp", "particip. pass.", "trp. príč."),   //
    PREP (Integer.valueOf(20), "prep", "prep.", "predl."),    //
    PRON (Integer.valueOf(21), "pron", "pron.", "zám."),     //
    PT (Integer.valueOf(22), "pt", "part.", "čast."),       //
    SUJ (Integer.valueOf(23), "suj", "suj.", ""),
    SUB (Integer.valueOf(24), "sub", "sub.", ""),
    SUPERL (Integer.valueOf(25), "superl", "sup.", "superl."),
    TB (Integer.valueOf(26), "tb", "tb.", ""),              //
    VB (Integer.valueOf(27), "vb", "v.", "slov."), //vb        //
    VI (Integer.valueOf(28), "vi", "v.", "slov."), //vi        //
    VR (Integer.valueOf(29), "vr", "v.", "slov."), //vr        //
    VT (Integer.valueOf(30), "vt", "v.", "slov."), //vt        //
    INTER (Integer.valueOf(31), "inter", "interj.", "cit."),   //
    BRAZ (Integer.valueOf(32), "braz", "braz.", "braz."),
    PORT (Integer.valueOf(33), "port", "port.", "port."),

    VPRON (Integer.valueOf(50), "pronominal", "v. pron.", "zvrat. slov."),   //
    PARTICIP (Integer.valueOf(51), "particip", "particip.", "príč."),
    LOC (Integer.valueOf(52), "loc", "loc.", "sp."),
    LOCPREP (Integer.valueOf(53), "locprep", "loc. prep.", "predl. sp."),
    LOCADV (Integer.valueOf(54), "locadv", "loc. adv.", "prísl. sp."),
    LOCCONJ (Integer.valueOf(55), "loccong", "loc. conj.", "spoj. výr."),

    POSS (Integer.valueOf(56), "poss", "poss.", "privl."),
    DEM (Integer.valueOf(57), "dem", "dem.", "ukaz."),
    PRONPESS (Integer.valueOf(58), "pronpess", "pron. pess.", "osob. zám."),
    PRONREL (Integer.valueOf(59), "pronrel", "pron. rel.", "vzťaž. zám."),
    PRONINT (Integer.valueOf(60), "pronint", "pron. int.", "opyt. zám."),
    NUMORD (Integer.valueOf(61), "numord", "num. ord.", "zákl. čísl."),
    NUMCARD (Integer.valueOf(62), "numcard", "num. card.", "rad. čísl."),
    QUANT (Integer.valueOf(63), "quant", "quant.", "kvant."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private WordClass(Integer id, String key, String print_pt, String print_sk) {
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
