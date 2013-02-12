package sk.portugal.leksi.mirror.processing;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.Meaning;
import sk.portugal.leksi.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MirroringProcessor {

    /**
     * Cleanse words, combine split lines (##), remove brackets
     *
     * @param words
     * @return list of cleaned words
     */
    public static void cleanseTranslations(List<Word> words) {
        List<Integer> linesToRemove = new ArrayList<>();

        //combine split lines
        for (Word tr: words) {
            for (int i =  tr.getWordTypes().get(0).getMeanings().size() - 1; i >= 0; i--) {
                Meaning se = tr.getWordTypes().get(0).getMeanings().get(i);
                if (se.getSynonyms().startsWith("##")) {
                    Meaning prev = tr.getWordTypes().get(0).getMeanings().get(i - 1);

                    //combine, but remove starting "##"
                    prev.setSynonyms(prev.getSynonyms().trim() + " " + se.getSynonyms().substring(2).trim());

                    //mark "line" to be removed
                    linesToRemove.add(i);
                }
                removeMarkedLines(tr, linesToRemove);
            }
        }

        //remove brackets and other stuff
        for (Word tr: words) {
            for (int i =  tr.getWordTypes().get(0).getMeanings().size() - 1; i >= 0; i--) {
                Meaning se = tr.getWordTypes().get(0).getMeanings().get(i);
                String cleaned = replaceCharactedCodes(se.getSynonyms());
                cleaned = cleaned.replaceAll("\\s*[(].*?[)]\\s*", "").trim(); //remove brackets
                cleaned = cleaned.replaceAll("[?#]", "").trim(); //remove question marks and hash chars
                cleaned = cleaned.replaceAll(".*[+].*", "").trim(); //remove lines with plus sign
                se.setSynonyms(cleaned);

                //if any "empty" lines appear, mark sensus for removal
                if (se.getSynonyms().trim().isEmpty()) linesToRemove.add(i);
            }
            removeMarkedLines(tr, linesToRemove);
        }

        //remove words with empty meanings
        for (int i = words.size() - 1; i >= 0 ; i--) {
            Word tr = words.get(i);
            if (tr.getWordTypes().get(0).getMeanings().isEmpty() || !tr.getWordTypes().get(0).hasNonEmptyMeanings()) {
                linesToRemove.add(i);
            }
        }
        removeMarkedLines(words, linesToRemove);
    }

    private static String replaceCharactedCodes(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    private static void removeMarkedLines(Word tr, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                tr.getWordTypes().get(0).getMeanings().remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    private static void removeMarkedLines(List<Word> words, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                words.remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    /**
     * Construct mirrored (other direction) words
     * @param words
     * @return list of 'dirty' mirrored words
     */
    public static List<Word> getMirroredTranslations(List<Word> words) {
        List<Word> mirr = new ArrayList<>();
        for (Word tr: words) {
            for (Meaning mea: tr.getWordTypes().get(0).getMeanings()) {

                String[] spl = StringUtils.split(mea.getSynonyms(), ";,.");

                //iterate through synonyms
                for (String s: spl) {
                    Word mt = getTranslation(mirr, cleanse(s.trim())); //retrieve existing
                    if (mt == null) {
                        mt = new Word(cleanse(s.trim()));
                        if (mt.getWordTypes().isEmpty()) {
                            mt.getWordTypes().add(new WordType());
                        }
                        mt.getWordTypes().get(0).setWordClass(tr.getWordTypes().get(0).getWordClass());
                        mirr.add(mt);
                    }

                    addMeaning(mt, tr);
                }

            }
        }
        return mirr;
    }

    private static String cleanse(String str) {
        String result = str;
        //remove trailing number
        result = StringUtils.stripEnd(result, "123");
        result = result.replaceAll("^\\)\\s*", "");
        return result;
    }

    private static void  addMeaning(Word word, Word original) {
        if (word.getWordTypes().get(0).getMeanings() == null) {
            word.getWordTypes().get(0).setMeanings(new ArrayList<Meaning>());
        }
        List<Meaning> meanings = word.getWordTypes().get(0).getMeanings();

        //check if meaning already on word
        for (Meaning m: meanings) {
            if (m.getSynonyms().equalsIgnoreCase(original.getOrig())) return;
        }

        //create new meaning
        Meaning meaning = new Meaning();
        String syns = cleanse(original.getOrig());

        //adding also extra info
        if (original.getWordTypes().get(0).getWordClass() != null && original.getWordTypes().get(0).getWordClass().isNoun() && original.getWordTypes().get(0).getNumGend() != null) {
            syns = syns + " (" + original.getWordTypes().get(0).getNumGend().getKey() + ")";
        }
        meaning.setSynonyms(syns);
        meanings.add(meaning);
    }

    private static Word getTranslation(List<Word> words, String orig) {
        for (Word t: words) {
            if (t.getOrig().equalsIgnoreCase(orig)) {
                return t;
            }
        }

        return null;
    }
}
