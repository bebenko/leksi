package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum WordClass implements Serializable {

    NONE (Integer.valueOf(0), "none", "", "", "none"),    //
    ABR (Integer.valueOf(1), "abr", "abr.", ""),
    ADJ (Integer.valueOf(2), "adj", "adj.", "príd.", "adj"),        //
    ADV (Integer.valueOf(3), "adv", "adv.", "prísl.", "adv"),       //
    ARTDEF (Integer.valueOf(4), "artdef", "art def.", "urč. člen", "artic"), //
    ARTINDEF (Integer.valueOf(5), "artindef", "art indef.", "neurč. člen", "artic"),
    ATR (Integer.valueOf(6), "atr", "atr.", ""),
    AUX (Integer.valueOf(7), "aux", "aux.", ""),
    COMPAR (Integer.valueOf(8), "compar", "comp.", "komp."),
    CONJ (Integer.valueOf(9), "conj", "conj.", "spoj.", "conj"),      //
    ESP (Integer.valueOf(10), "esp", "esp.", ""),
    EJAC(Integer.valueOf(11), "ejac", "excl.", "zvol.", "ejac"),     //
    GER (Integer.valueOf(12), "ger", "ger.", ""),
    IMPESS (Integer.valueOf(13), "impess", "impess.", "neos."),
    INFIN (Integer.valueOf(14), "infin", "infin.", ""),
    INV (Integer.valueOf(15), "inv", "inv.", "inv."),        //
    IRREG (Integer.valueOf(16), "irreg", "irreg.", "nepr."),
    N (Integer.valueOf(17), "n", "n.", "podst. m.", "noun"),              //
    NUM (Integer.valueOf(18), "num", "num.", "čísl.", "num"),       //
    PP (Integer.valueOf(19), "pp", "particip. pass.", "trp. príč.", "part"),   //
    PREP (Integer.valueOf(20), "prep", "prep.", "predl.", "prep"),    //
    PRON (Integer.valueOf(21), "pron", "pron.", "zám.", "pron"),     //
    PT (Integer.valueOf(22), "pt", "port.", "port."),       //
    SUJ (Integer.valueOf(23), "suj", "suj.", ""),
    SUB (Integer.valueOf(24), "sub", "sub.", ""),
    SUPERL (Integer.valueOf(25), "superl", "sup.", "superl."),
    TB (Integer.valueOf(26), "tb", "tb.", ""),              //
    VB (Integer.valueOf(27), "vb", "v.", "slov.", "verb"), //vb        //
    VI (Integer.valueOf(28), "vi", "v.", "slov.", "verb"), //vi        //
    VR (Integer.valueOf(29), "vr", "v.", "slov.", "verb"), //vr        //
    VT (Integer.valueOf(30), "vt", "v.", "slov.", "verb"), //vt        //
    INTER (Integer.valueOf(31), "inter", "interj.", "cit.", "inter"),   //
    BRAZ (Integer.valueOf(32), "braz", "braz.", "braz."),
    PART (Integer.valueOf(33), "part", "", ""), //EXCEPTION: do not show! (for now)  PART (Integer.valueOf(33), "part", "part.", "čast.")
    P (Integer.valueOf(34), "p", "particip.", "príč.", "part"),

    VPRON (Integer.valueOf(50), "pronominal", "v. pron.", "zvrat. slov.", "verb"),   //
    //PARTICIP (Integer.valueOf(51), "particip", "particip.", "príč."),
    LOC (Integer.valueOf(52), "loc", "loc.", "sp.", "colloc"),
    LOCPREP (Integer.valueOf(53), "locprep", "loc. prep.", "predl. sp.", "colloc"),
    LOCADV (Integer.valueOf(54), "locadv", "loc. adv.", "prísl. sp.", "colloc"),
    LOCCONJ (Integer.valueOf(55), "locconj", "loc. conj.", "spoj. výr.", "colloc"),
    VPRONSA (Integer.valueOf(56), "pronominal-sa", "v. pron. (-sa)", "zvrat. slov. (-sa)", "verb"),   //
    VPRONSI (Integer.valueOf(57), "pronominal-si", "v. pron. (-si)", "zvrat. slov. (-si)", "verb"),   //

    PRONPOSS (Integer.valueOf(60), "pronposs", "poss.", "zám. privl.", "pron"),
    PRONDEM (Integer.valueOf(61), "prondem", "dem.", "zám. ukaz.", "pron"),
    PRONPESS (Integer.valueOf(62), "pronpess", "pron. pess.", "zám. osob.", "pron"),
    PRONREL (Integer.valueOf(63), "pronrel", "pron. rel.", "zám. vzťaž.", "pron"),
    PRONINT (Integer.valueOf(64), "pronint", "pron. int.", "zám. opyt.", "pron"),
    PRONQUANT (Integer.valueOf(65), "pronquant", "quant.", "zám. vymedz.", "pron"),
    PRONINDEF (Integer.valueOf(66), "pronindef", "pron. indef.", "zám. neurč.", "pron"),
    //PRONZVR (Integer.valueOf(67), "pron", "pron.", "zám. zvrat."),

    VIMP (Integer.valueOf(70), "vimp", "v. imp.", "slov. nedok.", "verb"),
    VPERF (Integer.valueOf(71), "vperf", "v. perf.", "slov. dok.", "verb"),
    VIMPPERF (Integer.valueOf(72), "vimpperf", "v. imp./perf.", "slov. nedok./dok.", "verb"),
    VPRONIMP (Integer.valueOf(73), "vpronimp", "v. pron. imp.", "zvrat. slov. nedok.", "verb"),
    VPRONPERF (Integer.valueOf(74), "vpronperf", "v. pron. perf.", "zvrat. slov. dok.", "verb"),
    VPRONIMPPERF (Integer.valueOf(75), "vpronimpperf", "v. pron. imp./perf.", "zvrat. slov. nedok./dok.", "verb"),

    NUMORD (Integer.valueOf(80), "numord", "num. ord.", "čísl. zákl.", "num"),
    NUMCARD (Integer.valueOf(81), "numcard", "num. card.", "čísl. rad.", "num"),
    NUMFRAC (Integer.valueOf(82), "numfrac", "num. fr.", "čísl. zlom.", "num"),

    CONTR (Integer.valueOf(96), "contr", "", ""),

    UNDEF (Integer.valueOf(99), "undef", "", ""),
    ALL (Integer.valueOf(100), "all", "", "", "all");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;
    private String group;

    private WordClass(Integer id, String key, String print_pt, String print_sk) {
        this.id = id;
        this.key = key;
        this.print_pt = print_pt;
        this.print_sk = print_sk;
    }

    private WordClass(Integer id, String key, String print_pt, String print_sk, String group) {
        this(id, key, print_pt, print_sk);
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public boolean isNoun() {
        return group == null ? false : group.equals("noun");
    }

    public boolean isAdjective() {
        return group == null ? false : group.equals("adj");
    }

    public boolean isVerb() {
        return group == null ? false : group.equals("verb");
    }

    public boolean isPronoun() {
        return group == null ? false : group.equals("pron");
    }

    public boolean isNumber() {
        return group == null ? false : group.equals("num");
    }

    public boolean isParticiple() {
        return group == null ? false : group.equals("part");
    }

    public boolean isAdverb() {
        return group == null ? false : group.equals("adv");
    }

    public boolean isPreposition() {
        return group == null ? false : group.equals("prep");
    }

    public boolean isInterjection() {
        return group == null ? false : group.equals("inter");
    }

    public boolean isEjaculation() {
        return group == null ? false : group.equals("ejac");
    }

    public boolean isArticle() {
        return group == null ? false : group.equals("artic");
    }

    public boolean isConjunction() {
        return group == null ? false : group.equals("conj");
    }

    public boolean isCollocation() {
        return group == null ? false : group.equals("colloc");
    }

    public boolean isNone() {
        return group == null ? false : group.equals("none");
    }

    private static int getCount(String group) {
        int count = 0;
        for (WordClass value: values()) {
            if ((group == null && value.getGroup() == null) || (group != null && group.equals(value.getGroup()))) count++;
        }
        return count;
    }

    public static WordClass[] all() {
        WordClass[] result = new WordClass[values().length]; int i = 1; //!
        result[0] = WordClass.ALL;
        for (WordClass value: values()) {
            if (value.id < 99) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] verbs() {
        WordClass[] result = new WordClass[getCount("verb")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isVerb()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] numbers() {
        WordClass[] result = new WordClass[getCount("num")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isNumber()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] pronouns() {
        WordClass[] result = new WordClass[getCount("pron")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isPronoun()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] participles() {
        WordClass[] result = new WordClass[getCount("part")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isParticiple()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] articles() {
        WordClass[] result = new WordClass[getCount("artic")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isArticle()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] collocations() {
        WordClass[] result = new WordClass[getCount("colloc")]; int i = 0;
        for (WordClass value: values()) {
            if (value.isCollocation()) { result[i++] = value; }
        }
        return result;
    }

    public static WordClass[] adverbs() {
        return new WordClass[] {ADV};
    }

    public static WordClass[] prepositions() {
        return new WordClass[] {PREP};
    }

    public static WordClass[] interjections() {
        return new WordClass[] {INTER};
    }

    public static WordClass[] ejaculations() {
        return new WordClass[] {EJAC};
    }

    public static WordClass[] conjunctions() {
        return new WordClass[] {CONJ};
    }

    public static WordClass[] adjectives() {
        return new WordClass[] {ADJ};
    }

    public static WordClass[] nouns() {
        return new WordClass[] {N};
    }

    public static WordClass[] others() {
        WordClass[] result = new WordClass[getCount(null) + 1]; int i = 1; //!
        result[0] = WordClass.NONE;
        for (WordClass value: values()) {
            if (value.getGroup() == null) { result[i++] = value; }
        }
        return result;
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
