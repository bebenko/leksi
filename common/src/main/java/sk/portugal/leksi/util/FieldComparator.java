package sk.portugal.leksi.util;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.enums.FieldType;

import java.util.Comparator;

public class FieldComparator implements Comparator<FieldType> {

    private static String comptype = null;

    public FieldComparator() {}

    public FieldComparator(String comptype) {
        this.comptype = comptype;
    }

    @Override
    public int compare(FieldType f1, FieldType f2) {
        if (comptype != null && comptype == "ord")
            return Integer.valueOf(f1.ordinal()).compareTo(Integer.valueOf(f2.ordinal()));
        return StringUtils.stripAccents(f1.getKey()).compareTo(StringUtils.stripAccents(f2.getKey()));
    }
}
