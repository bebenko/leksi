package sk.portugal.leksi.mirror.service;

import sk.portugal.leksi.model.Word;

import java.util.List;

/**
 */
public interface DictionaryService {

    public abstract List<Word> getAllPTWords();

    public abstract void saveAllSKWords(List<Word> words);
}
