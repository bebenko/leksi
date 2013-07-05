package sk.portugal.leksi.model.extra;

import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.Word;

/**
 */
public class Contraction {

    private Homonym first;
    private int firstWordIndex;

    private Homonym second;
    private int secondWordIndex;

    private String additionalText;

    public Contraction(Homonym first, int firstWordIndex, Homonym second, int secondWordIndex) {
        this.first = first;
        this.firstWordIndex = firstWordIndex;
        this.second = second;
        this.secondWordIndex = secondWordIndex;
    }

    public Contraction(Homonym first, int firstWordIndex, Homonym second, int secondWordIndex, String additionalText) {
        this(first, firstWordIndex, second, secondWordIndex);
        this.additionalText = additionalText;
    }

    public Homonym getFirstHomonym() {
        return first;
    }

    public Homonym getSecondHomonym() {
        return second;
    }

    public Word getFirstWord() {
        return first.getWords().get(firstWordIndex);
    }

    public Word getSecondWord() {
        return second.getWords().get(secondWordIndex);
    }

    public String getAdditionalText() {
        return additionalText;
    }

}
