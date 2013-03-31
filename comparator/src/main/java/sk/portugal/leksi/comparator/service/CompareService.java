package sk.portugal.leksi.comparator.service;

import sk.portugal.leksi.model.Word;

import java.io.File;
import java.util.List;

/**
 */
public interface CompareService {

    public void compare(List<Word> wordList1, List<Word> wordList2, File out);
}
