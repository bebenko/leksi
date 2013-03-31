package sk.portugal.leksi.wordex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.enums.WordClass;
import sk.portugal.leksi.util.WordComparator;
import sk.portugal.leksi.util.write.Printer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordExport {

    public static List<WordClass[]> wcGroups = new ArrayList<>();

    static {
        wcGroups.add(WordClass.all());
        wcGroups.add(WordClass.nouns());
        wcGroups.add(WordClass.verbs());
        wcGroups.add(WordClass.adjectives());
        wcGroups.add(WordClass.adverbs());
        wcGroups.add(WordClass.numbers());
        wcGroups.add(WordClass.pronouns());
        wcGroups.add(WordClass.participles());
        wcGroups.add(WordClass.prepositions());
        wcGroups.add(WordClass.collocations());
        wcGroups.add(WordClass.interjections());
        wcGroups.add(WordClass.conjunctions());
        wcGroups.add(WordClass.ejaculations());
        wcGroups.add(WordClass.articles());
        wcGroups.add(WordClass.others());
    }

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Word> words; int count;

        for (Lang lang : Lang.getAll()) {
            words = loadingService.loadAll(lang);
            for (Lang explang : Lang.getAll()) {
                Collections.sort(words, new WordComparator());

                for (WordClass[] wcg: wcGroups) {
                    count = DocxWrite.export(lang, explang, words, wcg);
                    System.out.println(wcg[0].getGroup() + " (" + lang.getKey() + "/" + explang.getKey() + "): " + count);
                }
            }
        }

/*        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.setOut(out);*/

        //Printer.printAll(words);
    }

}
