package sk.portugal.leksi.model.enums;

/**
 */
public enum AltType  {

    ALTERNATIVE (Integer.valueOf(1), ""),
    OLD_ORTOGRAPHY (Integer.valueOf(2), ""),

    UNDEF (Integer.valueOf(99), "");

    private Integer id;
    private String key;

    private AltType(Integer id, String key) {
        this.id = id;
        this.key = key;
    }

    /**
     * Method translates identifier into the Enum entity.
     *
     * @param intValue Identifier.
     * @return Enum entity.
     */
    public static AltType valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        AltType[] values = values();
        for (AltType value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

}