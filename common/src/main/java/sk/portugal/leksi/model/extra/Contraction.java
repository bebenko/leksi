package sk.portugal.leksi.model.extra;

import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.enums.PhrasemeType;
import sk.portugal.leksi.util.helper.StringHelper;

/**
 */
public class Contraction {

    private Word first;
    private int firstWordTypeIndex;

    private Word second;
    private int secondWordTypeIndex;

    private String additionalText;

    public Contraction(Word first, int firstWordTypeIndex, Word second, int secondWordTypeIndex) {
        this.first = first;
        this.firstWordTypeIndex = firstWordTypeIndex;
        this.second = second;
        this.secondWordTypeIndex = secondWordTypeIndex;
    }

    public Contraction(Word first, int firstWordTypeIndex, Word second, int secondWordTypeIndex, String additionalText) {
        this(first, firstWordTypeIndex, second, secondWordTypeIndex);
        this.additionalText = additionalText;
    }

    public Word getFirstWord() {
        return first;
    }

    public Word getSecondWord() {
        return second;
    }

    public WordType getFirstWordType() {
        return first.getWordTypes().get(firstWordTypeIndex);
    }

    public WordType getSecondWordType() {
        return second.getWordTypes().get(secondWordTypeIndex);
    }

    public String getAdditionalText() {
        return additionalText;
    }

}
