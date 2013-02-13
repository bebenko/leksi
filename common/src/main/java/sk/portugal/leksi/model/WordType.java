package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;

import java.util.ArrayList;
import java.util.List;

public class WordType {

    private List<Meaning> meanings;
    private NumberGender numGend;
    private WordClass wordClass;
    private List<Form> forms;
    private String paradigm;

    public WordType() {}

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public NumberGender getNumGend() {
        return numGend;
    }

    public void setNumGend(NumberGender numGend) {
        this.numGend = numGend;
    }

    public WordClass getWordClass() {
        return wordClass;
    }

    public void setWordClass(WordClass wordClass) {
        this.wordClass = wordClass;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void addForm(Form form) {
        if (this.forms == null) this.forms = new ArrayList<>();
        this.forms.add(form);
    }

    public void clearVariants() {
        forms.clear();
    }

    public boolean isVerb() {
        return wordClass.isVerb();
    }

    public String getParadigm() {
        return paradigm;
    }

    public void setParadigm(String paradigm) {
        this.paradigm = paradigm;
    }

    public boolean hasNonEmptyMeanings() {
        for (Meaning m: getMeanings()) {
            if (!m.getSynonyms().isEmpty() && !m.getSynonyms().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasForms() {
        if (this.forms == null) return false;
        for (Form v: getForms()) {
            if (v.getType() != null && (/*v.getType() != FormType.PRON ||*/ v.getType() != FormType.UNDEF)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasClassOrNumGend(WordType wt) {
        return this.getWordClass() != null || this.getNumGend() != null;
    }
}
