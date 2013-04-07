package sk.portugal.leksi.util;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Homonym;
//import sk.portugal.leksi.model.enums.Lang;

import java.util.Comparator;
//import java.util.Locale;

public class WordComparator implements Comparator<Homonym> {

    //private Locale locale = new Locale("pt", "PT");
    private boolean withAccents = false;

    public WordComparator() {}

    public WordComparator(boolean withAccents) {
        this.withAccents = withAccents;
    }

    //TODO use Collator with Locale to compare

    @Override
    public int compare(Homonym o1, Homonym o2) {
        if (withAccents) {
            return o1.getOrig().toUpperCase().compareTo(o2.getOrig().toUpperCase());
        } else {
            return StringUtils.stripAccents(o1.getOrig().toUpperCase()).compareTo(StringUtils.stripAccents(o2.getOrig().toUpperCase()));
        }
    }
}
