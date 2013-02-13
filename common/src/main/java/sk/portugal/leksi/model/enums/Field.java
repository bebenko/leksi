package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum Field implements Serializable {
    //TODO should be language dependent (as should other enums)

    NONE (Integer.valueOf(0), ""),
    AFR (Integer.valueOf(59), "afr"),
    ANAT (Integer.valueOf(1), "anat"),
    ARCHEOL (Integer.valueOf(52), "archeol"),
    ARCHIT (Integer.valueOf(2), "archit"),
    ASTROL (Integer.valueOf(60), "astrol"),
    ASTRON (Integer.valueOf(42), "astron"),
    BAN (Integer.valueOf(61), "ban"),
    BIBL (Integer.valueOf(62), "bibl"),
    BIOL (Integer.valueOf(5), "biol"),
    BOT (Integer.valueOf(6), "bot"),
    BRAZ (Integer.valueOf(7), "braz"),
    CHEM (Integer.valueOf(31), "chem"),
    CIRK (Integer.valueOf(63), "cirk"),
    DIV (Integer.valueOf(48), "div"),
    DOPR (Integer.valueOf(64), "dopr"),
    EKOL (Integer.valueOf(65), "ekol"),
    EKON (Integer.valueOf(12), "ekon"),
    ELEKTR (Integer.valueOf(14), "elektr"),
    ETNOGR (Integer.valueOf(66), "etnogr"),
    EXPR (Integer.valueOf(88), "expr"),
    FARM (Integer.valueOf(54), "farm"),
    FILM (Integer.valueOf(43), "film"),
    FILOZ (Integer.valueOf(44), "filoz"),
    FON (Integer.valueOf(67), "fon"),
    FOT (Integer.valueOf(18), "fot"),
    FYZ (Integer.valueOf(16), "fyz"),
    FYZIOL (Integer.valueOf(17), "fyziol"),
    GEOGR (Integer.valueOf(68), "geogr"),
    GEOL (Integer.valueOf(19), "geol"),
    GEOM (Integer.valueOf(20), "geom"),
    HERALD (Integer.valueOf(69), "herald"),
    HIST (Integer.valueOf(46), "hist"),
    HIST_VOJ (Integer.valueOf(70), "hist. voj"),
    HUD (Integer.valueOf(26), "hud"),
    HUT (Integer.valueOf(55), "hut"),
    INFORM (Integer.valueOf(9), "inform"),
    IRON (Integer.valueOf(87), "iron"),
    KART (Integer.valueOf(71), "kart"),
    KNIZ (Integer.valueOf(72), "kniž"),
    KUCH (Integer.valueOf(11), "kuch"),
    LES (Integer.valueOf(73), "les"),
    LET (Integer.valueOf(4), "let"),
    LINGV (Integer.valueOf(22), "lingv"),
    LIT (Integer.valueOf(49), "lit"),
    LOD (Integer.valueOf(74), "lod"),
    MASM (Integer.valueOf(75), "masm"),
    MAT (Integer.valueOf(23), "mat"),
    MED (Integer.valueOf(24), "med"),
    METEOR (Integer.valueOf(56), "meteor"),
    MINER (Integer.valueOf(57), "miner"),
    MOTOR (Integer.valueOf(3), "motor"),
    MYTOL (Integer.valueOf(58), "mytol"),
    NAB (Integer.valueOf(32), "náb"),
    NAMOR (Integer.valueOf(27), "námor"),
    OBCH (Integer.valueOf(8), "obch"),
    OPT (Integer.valueOf(76), "opt"),
    PENAZ (Integer.valueOf(47), "peňaž"),
    POLIT (Integer.valueOf(28), "polit"),
    POLN (Integer.valueOf(51), "poľn"),
    POLOV (Integer.valueOf(77), "poľov"),
    POLYGR (Integer.valueOf(35), "polygr"),
    POLYGR2 (Integer.valueOf(50), "polygr"),
    POLYGR3 (Integer.valueOf(37), "polygr"),
    PORT (Integer.valueOf(30), "port"),
    POST (Integer.valueOf(78), "pošt"),
    PRAV (Integer.valueOf(21), "práv"),
    PSYCH (Integer.valueOf(29), "psych"),
    REG (Integer.valueOf(79), "reg"),
    SLANG (Integer.valueOf(89), "slang"),
    SPORT (Integer.valueOf(41), "šport"),
    STAV (Integer.valueOf(10), "stav"),
    STROJ (Integer.valueOf(80), "stroj"),
    TECH (Integer.valueOf(33), "tech"),
    TEL (Integer.valueOf(34), "tel"),
    TEXT (Integer.valueOf(81), "text"),
    UCT (Integer.valueOf(82), "účt"),
    UMEL (Integer.valueOf(40), "umel"),
    VETER (Integer.valueOf(83), "veter"),
    VIN (Integer.valueOf(84), "vin"),
    VOD (Integer.valueOf(85), "vod"),
    VOJ (Integer.valueOf(25), "voj"),
    VYTV (Integer.valueOf(53), "výtv"),
    ZASTAR (Integer.valueOf(45), "zastar"),
    ZEL (Integer.valueOf(15), "žel"),
    ZOOL (Integer.valueOf(39), "zool"),
    ZURN (Integer.valueOf(86), "žurn"),

    UNDEF (Integer.valueOf(99), "undef");

    private Integer id;
    private String key;

    private Field(Integer id, String key) {
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

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static Field valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        Field[] values = values();
        for (Field value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        //throw new IllegalArgumentException("Field constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }
}
