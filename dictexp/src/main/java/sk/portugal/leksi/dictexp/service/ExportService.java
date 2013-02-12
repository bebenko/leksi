package sk.portugal.leksi.dictexp.service;

import sk.portugal.leksi.model.Word;

import java.util.List;

/**
 */
public interface ExportService {

    public void generateV2Export(List<Word> words);
}
