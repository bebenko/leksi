package sk.portugal.leksi.mirror.processing;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.Meaning;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MirroringProcessor {

    /**
     * Cleanse homonyms, combine split lines (##), remove brackets
     *
     * @param homonyms
     * @return list of cleaned homonyms
     */
    public static void cleanseTranslations(List<Homonym> homonyms) {
        List<Integer> linesToRemove = new ArrayList<>();

        //combine split lines
        for (Homonym tr: homonyms) {
            for (int i =  tr.getWords().get(0).getMeanings().size() - 1; i >= 0; i--) {
                Meaning se = tr.getWords().get(0).getMeanings().get(i);
                if (se.getSynonyms().startsWith("##")) {
                    Meaning prev = tr.getWords().get(0).getMeanings().get(i - 1);

                    //combine, but remove starting "##"
                    prev.setSynonyms(prev.getSynonyms().trim() + " " + se.getSynonyms().substring(2).trim());

                    //mark "line" to be removed
                    linesToRemove.add(i);
                }
                removeMarkedLines(tr, linesToRemove);
            }
        }

        //remove brackets and other stuff
        for (Homonym tr: homonyms) {
            for (int i =  tr.getWords().get(0).getMeanings().size() - 1; i >= 0; i--) {
                Meaning se = tr.getWords().get(0).getMeanings().get(i);
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

        //remove homonyms with empty meanings
        for (int i = homonyms.size() - 1; i >= 0 ; i--) {
            Homonym tr = homonyms.get(i);
            if (tr.getWords().get(0).getMeanings().isEmpty() || !tr.getWords().get(0).hasNonEmptyMeanings()) {
                linesToRemove.add(i);
            }
        }
        removeMarkedLines(homonyms, linesToRemove);
    }

    private static String replaceCharactedCodes(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    private static void removeMarkedLines(Homonym tr, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                tr.getWords().get(0).getMeanings().remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    private static void removeMarkedLines(List<Homonym> homonyms, List<Integer> linesToRemove) {
        if (!linesToRemove.isEmpty()) {
            for (Integer ii: linesToRemove) {
                homonyms.remove((int)ii);
            }
            linesToRemove.clear();
        }
    }

    /**
     * Construct mirrored (other direction) homonyms
     * @param homonyms
     * @return list of 'dirty' mirrored homonyms
     */
    public static List<Homonym> getMirroredTranslations(List<Homonym> homonyms) {
        List<Homonym> mirr = new ArrayList<>();
        for (Homonym tr: homonyms) {
            for (Meaning mea: tr.getWords().get(0).getMeanings()) {

                String[] spl = StringUtils.split(mea.getSynonyms(), ";,.");

                //iterate through synonyms
                for (String s: spl) {
                    Homonym mt = getTranslation(mirr, cleanse(s.trim())); //retrieve existing
                    if (mt == null) {
                        mt = new Homonym(cleanse(s.trim()));
                        if (mt.getWords().isEmpty()) {
                            mt.getWords().add(new Word());
                        }
                        mt.getWords().get(0).setWordClass(tr.getWords().get(0).getWordClass());
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

    private static void  addMeaning(Homonym homonym, Homonym original) {
        if (homonym.getWords().get(0).getMeanings() == null) {
            homonym.getWords().get(0).setMeanings(new ArrayList<Meaning>());
        }
        List<Meaning> meanings = homonym.getWords().get(0).getMeanings();

        //check if meaning already on homonym
        for (Meaning m: meanings) {
            if (m.getSynonyms().equalsIgnoreCase(original.getOrig())) return;
        }

        //create new meaning
        Meaning meaning = new Meaning();
        String syns = cleanse(original.getOrig());

        //adding also extra info
        if (original.getWords().get(0).getWordClass() != null && original.getWords().get(0).getWordClass().isNoun() && original.getWords().get(0).getNumberGender() != null) {
            syns = syns + " (" + original.getWords().get(0).getNumberGender().getKey() + ")";
        }
        meaning.setSynonyms(syns);
        meanings.add(meaning);
    }

    private static Homonym getTranslation(List<Homonym> homonyms, String orig) {
        for (Homonym t: homonyms) {
            if (t.getOrig().equalsIgnoreCase(orig)) {
                return t;
            }
        }

        return null;
    }
}
