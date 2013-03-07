package sk.portugal.leksi.util.write;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;

/**
 * Prints all words in readable format/layout
 */
public class Printer {

    public static void printAll(Lang lang, List<Word> words) {
        for (Word w: words) {
            System.out.print(w.getOrig());
            System.out.print((w.getPronunciation() != null ? " [" + w.getPronunciation() + "]" : ""));
            if (w.getAlternatives() != null) {
                for (Alternative alt: w.getAlternatives()) {
                    System.out.print(" →" + alt.getType().getPrint(lang) + ":" + alt.getValue() + " «" +
                            (alt.getNumberGender() != null ? "ng:" + alt.getNumberGender().getPrint(lang) + ";" : "") +
                            (alt.getWordClass() != null ? "wc:" + alt.getWordClass().getPrint(lang) : "") +
                            "»"
                    );
                }
            }
            for (WordType wt : w.getWordTypes()) {
                System.out.print(" {«" +
                        (wt.getParadigm() != null ? "pdg:" + wt.getParadigm() + ';' : "") +
                        (wt.getNumGend() != null ? "ng:" + wt.getNumGend().getPrint(lang) + ";" : "") +
                        (wt.getWordClass() != null ? "wc:" + wt.getWordClass().getPrint(lang) + ";" : "") +
                        (wt.getCaseType() != null ? "ct:" + wt.getCaseType().getPrint(lang) : ""));
                if (wt.getForms() != null) {
                    System.out.print(" /");
                    if (w.getOrig().equals("correr")) {
                        int i = 0;
                    }
                    for (Form f : wt.getForms()) {
                        System.out.print("T:" + f.getType().getPrint(lang) + " V:" + f.getValues() + "/");
                    }
                }
                System.out.print("»");
                for (Meaning s: wt.getMeanings()) {
                    System.out.print(" [" +
                            (s.getFieldType() != null ? "fi:" + s.getFieldType().getPrint(lang) + ";" : "") +
                            (s.getStyle() != null ? "st:" + s.getStyle().getPrint(lang) + ";" : "") +
                            s.getSynonyms()); //s.getSynonymsSpec() + ":: " + s.getSynonymsSyn();
                    if (s.getExpressions() != null && !s.getExpressions().isEmpty()) {
                        System.out.print(" EXPR: ");
                        for (Phraseme p : s.getExpressions()) {
                            System.out.print("<" +
                                    (p.getFieldType() != null ? "fi:" + p.getFieldType().getPrint(lang) + ";" : "") +
                                    (p.getStyle() != null ? "st:" + p.getStyle().getPrint(lang) + ";" : "") +
                                    p.getOrig() + " → " + p.getTran() + ">");
                        }
                    }
                    System.out.print("]");
                }
                System.out.print("}");
            }
            if (w.getIdioms() != null && !w.getIdioms().isEmpty()) {
                System.out.print(" IDIOMS: ");
                for (Phraseme idiom : w.getIdioms()) {
                    System.out.print("[" +
                            (idiom.getFieldType() != null ? "fi:" + idiom.getFieldType().getPrint(lang) + ";" : "") +
                            (idiom.getStyle() != null ? "st:" + idiom.getStyle().getPrint(lang) + ";" : "") +
                            idiom.getOrig() + " → " + idiom.getTran() + "]");
                }

            }
            System.out.println("|");
        }
    }

    public static void printWordList(List<Word> words, boolean withAccents) {
        String letter = "A", compLetter;
        System.out.println(letter);

        for (Word w: words) {
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
