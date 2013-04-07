package sk.portugal.leksi.mirror.service;

import sk.portugal.leksi.model.Homonym;

import java.util.List;

/**
 */
public interface DictionaryService {

    public abstract List<Homonym> getAllPTWords();

    public abstract void saveAllSKWords(List<Homonym> homonyms);
}
