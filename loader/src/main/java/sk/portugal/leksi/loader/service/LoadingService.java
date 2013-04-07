package sk.portugal.leksi.loader.service;

import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;

/**
 */
public interface LoadingService {

    public abstract List<Homonym> loadAll(final Lang lang);
}
