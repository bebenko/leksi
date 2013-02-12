package sk.portugal.leksi.loader.service;

import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;

/**
 */
public interface LoadingService {

    public abstract List<Word> loadAll(final Lang lang);
}
