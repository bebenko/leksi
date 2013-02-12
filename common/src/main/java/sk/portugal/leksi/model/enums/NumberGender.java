package sk.portugal.leksi.model.enums;

import java.io.Serializable;

/**
 */
public enum NumberGender implements Serializable {

    NONE (Integer.valueOf(0), ""),
    F (Integer.valueOf(1), "f"),
    FPL (Integer.valueOf(2), "f/pl"),
    M (Integer.valueOf(3), "m"),
    MPL (Integer.valueOf(4), "m/pl"),
    MF (Integer.valueOf(5), "m/f"),
    MFPL (Integer.valueOf(6), "m/f/pl"),
    PL (Integer.valueOf(7), "pl"),
    N (Integer.valueOf(8), "n"),
    NPL (Integer.valueOf(9), "n/pl"),

    UNDEF (Integer.valueOf(99), "undef");

    private Integer id;
    private String key;

    private NumberGender(Integer id, String key) {
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
    public static NumberGender valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        NumberGender[] values = values();
        for (NumberGender value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        //throw new IllegalArgumentException("NumberGender constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }
}
