package sk.portugal.leksi.util.write;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.extra.Alternative;

import java.util.List;

/**
 * Prints all words in readable format/layout
 */
public class Printer {

    public static void printAll(Lang lang, List<Homonym> homonyms) {
        for (Homonym w: homonyms) {
            print(lang, w);
        }
    }

    public static void print(Lang lang, Homonym w) {
        System.out.println(getPrint(lang, w));
    }

    public static String getPrint(Lang lang, Homonym w) {
        String res = w.getOrig();
        res += (w.getPronunciation() != null ? " [" + w.getPronunciation() + "]" : "");
        if (w.getAlternatives() != null) {
            for (Alternative alt: w.getAlternatives()) {
                res += " →" + alt.getType().getPrint(lang) + ":" + alt.getValue() + " «" +
                        (alt.getNumberGender() != null ? "ng:" + alt.getNumberGender().getPrint(lang) + ";" : "") +
                        (alt.getWordClass() != null ? "wc:" + alt.getWordClass().getPrint(lang) : "") +
                        "»";
            }
        }
        for (Word wt : w.getWords()) {
            res += " {«" +
                    (wt.getParadigm() != null ? "pdg:" + wt.getParadigm() + ';' : "") +
                    (wt.getNumberGender() != null ? "ng:" + wt.getNumberGender().getPrint(lang) + ";" : "") +
                    (wt.getWordClass() != null ? "wc:" + wt.getWordClass().getPrint(lang) + ";" : "") +
                    (wt.getCaseType() != null ? "ct:" + wt.getCaseType().getPrint(lang) : "");
            if (wt.getForms() != null) {
                res += " /";
                if (w.getOrig().equals("correr")) {
                    int i = 0;
                }
                for (Form f : wt.getForms()) {
                    res += "T:" + f.getType().getPrint(lang) + " V:" + f.getValues() + "/";
                }
            }
            res += "»";
            for (Meaning s: wt.getMeanings()) {
                res += " [" +
                        (s.getFieldType() != null ? "fi:" + s.getFieldType().getPrint(lang) + ";" : "") +
                        (s.getStyle() != null ? "st:" + s.getStyle().getPrint(lang) + ";" : "") +
                        s.getSynonyms(); //s.getSynonymsSpec() + ":: " + s.getSynonymsSyn();
                if (s.getExpressions() != null && !s.getExpressions().isEmpty()) {
                    res += " EXPR: ";
                    for (Phraseme p : s.getExpressions()) {
                        res += "<" +
                                (p.getFieldType() != null ? "fi:" + p.getFieldType().getPrint(lang) + ";" : "") +
                                (p.getStyle() != null ? "st:" + p.getStyle().getPrint(lang) + ";" : "") +
                                p.getOrig() + " → " + p.getTran() + ">";
                    }
                }
                res += "]";
            }
            res += "}";
        }
        if (w.getIdioms() != null && !w.getIdioms().isEmpty()) {
            res += " IDIOMS: ";
            for (Phraseme idiom : w.getIdioms()) {
                res += "[" +
                        (idiom.getFieldType() != null ? "fi:" + idiom.getFieldType().getPrint(lang) + ";" : "") +
                        (idiom.getStyle() != null ? "st:" + idiom.getStyle().getPrint(lang) + ";" : "") +
                        idiom.getOrig() + " → " + idiom.getTran() + "]";
            }

        }
        res += "|" + "\n";
        return res;
    }

    public static void printWordList(List<Homonym> homonyms, boolean withAccents) {
        String letter = "A", compLetter;
        System.out.println(letter);

        for (Homonym w: homonyms) {
            if (withAccents) {
                compLetter = w.getOrig().substring(0, 1).toUpperCase();
            } else {
                compLetter = StringUtils.stripAccents(w.getOrig().substring(0, 1)).toUpperCase();
            }

            if (!letter.startsWith(compLetter)) {
                letter = compLetter;
                System.out.println();
                System.out.println();
                System.out.println(letter);
            }
            System.out.println(w.getOrig());
        }
    }
}
