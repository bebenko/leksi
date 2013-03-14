package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Form;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.PhrasemeType;
import sk.portugal.leksi.model.enums.WordClass;

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
        if (StringUtils.equals(s, StringHelper.PLINV)) {
            wt.setNumGend(NumberGender.SGPL);
        } else if (StringUtils.startsWith(s, "forma ")) {
            wt.addForm(new Form(FormType.VERBFORM, StringUtils.substringAfter(s, "forma ")));
        } else if (StringUtils.startsWithAny(s, "f ", "pl ", "p ", "pp ")) {
            Form f = new Form();
            String[] ss = StringUtils.split(s, ",");

            //set first
            f.setType(FormType.valueOfKey(StringUtils.substringBefore(ss[0], " ")));
            f.setValues(StringUtils.substringAfter(ss[0], " "));
            wt.addForm(f);
            Form saveLast = f;

            for (int i = 1; i < ss.length; i++) {
                f = new Form();
                if (FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")) != FormType.UNDEF) {
                    f.setType(FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")));
                    f.setValues(StringUtils.substringAfter(StringUtils.trimToEmpty(ss[i]), " "));
                    wt.addForm(f);
                } else if (StringUtils.isNotBlank(StringUtils.trimToEmpty(ss[i]))) {
                    saveLast.setValues(saveLast.getValues() + ", " + StringUtils.substringAfter(ss[i], " "));
                }
                saveLast = f;
            }
        } else if (StringUtils.isNotBlank(s)) {
            s = StringUtils.trimToEmpty(s);
            if (s.equals(StringHelper.PRONOMINAL)) {
                wt.setWordClass(WordClass.VPRON);
            } else if (s.equals("*") || s.equals(".") || s.equals("#")) {
                //System.out.println(w.getOrig() + " " + s);
            } else if (wt.getWordClass() != null && (wt.getWordClass() == WordClass.P || wt.getWordClass() == WordClass.PP)) {
                wt.addForm(new Form(FormType.PARTVERB, s));
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
