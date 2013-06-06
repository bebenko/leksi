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
import sk.portugal.leksi.util.WordComparator;
import sk.portugal.leksi.util.helper.StringHelper;

import java.util.*;

public class Checker {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        //List<Homonym> ptWords = loadingService.loadAll(Lang.PT);

        //checkOldOrthography(ptWords);

        //listFieldsUsed(ptWords);

        //listParentheses(ptWords);

        List<Homonym> skHomonyms = loadingService.loadAll(Lang.SK);
        Collections.sort(skHomonyms, new WordComparator());
        listSkVerbs(skHomonyms);
    }

    private static void listSkVerbs(List<Homonym> homonymList) {
        for (Homonym w: homonymList) {
            for (Word wt: w.getWords()) {
                if (wt.getWordClass() != null && wt.getWordClass().isVerb()) {
                    System.out.println(w.getOrig());
                }
            }
        }
    }

    private static void listParentheses(List<Homonym> homonymList) {
        String[] strings = new String[] {"(imp.)", "(perf.)", "(imp./perf.)", "(f)", "(m)", "(m/f)", "(f/pl)", "(m/pl)", "(@ ", "(## ", "(-"};
        for (Homonym w: homonymList) {
            for (Word wt: w.getWords()) {
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

    private static void mapIns(Map<FieldType, List<Homonym>> map, FieldType f, Homonym w) {
        if (map.containsKey(f)) {
            map.get(f);
        }
    }

    public static void listFieldsUsed(List<Homonym> homonyms) {
        Set<FieldType> fieldTypes = new TreeSet<>(new FieldComparator("ord"));
        //Map<FieldType, List<Homonym>> map
        //Map<FieldType, List<Homonym>> map = new HashMap<>();
        for (Homonym w: homonyms) {
            for (Word wt: w.getWords()) {
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

    public static void checkOldOrthography(List<Homonym> homonyms) {
        List<String> oldOrtho = getOldOrtho(homonyms);

        //check idioms
        for (Homonym w: homonyms) {
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
        for (Homonym w: homonyms) {
            for (Word wt: w.getWords()) {
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

    private static List<String> getOldOrtho(List<Homonym> homonyms) {
        List<String> ortho = new ArrayList<String>();
        for (Homonym w: homonyms) {
            if (w.getAlternatives() != null) {
                for (Alternative alt: w.getAlternatives()) {
                    if (alt.getType() == AltType.GRAFANT) {
                        ortho.add(alt.getValue());
                    }
                }
            }
        }
        return ortho;
    }
}
