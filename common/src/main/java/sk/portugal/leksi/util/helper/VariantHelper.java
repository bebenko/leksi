package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Form;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.PhrasemeType;

/**
 */
public class VariantHelper {

    public static void processVariants(Word w, WordType wt, String var) {
        String s = StringUtils.trimToEmpty(var);
        if (StringUtils.isNumeric(StringUtils.substringBefore(s, " "))) {
            wt.setParadigm(StringUtils.substringBefore(s, " "));
            /*if (wt instanceof Verb) {
                ((Verb)wt).setParadigm(StringUtils.substringBefore(s, " "));
            }*/
            s = StringUtils.trimToEmpty(StringUtils.removeStart(s, StringUtils.substringBefore(s, " ")));
        }
        if (StringUtils.startsWith(s, "[")) {
            w.setPronunciation(StringUtils.substringBefore(s.substring(1), "]"));
            s = StringUtils.trimToEmpty(StringUtils.removeStart(s, "[" + w.getPronunciation() + "]"));
        }
        if (StringUtils.startsWithAny(s, "f ", "pl ", "pp ")) {
            Form v = new Form();
            String[] ss = StringUtils.split(s, ",");

            //set first
            v.setType(FormType.valueOfKey(StringUtils.substringBefore(ss[0], " ")));
            v.setValues(StringUtils.substringAfter(ss[0], " "));
            wt.addForm(v);
            Form saveLast = v;

            for (int i = 1; i < ss.length; i++) {
                v = new Form();
                if (FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")) != FormType.UNDEF) {
                    v.setType(FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")));
                    v.setValues(StringUtils.substringAfter(StringUtils.trimToEmpty(ss[i]), " "));
                    wt.addForm(v);
                } else if (StringUtils.isNotBlank(StringUtils.trimToEmpty(ss[i]))) {
                    saveLast.setValues(saveLast.getValues() + ", " + StringUtils.substringAfter(ss[i], " "));
                }
                saveLast = v;
            }
        } else if (StringUtils.isNotBlank(s)) {
            s = StringUtils.trimToEmpty(s);
            if (s.equals(StringHelper.PRONOMINAL)) {
                wt.addForm(new Form(FormType.PRON, s));
            } else {
                wt.addForm(new Form(s));
            }
        }
    }

    public static PhrasemeType getPhrasemeType(String orig) {
        if (StringUtils.endsWith(orig, PhrasemeType.FIX.getKey())
                || StringUtils.endsWith(orig, StringHelper.LEFTPARENTHESIS + PhrasemeType.FIX.getKey() + StringHelper.RIGHTPARENTHESIS)) {
            return PhrasemeType.FIX;
        } else if (StringUtils.endsWith(orig, PhrasemeType.SEMIFIX.getKey())
                || StringUtils.endsWith(orig, StringHelper.LEFTPARENTHESIS + PhrasemeType.SEMIFIX.getKey() + StringHelper.RIGHTPARENTHESIS)) {
            return PhrasemeType.SEMIFIX;
        }
        return PhrasemeType.NONE;
    }
}
