package sk.portugal.leksi.util.write;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.*;

import java.util.List;

/**
 * Prints all words in readable format/layout
 */
public class Printer {

    public static void printAll(List<Word> words) {
        for (Word w: words) {
            System.out.print(w.getOrig());
            System.out.print((w.getPronunciation() != null ? " [" + w.getPronunciation() + "]" : ""));
            for (WordType wt : w.getWordTypes()) {
                System.out.print(" {«" +
                        (wt.getParadigm() != null ? "pdg:" + wt.getParadigm() + ';' : "") +
                        (wt.getNumGend() != null ? "ng:" + wt.getNumGend().getKey() + ";" : "") +
                        (wt.getWordClass() != null ? "wc:" + wt.getWordClass().getKey() : ""));
                if (wt.getForms() != null) {
                    System.out.print(" /");
                    for (Form v: wt.getForms()) {
                        System.out.print("T:" + v.getType().getKey() + " V:" + v.getValues() + "/");
                    }
                }
                System.out.print("»");
                for (Meaning s: wt.getMeanings()) {
                    System.out.print(" [" +
                            (s.getField() != null ? "fi:" + s.getField().getKey() + ";" : "") +
                            (s.getStyle() != null ? "st:" + s.getStyle().getKey() + ";" : "") +
                            s.getSynonyms()); //s.getSynonymsSpec() + ":: " + s.getSynonymsSyn();
                    if (s.getExpressions() != null && !s.getExpressions().isEmpty()) {
                        System.out.print(" EXPR: ");
                        for (Phraseme p : s.getExpressions()) {
                            System.out.print("<" +
                                    (p.getField() != null ? "fi:" + p.getField().getKey() + ";" : "") +
                                    (p.getStyle() != null ? "st:" + p.getStyle().getKey() + ";" : "") +
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
                            (idiom.getField() != null ? "fi:" + idiom.getField().getKey() + ";" : "") +
                            (idiom.getStyle() != null ? "st:" + idiom.getStyle().getKey() + ";" : "") +
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
