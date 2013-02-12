package sk.portugal.leksi.model.enums;

/**
 */
public enum Lang {

    PT (Integer.valueOf(0), "PT"),
    SK (Integer.valueOf(1), "SK"),

    UNDEF (Integer.valueOf(999), "undef");

    private Integer id;
    private String key;

    private Lang(Integer id, String key) {
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
    public static Lang valueOf(Integer intValue) {
        if (intValue == null) { return null; }

        Lang[] values = values();
        for (Lang value: values) {
            if (value.id.intValue() == intValue.intValue()) { return value; }
        }

        return UNDEF;
    }

}
