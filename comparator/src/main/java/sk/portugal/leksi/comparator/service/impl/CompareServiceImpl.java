package sk.portugal.leksi.comparator.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.comparator.service.CompareService;
import sk.portugal.leksi.model.Meaning;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.util.write.Printer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class CompareServiceImpl implements CompareService {

    private static final String VS = " VS ";
    private static final String NULL = "NULL";

    private static Word lastWord1 = new Word("XXXX"), lastWord2 = null;
    private static File out;

    private enum Level {
        WORD(0),
        WORDTYPE(1),
        MEANING(2);

        int level;

        Level(int level) {
            this.level = level;
        }

        int getLevel() {
            return level;
        }
    }

    private Word getWord(List<Word> wordList, String wrd) {
        for (Word w: wordList) {
            if (w.getOrig().equals(wrd)) return w;
        }
        return null;
    }

    private void compareMeanings(Word w1, Word w2, List<Meaning> meaningList1, List<Meaning> meaningList2) {
        if (meaningList1 == null && meaningList2 == null) {
            return;
        } else if ((meaningList1 == null && meaningList2 != null) || (meaningList1 != null && meaningList2 == null)) {
            print(Level.MEANING, w1, w2, "Meanings differ, one is null");
            return;
        } else if (meaningList1.size() != meaningList1.size()) {
            print(Level.MEANING, w1, w2, "Meaning sizes differ - " + meaningList1.size() + VS + meaningList1.size());
        }
        for (int i = 0; i < min(meaningList1.size(), meaningList2.size()); i++) {
            Meaning m1 = meaningList1.get(i), m2 = meaningList2.get(i);

            if (m1.getFieldType() != m2.getFieldType()) {
                print(Level.MEANING, w1, w2, i + ". Field types differ - " + (m1.getFieldType() == null ? NULL : m1.getFieldType().getKey())
                        + VS + (m2.getFieldType() == null ? NULL : m2.getFieldType().getKey()));
            }
            if (m1.getStyle() != m2.getStyle()) {
                print(Level.MEANING, w1, w2, i + ". Styles differ - " + (m1.getStyle() == null ? NULL : m1.getStyle().getKey())
                        + VS + (m2.getStyle() == null ? NULL : m2.getStyle().getKey()));
            }
            if (!m1.getSynonyms().equals(m2.getSynonyms())) {
                print(Level.MEANING, w1, w2, i + ". Synonyms differ - " + m1.getSynonyms() + VS + m2.getSynonyms());
            }
        }
    }

    private void compareWordTypes(Word w1, Word w2, List<WordType> wordTypeList1, List<WordType> wordTypeList2) {
        if (wordTypeList1 == null && wordTypeList2 == null) {
            return;
        } else if ((wordTypeList1 == null && wordTypeList2 != null) || (wordTypeList1 != null && wordTypeList2 == null)) {
            print(Level.WORDTYPE, w1, w2, "Word types differ, one is null");
            return;
        } else if (wordTypeList1.size() != wordTypeList2.size()) {
            print(Level.WORDTYPE, w1, w2, "Word type sizes differ - " + wordTypeList1.size() + " vs " + wordTypeList2.size());
        }

        for (int i = 0; i < min(wordTypeList1.size(), wordTypeList2.size()); i++) {
            WordType wt1 = wordTypeList1.get(i), wt2 = wordTypeList2.get(i);

            if (wt1.getWordClass() != wt2.getWordClass()) {
                print(Level.WORDTYPE, w1, w2, i + ". Classes differ - " + (wt1.getWordClass() == null ? NULL : wt1.getWordClass().getKey())
                        + VS + (wt2.getWordClass() == null ? NULL : wt2.getWordClass().getKey()));
            }
            if (wt1.getNumberGender() != wt2.getNumberGender()) {
                print(Level.WORDTYPE, w1, w2, i + ". Number/Gender differs - " + (wt1.getNumberGender() == null ? NULL : wt1.getNumberGender().getKey())
                        + VS + (wt2.getNumberGender() == null ? "null" : wt2.getNumberGender().getKey()));
            }
            if (wt1.getCaseType() != wt2.getCaseType()) {
                print(Level.WORDTYPE, w1, w2, i + ". Case differs - " + wt1.getCaseType().getKey() + VS + wt2.getCaseType().getKey());
            }
            //if (wt1.getParadigm())
            //if (wt1.getForms()
            compareMeanings(w1, w2, wt1.getMeanings(), wt2.getMeanings());
        }
    }

    private void doCompare(List<Word> wordList1, List<Word> wordList2) {
        for (Word w1: wordList1) {
            Word w2 = getWord(wordList2, w1.getOrig());
            if (w2 == null) {
                print(Level.WORD, w1, w2, "Word not found!");
                continue;
            }

            if (w1.getLang() != w2.getLang()) {
                print(Level.WORD, w1, w2, "Lang differs - " + w1.getLang().getKey() + VS + w2.getLang().getKey());
            }
            if (w1.getPronunciation() != null && !w1.getPronunciation().equals(w2.getPronunciation())) {
                print(Level.WORD, w1, w2, "Pronunciation differs - " + (w1.getPronunciation() == null ? NULL : w1.getPronunciation())
                        + VS + (w2.getPronunciation() == null ? NULL : w2.getPronunciation()));
            }
            compareWordTypes(w1, w2, w1.getWordTypes(), w2.getWordTypes());
            //compareIdioms(w1.getIdioms(), w2.getIdioms());
            //compareAlternatives(w1.getAlternatives(), w2.getAlternatives());
        }
    }

    @Override
    public void compare(List<Word> wordList1, List<Word> wordList2, File out) {
        this.out = out;

        doCompare(wordList1, wordList2);
    }

    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    private void print(Level lvl, Word word1, Word word2, String text) {
        try {
            if (lastWord1.getOrig() != word1.getOrig()) {
                lastWord1 = word1;
                lastWord2 = word2;
                FileUtils.writeStringToFile(out, "\n\n" + Printer.getPrint(Lang.NONE, word1) + Printer.getPrint(Lang.NONE, word2) + "\n", "UTF-8", true);
            }
            FileUtils.writeStringToFile(out, StringUtils.repeat("  ", lvl.getLevel()) + text + "\n", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
