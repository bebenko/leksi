package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.CaseType;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private List<Meaning> meanings;
    private NumberGender numberGender;
    private WordClass wordClass;
    private CaseType caseType;
    private boolean showCaseTypeInContractions;
    private List<Form> forms;
    private String paradigm;

    public Word() {}

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public void addMeaning(Meaning meaning) {
        if (meanings == null) this.meanings = new ArrayList<>();
        this.meanings.add(meaning);
    }

    public NumberGender getNumberGender() {
        return numberGender;
    }

    public void setNumberGender(NumberGender numberGender) {
        this.numberGender = numberGender;
    }

    public WordClass getWordClass() {
        return wordClass;
    }

    public void setWordClass(WordClass wordClass) {
        this.wordClass = wordClass;
    }

    public CaseType getCaseType() {
        return getCaseType(false);
    }

    public CaseType getCaseType(boolean inContraction) {
        if (inContraction && showCaseTypeInContractions) {
            return caseType;
        } else {
            return null;
        }
    }

    public void setCaseType(CaseType caseType) {
        setCaseType(caseType, true);
    }

    public void setCaseType(CaseType caseType, boolean showCaseTypeInContractions) {
        this.caseType = caseType;
        this.showCaseTypeInContractions = showCaseTypeInContractions;
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
        if (wordClass == null) return false;
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
        for (Form f : getForms()) {
            if (f.getType() != null && f.getType() != FormType.LINK && f.getType() != FormType.LINK_GRAFANT
                    && f.getType() != FormType.UNDEF && f.getType() != FormType.PARTVERB
                    && f.getType() != FormType.LINK_SK_VERB_IMP
                    && f.getType() != FormType.VERBFORM && f.getType() != FormType.VREFLSA && f.getType() != FormType.VREFLSI) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVerbForm() {
        if (this.forms == null) return false;
        for (Form f : getForms()) {
            if (f.getType() == FormType.VERBFORM || f.getType() == FormType.VREFLSA || f.getType() == FormType.VREFLSI) {
                return true;
            }
        }
        return false;
    }

    public boolean hasClassOrNumGend(Word wt) {
        return this.getWordClass() != null || this.getCaseType() != null || this.getNumberGender() != null;
    }
}
