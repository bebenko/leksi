package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.CaseType;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;
import sk.portugal.leksi.model.extra.Comparison;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private List<Meaning> meanings;
    private NumberGender numberGender;
    private WordClass wordClass;
    private CaseType caseType;
    private boolean showNumberGenderAndCaseTypeInContractions;
    private List<Form> forms;
    private String paradigm;
    private NumberGender numberGender2;
    private Comparison comparison;

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
        return getNumberGender(false);
    }

    public NumberGender getNumberGender(boolean inContraction) {
        if (!inContraction || (inContraction && showNumberGenderAndCaseTypeInContractions)) {
            return numberGender;
        } else {
            return null;
        }
    }

    public void setNumberGender(NumberGender numberGender) {
        setNumberGender(numberGender, true);
    }

    public void setNumberGender(NumberGender numberGender, boolean showNumberGenderCaseTypeInContractions) {
        this.numberGender = numberGender;
        this.showNumberGenderAndCaseTypeInContractions = showNumberGenderCaseTypeInContractions;
    }

    public boolean hasNumberGender2() {
        return this.numberGender2 != null;
    }

    public NumberGender getNumberGender2() {
        return numberGender2;
    }

    public void setNumberGender2(NumberGender numberGender2) {
        this.numberGender2 = numberGender2;
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
        if (!inContraction || (inContraction && showNumberGenderAndCaseTypeInContractions)) {
            return caseType;
        } else {
            return null;
        }
    }

    public void setCaseType(CaseType caseType) {
        setCaseType(caseType, true);
    }

    public void setCaseType(CaseType caseType, boolean showNumberGenderCaseTypeInContractions) {
        this.caseType = caseType;
        this.showNumberGenderAndCaseTypeInContractions = showNumberGenderCaseTypeInContractions;
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

    public boolean isAdjective() {
        if (wordClass == null) return false;
        return wordClass.isAdjective();
    }

    public String getParadigm() {
        return paradigm;
    }

    public void setParadigm(String paradigm) {
        this.paradigm = paradigm;
    }

    public boolean hasComparison() {
        return comparison != null;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public void setComparison(Comparison comparison) {
        this.comparison = comparison;
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
            if (f.getType() != null && f.getType() != FormType.LINK && f.getType() != FormType.LINK_GRAFANT && f.getType() != FormType.LINK_GRAFDUPL
                    && f.getType() != FormType.UNDEF && f.getType() != FormType.PARTVERB
                    && f.getType() != FormType.LINK_SK_VERB_IMP
                    && f.getType() != FormType.VERBFORM && f.getType() != FormType.VREFLSA && f.getType() != FormType.VREFLSI) {
                return true;
            }
        }
        return false;
    }

    public int getFormsSize() {
        int cnt = 0;
        for (Form f : getForms()) {
            if (f.getType() != null && f.getType() != FormType.LINK && f.getType() != FormType.LINK_GRAFANT && f.getType() != FormType.LINK_GRAFDUPL
                    && f.getType() != FormType.UNDEF && f.getType() != FormType.PARTVERB
                    && f.getType() != FormType.LINK_SK_VERB_IMP
                    && f.getType() != FormType.VERBFORM && f.getType() != FormType.VREFLSA && f.getType() != FormType.VREFLSI) {
                cnt++;
            }
        }
        return cnt;
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

    public boolean hasLinkForms() {
        if (this.forms == null) return false;
        for (Form f : getForms()) {
            if (f.getType() == FormType.LINK_GRAFDUPL || f.getType() == FormType.LINK_GRAFANT || f.getType() == FormType.LINK_SK_VERB_IMP) {
                return true;
            }
        }
        return false;
    }

    public Form getLinkForm() {
        if (this.forms == null) return null;
        for (Form f : getForms()) {
            if (f.getType() == FormType.LINK_GRAFDUPL || f.getType() == FormType.LINK_GRAFANT || f.getType() == FormType.LINK_SK_VERB_IMP) {
                return f;
            }
        }
        return null;
    }

    public boolean hasGrafForm() {
        if (this.forms == null) return false;
        for (Form f : getForms()) {
            if (f.getType() == FormType.LINK_GRAFDUPL || f.getType() == FormType.LINK_GRAFANT) {
                return true;
            }
        }
        return false;
    }

    public boolean hasGrafDuplForm() {
        if (this.forms == null) return false;
        for (Form f : getForms()) {
            if (f.getType() == FormType.LINK_GRAFDUPL) {
                return true;
            }
        }
        return false;
    }

    public boolean hasClassOrNumGend(Word wt) {
        return this.getWordClass() != null || this.getCaseType() != null || this.getNumberGender() != null;
    }
}
