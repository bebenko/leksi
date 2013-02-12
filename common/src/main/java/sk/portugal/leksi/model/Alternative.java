package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.AltType;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;

/**
 */
public class Alternative {

    private AltType type;
    private String value;
    private WordClass wordClass;
    private NumberGender numberGender;


    public AltType getType() {
        return type;
    }

    public void setType(AltType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public WordClass getWordClass() {
        return wordClass;
    }

    public void setWordClass(WordClass wordClass) {
        this.wordClass = wordClass;
    }

    public NumberGender getNumberGender() {
        return numberGender;
    }

    public void setNumberGender(NumberGender numberGender) {
        this.numberGender = numberGender;
    }
}
