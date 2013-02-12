package sk.portugal.leksi.model.enums;

import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public enum PhrasemeType {

    NONE (Integer.valueOf(0), "", ""),
    FIX (Integer.valueOf(1), StringHelper.EXPRFIX, StringHelper.EXPRFIX),
    SEMIFIX (Integer.valueOf(2), StringHelper.EXPRSEMIFIX, StringHelper.EXPRSEMIFIX),

    UNDEF (Integer.valueOf(99), "undef", "");

    private Integer id;
    private String key;
    private String print;

    private PhrasemeType(Integer id, String key, String print) {
        this.id = id;
        this.key = key;
        this.print = print;
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

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static PhrasemeType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        PhrasemeType[] values = values();
        for (PhrasemeType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        //throw new IllegalArgumentException("NumberGender constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }

    public static PhrasemeType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        PhrasemeType[] values = values();
        for (PhrasemeType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        //throw new IllegalArgumentException("NumberGender constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }
}
