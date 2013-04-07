package sk.portugal.leksi.comparator.service;

import sk.portugal.leksi.model.Homonym;

import java.io.File;
import java.util.List;

/**
 */
public interface CompareService {

    public void compare(List<Homonym> homonymList1, List<Homonym> homonymList2, File out);
}
