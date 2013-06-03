package sk.portugal.leksi.model.extra;

import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.Word;

/**
 */
public class Contraction {

    private Homonym first;
    private int firstWordTypeIndex;

    private Homonym second;
    private int secondWordTypeIndex;

    private String additionalText;

    public Contraction(Homonym first, int firstWordTypeIndex, Homonym second, int secondWordTypeIndex) {
        this.first = first;
        this.firstWordTypeIndex = firstWordTypeIndex;
        this.second = second;
        this.secondWordTypeIndex = secondWordTypeIndex;
    }

    public Contraction(Homonym first, int firstWordTypeIndex, Homonym second, int secondWordTypeIndex, String additionalText) {
        this(first, firstWordTypeIndex, second, secondWordTypeIndex);
        this.additionalText = additionalText;
    }

    public Homonym getFirstHomonym() {
        return first;
    }

    public Homonym getSecondHomonym() {
        return second;
    }

    public Word getFirstWord() {
        return first.getWords().get(firstWordTypeIndex);
    }

    public Word getSecondWord() {
        return second.getWords().get(secondWordTypeIndex);
    }

    public String getAdditionalText() {
        return additionalText;
    }

}
