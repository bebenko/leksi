package sk.portugal.leksi.model.enums;

import java.util.ArrayList;
import java.util.List;

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

    public String getKey() {
        return key;
    }

    public static List<Lang> getAll() {
        List<Lang> result = new ArrayList<>();
        for (Lang lang: values()) {
            if (lang != UNDEF) {
                result.add(lang);
            }
        }
        return result;
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
