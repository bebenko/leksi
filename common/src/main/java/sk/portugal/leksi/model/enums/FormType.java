package sk.portugal.leksi.model.enums;

/**
 */
public enum FormType {

    NONE (Integer.valueOf(0), "", ""),
    PP (Integer.valueOf(1), "pp", "p. p."),
    F (Integer.valueOf(2), "f", "f"),
    PL (Integer.valueOf(3), "pl", "pl"),
    PLINV (Integer.valueOf(4), "pl inv", "pl inv"),

    PRON (Integer.valueOf(9), "pron", ""),

    UNDEF (Integer.valueOf(99), "undef", "");

    private Integer id;
    private String key;
    private String print;

    private FormType(Integer id, String key, String print) {
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
    public static FormType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        FormType[] values = values();
        for (FormType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        //throw new IllegalArgumentException("NumberGender constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }

    public static FormType valueOfKey(String strValue) {
        if (strValue == null) { return null; }

        FormType[] values = values();
        for (FormType value: values) {
            if (value.key.equals(strValue)) { return value; }
        }

        //throw new IllegalArgumentException("NumberGender constant with id " + intValue.intValue() + " doesn't exist.");
        return UNDEF;
    }
}
