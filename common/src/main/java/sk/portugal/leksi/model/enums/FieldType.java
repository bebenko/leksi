package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum FieldType implements Serializable {

    NONE (Integer.valueOf(0), "n/a", "", ""),
    AFR (Integer.valueOf(59), "afr", "afric.", "afr."),
    ANAT (Integer.valueOf(1), "anat", "anat.", "anat."),
    ARCHEOL (Integer.valueOf(52), "archeol", "arqueol.", "archeol."),
    ARCHIT (Integer.valueOf(2), "archit", "arq.", "archit."),
    ASTROL (Integer.valueOf(60), "astrol", "astrol.", "astrol."),
    ASTRON (Integer.valueOf(42), "astron", "astron.", "astron."),
    BAN (Integer.valueOf(61), "ban", "", "ban."),
    BIBL (Integer.valueOf(62), "bibl", "bíbl.", "bibl."),
    BIOL (Integer.valueOf(5), "biol", "biol.", "biol."),
    BOT (Integer.valueOf(6), "bot", "bot.", "bot."),
    BRAZ (Integer.valueOf(7), "braz", "bras.", "braz."),
    CHEM (Integer.valueOf(31), "chem", "quím.", "chem."),
    CIRK (Integer.valueOf(63), "cirk", "relig.", "cirk."),
    DIV (Integer.valueOf(48), "div", "teatro", "div."),
    DOPR (Integer.valueOf(64), "dopr", "", "dopr."),
    EKOL (Integer.valueOf(65), "ekol", "", "ekol."),
    EKON (Integer.valueOf(12), "ekon", "econ", "ekon."),
    ELEKTR (Integer.valueOf(14), "elektr", "eletr.", "elektr."),
    ETNOGR (Integer.valueOf(66), "etnogr", "", "etnogr."),
    EXPR (Integer.valueOf(88), "expr", "express.", "expr."),
    FARM (Integer.valueOf(54), "farm", "farm.", "farm."),
    FILM (Integer.valueOf(43), "film", "film.", "film."),
    FILOZ (Integer.valueOf(44), "filoz", "fil.", "filoz."),
    FON (Integer.valueOf(67), "fon", "", "fon."),
    FOT (Integer.valueOf(18), "fot", "fot.", "fot."),
    FYZ (Integer.valueOf(16), "fyz", "fís.", "fyz."),
    FYZIOL (Integer.valueOf(17), "fyziol", "fisiol.", "fyziol."),
    GEOGR (Integer.valueOf(68), "geogr", "geog.", "geogr."),
    GEOL (Integer.valueOf(19), "geol", "geol.", "geol."),
    GEOM (Integer.valueOf(20), "geom", "geom.", "geom."),
    HERALD (Integer.valueOf(69), "herald", "her.", "herald."),
    HIST (Integer.valueOf(46), "hist", "hist.", "hist."),
    HIST_VOJ (Integer.valueOf(70), "hist. voj.", "", "hist. voj."),
    HUD (Integer.valueOf(26), "hud", "mús.", "hud."),
    HUT (Integer.valueOf(55), "hut", "metal.", "hut."),
    INFORM (Integer.valueOf(9), "inform", "inform.", "inform."),
    IRON (Integer.valueOf(87), "iron", "irón", "iron."),
    KART (Integer.valueOf(71), "kart", "", "kart."),
    KNIZ (Integer.valueOf(72), "kniž", "", "kniž."),
    KUCH (Integer.valueOf(11), "kuch", "cul.", "kuch."),
    LES (Integer.valueOf(73), "les", "", "les."),
    LET (Integer.valueOf(4), "let", "", "let."),
    LINGV (Integer.valueOf(22), "lingv", "ling.", "lingv."),
    LIT (Integer.valueOf(49), "lit", "lit.", "lit."),
    LOD (Integer.valueOf(74), "lod", "", "lod."),
    MASM (Integer.valueOf(75), "masm", "", "masm."),
    MAT (Integer.valueOf(23), "mat", "mat.", "mat."),
    MED (Integer.valueOf(24), "med", "med.", "med."),
    METEOR (Integer.valueOf(56), "meteor", "meteor.", "meteor."),
    MINER (Integer.valueOf(57), "miner", "min.", "miner."),
    MOTOR (Integer.valueOf(3), "motor", "mec.", "motor."),
    MYTOL (Integer.valueOf(58), "mytol", "mitol.", "mytol."),
    NAB (Integer.valueOf(32), "náb", "rel.", "náb."),
    NAMOR (Integer.valueOf(27), "námor", "náut.", "námor."),
    OBCH (Integer.valueOf(8), "obch", "com.", "obch."),
    OPT (Integer.valueOf(76), "opt", "", "opt."),
    PENAZ (Integer.valueOf(47), "peňaž", "", "peňaž."),
    POLIT (Integer.valueOf(28), "polit", "polít.", "polit."),
    POLN (Integer.valueOf(51), "poľn", "", "poľn."),
    POLOV (Integer.valueOf(77), "poľov", "", "poľov."),
    POLYGR (Integer.valueOf(35), "polygr", "tip.", "polygr."),
    POLYGR2 (Integer.valueOf(50), "polygr", "tip.", "polygr."),
    POLYGR3 (Integer.valueOf(37), "polygr", "tip.", "polygr."),
    PORT (Integer.valueOf(30), "port", "", "port."),
    POST (Integer.valueOf(78), "pošt", "", "pošt."),
    PRAV (Integer.valueOf(21), "práv", "jur.", "práv."),
    PSYCH (Integer.valueOf(29), "psych", "psic.", "psych."),
    REG (Integer.valueOf(79), "reg", "reg.", "reg."),
    SLANG (Integer.valueOf(89), "slang", "calão", "slang."),
    SPORT (Integer.valueOf(41), "šport", "desp.", "šport."),
    STAV (Integer.valueOf(10), "stav", "", "stav."),
    STROJ (Integer.valueOf(80), "stroj", "", "stroj."),
    TECH (Integer.valueOf(33), "tech", "tec.", "tech."),
    TEL (Integer.valueOf(34), "tel", "", "tel."),
    TEXT (Integer.valueOf(81), "text", "", "text."),
    UCT (Integer.valueOf(82), "účt", "", "účt."),
    UMEL (Integer.valueOf(40), "umel", "arte", "umel."),
    VETER (Integer.valueOf(83), "veter", "veter.", "veter."),
    VIN (Integer.valueOf(84), "vin", "", "vin."),
    VOD (Integer.valueOf(85), "vod", "", "vod."),
    VOJ (Integer.valueOf(25), "voj", "mil.", "voj."),
    VYTV (Integer.valueOf(53), "výtv", "", "výtv."),
    ZASTAR (Integer.valueOf(45), "zastar", "arc.", "zastar."),
    ZEL (Integer.valueOf(15), "žel", "", "žel."),
    ZOOL (Integer.valueOf(39), "zool", "zool.", "zool."),
    ZURN (Integer.valueOf(86), "žurn", "jorn.", "žurn."),

    UNDEF (Integer.valueOf(99), "undef", "", "");

    private Integer id;
    private String key;
    private String print_pt;
    private String print_sk;

    private FieldType(Integer id, String key, String print_pt, String print_sk) {
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
    public static FieldType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        FieldType[] values = values();
        for (FieldType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

    public static FieldType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        FieldType[] values = values();
        for (FieldType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        return UNDEF;
    }
}
