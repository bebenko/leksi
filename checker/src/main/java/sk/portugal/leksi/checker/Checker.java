package sk.portugal.leksi.checker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.AltType;
import sk.portugal.leksi.model.enums.Lang;

import java.util.ArrayList;
import java.util.List;

public class Checker {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Word> ptWords = loadingService.loadAll(Lang.PT);

        checkOldOrthography(ptWords);
    }

    public static void checkOldOrthography(List<Word> words) {
        List<String> oldOrtho = getOldOrtho(words);

        //check idioms
        for (Word w: words) {
            if (w.getIdioms() != null) {
                for (Phraseme id: w.getIdioms()) {
                    for (String ortho: oldOrtho) {
                        if (id.getOrig().matches(".*" + ortho + ".*")) {
                            System.out.println(w.getOrig() + " - found:" + ortho + " within:" + id.getOrig());
                        }
                    }
                }
            }
        }

        //check examples
        System.out.println("expressions");
        for (Word w: words) {
            for (WordType wt: w.getWordTypes()) {
                for (Meaning m: wt.getMeanings()) {
                    if (m.getExpressions() != null) {
                        for (Phraseme ex : m.getExpressions()) {
                            for (String ortho: oldOrtho) {
                                if (ex.getOrig().matches(".*" + ortho + ".*")) {
                                    System.out.println(w.getOrig() + " - found:" + ortho + " within:" + ex.getOrig());
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private static List<String> getOldOrtho(List<Word> words) {
        List<String> ortho = new ArrayList<String>();
        for (Word w: words) {
            if (w.getAlternatives() != null) {
                for (Alternative alt: w.getAlternatives()) {
                    if (alt.getType() == AltType.OLD_ORTOGRAPHY) {
                        ortho.add(alt.getValue());
                    }
                }
            }
        }
        return ortho;
    }
}
