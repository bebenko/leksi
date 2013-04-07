package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.exception.UnsupportedLanguageException;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Dictionary {

    private Lang primaryLanguage;
    private Map<Homonym, Meaning> translations;

    private Dictionary(Lang primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
        this.translations = new HashMap<>();
    }

    public void addTranslation(Homonym homonym, Meaning meaning) {
        if (!primaryLanguage.equals(homonym.getLang())) {
            throw new UnsupportedLanguageException();
        }
        this.translations.put(homonym, meaning);
    }

    //public getAll()

}
