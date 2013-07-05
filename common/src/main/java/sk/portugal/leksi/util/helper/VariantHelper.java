package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Form;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.model.extra.Comparison;

/**
 */
public class VariantHelper {

    public static void processVariants(Homonym h, Word w, String var) {
        String s = StringUtils.trimToEmpty(var);
        if (StringUtils.isNumeric(StringUtils.substringBefore(s, " "))) {
            w.setParadigm(StringUtils.substringBefore(s, " "));
            /*if (w instanceof Verb) {
                ((Verb)w).setParadigm(StringUtils.substringBefore(s, " "));
            }*/
            s = StringUtils.trimToEmpty(StringUtils.removeStart(s, StringUtils.substringBefore(s, " ")));
        }
        if (StringUtils.startsWith(s, "[")) {
            h.setPronunciation(StringUtils.substringBefore(s.substring(1), "]"));
            s = StringUtils.trimToEmpty(StringUtils.removeStart(s, "[" + h.getPronunciation() + "]"));
        }
        if (StringUtils.startsWith(s, StringHelper.LINK + StringHelper.SPACE + StringHelper.DUPL)) {
            h.getWords().get(0).addForm(new Form(FormType.LINK_GRAFDUPL, ""));
        }
        if (StringUtils.equals(s, StringHelper.PLINV)) {
            w.setNumberGender(addSgPl(w.getNumberGender()));
        } else if (StringUtils.startsWithAny(s, "comp de ", "sup de ")) {
            w.setComparison(new Comparison(ComparisonDegree.valueOfKey(StringUtils.substringBefore(s, " de ")), StringUtils.substringAfter(s, " de ")));
        } else if (StringUtils.startsWith(s, "forma ")) {
            w.addForm(new Form(FormType.VERBFORM, StringUtils.substringAfter(s, "forma ")));
        } else if (StringUtils.startsWith(s, FormType.VREFLSA.getKey())) {
            w.addForm(new Form(FormType.VREFLSA, "sa"));
        } else if (StringUtils.startsWith(s, FormType.VREFLSI.getKey())) {
            w.addForm(new Form(FormType.VREFLSI, "si"));
        } else if (StringUtils.startsWithAny(s, "m ", "f ", "n ", "pl ", "p ", "pp ", "cf ")) {
            Form f = new Form();
            String[] ss = StringUtils.split(s, ",");

            //set first
            f.setType(FormType.valueOfKey(StringUtils.substringBefore(ss[0], " ")));
            f.setValues(StringUtils.substringAfter(ss[0], " "));
            w.addForm(f);
            Form saveLast = f;

            for (int i = 1; i < ss.length; i++) {
                f = new Form();
                if (FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")) != FormType.UNDEF) {
                    f.setType(FormType.valueOfKey(StringUtils.substringBefore(StringUtils.trimToEmpty(ss[i]), " ")));
                    f.setValues(StringUtils.substringAfter(StringUtils.trimToEmpty(ss[i]), " "));
                    w.addForm(f);
                } else if (StringUtils.isNotBlank(StringUtils.trimToEmpty(ss[i]))) {
                    saveLast.setValues(saveLast.getValues() + ", " + StringUtils.substringAfter(ss[i], " "));
                }
                saveLast = f;
            }
        } else if (StringUtils.isNotBlank(s)) {
            s = StringUtils.trimToEmpty(s);
            if (s.equals(WordClass.VPRON.getKey())) {
                w.setWordClass(WordClass.VPRON);
            } else if (s.equals(WordClass.VPRONSA.getKey())) {
                w.setWordClass(WordClass.VPRONSA);
            } else if (s.equals(WordClass.VPRONSI.getKey())) {
                w.setWordClass(WordClass.VPRONSI);
            } else if (s.equals("*") || s.equals(".") || s.equals("#")) {
                //System.out.println(h.getOrig() + " " + s);
            } else if (w.getWordClass() != null && (w.getWordClass() == WordClass.P || w.getWordClass() == WordClass.PP)) {
                w.addForm(new Form(FormType.PARTVERB, s));
            } else {
                w.addForm(new Form(s));
            }
        }
    }

    private static NumberGender addSgPl(NumberGender ng) {
        NumberGender rt;
        switch (ng) {
            case F: {
                rt = NumberGender.FSGPL;
                break;
            }
            case M: {
                rt = NumberGender.MSGPL;
                break;
            }
            case MF: {
                rt = NumberGender.MFSGPL;
                break;
            }
            default:
                rt = NumberGender.SGPL;
        }
        return rt;
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
