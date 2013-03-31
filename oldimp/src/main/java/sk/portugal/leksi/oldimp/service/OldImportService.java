package sk.portugal.leksi.oldimp.service;

import java.util.List;

/**
 */
public interface OldImportService {

    public void generateImport(List<String> wordList);

    public void generateVPronMerge(List<String> wordList);
}
