package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum Style implements Serializable {

    NONE (Integer.valueOf(0), ""),
    HOVOR (Integer.valueOf(1), "hovor"),
    COL (Integer.valueOf(2), "col(!)"),
    ZVOL (Integer.valueOf(3), "zvol"),
    PREN (Integer.valueOf(4), "pren"),
    PEJOR (Integer.valueOf(5), "pejor"),
    FAM (Integer.valueOf(6), "fam"),
    VULG (Integer.valueOf(7), "vulg"),
    EXPR (Integer.valueOf(8), "expr"),
    DETS (Integer.valueOf(9), "dets"),

    UNDEF (Integer.valueOf(99), "undef");

    private Integer id;
    private String key;

    private Style(Integer id, String key) {
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
    public static Style valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        Style[] values = values();
        for (Style value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        //throw new IllegalArgumentException("Style constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }
}
