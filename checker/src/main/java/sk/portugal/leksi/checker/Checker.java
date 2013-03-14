package sk.portugal.leksi.checker;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.AltType;
import sk.portugal.leksi.model.enums.FieldType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.extra.Alternative;
import sk.portugal.leksi.util.FieldComparator;
import sk.portugal.leksi.util.helper.StringHelper;

import java.util.*;

public class Checker {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Word> ptWords = loadingService.loadAll(Lang.PT);

        //checkOldOrthography(ptWords);

        //listFieldsUsed(ptWords);

        listParentheses(ptWords);

    }

    private static void listParentheses(List<Word> wordList) {
        String[] strings = new String[] {"(imp.)", "(perf.)", "(imp./perf.)", "(f)", "(m)", "(m/f)", "(f/pl)", "(m/pl)", "(@ ", "(## ", "(-"};
        for (Word w: wordList) {
            for (WordType wt: w.getWordTypes()) {
                if (wt.getMeanings() != null) {
                    for (Meaning m: wt.getMeanings()) {
                        String str = m.getSynonyms().substring(1), s;
                        while (StringUtils.isNotEmpty(str)) {
                            if (str.contains(StringHelper.LEFTPARENTHESIS)) {
                                str = str.substring(str.indexOf(StringHelper.LEFTPARENTHESIS));
                            } else {
                                str = "";
                                continue;
                            }
                            s = str.substring(0, StringHelper.findMatchingBracket(str));
                            if (!StringUtils.startsWithAny(s, strings))
                                System.out.println(w.getOrig() + " - " + s);
                            str = StringUtils.removeStart(str, s);

                        }
                    }
                }
            }
        }
    }

    private static void mapIns(Map<FieldType, List<Word>> map, FieldType f, Word w) {
        if (map.containsKey(f)) {
            map.get(f);
        }
    }

    public static void listFieldsUsed(List<Word> words) {
        Set<FieldType> fieldTypes = new TreeSet<>(new FieldComparator("ord"));
        //Map<FieldType, List<Word>> map
        //Map<FieldType, List<Word>> map = new HashMap<>();
        for (Word w: words) {
            for (WordType wt: w.getWordTypes()) {
                for (Meaning m: wt.getMeanings()) {
                    if (m.getFieldType() != null) {
                        fieldTypes.add(m.getFieldType());
                        //mapIns(map, m.getFieldType(), w);
                        if (m.getFieldType() == FieldType.TECH) System.out.println("XXX: " + w.getOrig());
                    }
                }
            }
        }

        int count = 0;
        for (FieldType f: fieldTypes) {
            count++;
            System.out.println(StringUtils.leftPad(f.getId().toString(), 2) + " "
                    + StringUtils.rightPad(f.getPrint(Lang.SK), 10));
        }
        System.out.println("count: " + count);
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
                    if (alt.getType() == AltType.OLD_ORTHOGRAPHY) {
                        ortho.add(alt.getValue());
                    }
                }
            }
        }
        return ortho;
    }
}
