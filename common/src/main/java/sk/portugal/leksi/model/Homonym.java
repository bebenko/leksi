package sk.portugal.leksi.model;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.extra.Alternative;
import sk.portugal.leksi.util.helper.StringHelper;
import sk.portugal.leksi.util.helper.VariantHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Homonym implements Serializable {
    private int id;
    private String orig;
    private String pronunciation;
    private Lang lang;
    private List<Word> words = new ArrayList<>();
    private List<Phraseme> idioms;
    private List<Alternative> alternatives;

    private boolean enabled;

    public Homonym() {
        this.enabled = true;
    }

    public Homonym(String orig) {
        this();
        this.orig = orig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig.trim();
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public List<Word> getWords() {
        return words;
    }

    private void setWords(List<Word> words) {
        this.words = words;
    }

    public void addWordType(Word word) {
        if (this.words == null) {
            this.words = new ArrayList<>();
        }
        this.words.add(word);
    }

    public List<Phraseme> getIdioms() {
        return idioms;
    }

    private void setIdioms(List<Phraseme> idioms) {
        this.idioms = idioms;
    }

    public void addIdiom(Phraseme idiom) {
        if (this.idioms == null) {
            this.idioms = new ArrayList<>();
        }

        if (idiom.getOrig().contains("###")) {
            String[] origSplits = StringUtils.splitByWholeSeparator(idiom.getOrig(), "###");
            String[] tranSplits = StringUtils.splitByWholeSeparator(idiom.getTran(), "###");
            for (int i = 0; i < origSplits.length; i++) {
                this.idioms.add(new Phraseme(StringHelper.removeExpr(origSplits[i].trim()), VariantHelper.getPhrasemeType(origSplits[i].trim()),
                        tranSplits[i].trim(), idiom.getFieldType(), idiom.getStyle()));
            }
        } else {
            this.idioms.add(idiom);
        }
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void addAlternative(Alternative alternative) {
        if (this.alternatives == null) {
            this.alternatives = new ArrayList<>();
        }
        this.alternatives.add(alternative);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Homonym homonym = (Homonym) o;

        if (lang != homonym.lang) return false;
        if (!orig.equals(homonym.orig)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orig.hashCode();
        result = 31 * result + lang.hashCode();
        return result;
    }

    public static Homonym createLinkedCopy(Homonym homonym, Homonym orig) {
        homonym.setOrig(homonym.getWords().get(0).getForms().get(0).getValues());
        homonym.setLang(orig.getLang());

        homonym.getWords().get(0).clearVariants();
        homonym.getWords().get(0).getMeanings().get(0).setSynonyms(StringHelper.LINK + StringHelper.SPACE + orig.getOrig());
        homonym.getWords().get(0).addForm(new Form(FormType.LINK, ""));

        return homonym;
    }

}