package sk.portugal.leksi.model;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.enums.Field;
import sk.portugal.leksi.model.enums.PhrasemeType;
import sk.portugal.leksi.model.enums.Style;
import sk.portugal.leksi.util.helper.StringHelper;

/**
 * Single example for word usage
 */
public class Phraseme {

    private Field field;
    private Style style;
    private String orig;
    private PhrasemeType type;
    private String tran;

    public Phraseme() {}

    public Phraseme(String orig, PhrasemeType type, String tran) {
        setOrig(orig.trim());
        setType(type);
        setTran(tran.trim());
    }

    public Phraseme(String orig, PhrasemeType type, String tran, Field field, Style style) {
        this(orig, type, tran);
        this.field = field;
        this.style = style;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        setOrig(orig, true);
    }

    public void setOrig(String orig, boolean remove) {
        if (remove && orig.startsWith("(")) {
            //remove (matching) brackets
            this.orig = orig.substring(1, StringHelper.findMatchingBracket(orig) - 1)
                    + orig.substring(StringHelper.findMatchingBracket(orig));
        } else {
            this.orig = orig.trim();
        }
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran.trim();
    }

    public PhrasemeType getType() {
        return type;
    }

    public void setType(PhrasemeType type) {
        this.type = type;
    }
}
