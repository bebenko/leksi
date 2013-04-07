package sk.portugal.leksi.dictexp.service;

import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;

/**
 */
public interface ExportService {

    public void generateV2Export(Lang lang, Lang explang, List<Homonym> homonyms);
}
