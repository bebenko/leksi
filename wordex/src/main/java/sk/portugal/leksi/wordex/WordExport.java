package sk.portugal.leksi.wordex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.util.WordComparator;
import sk.portugal.leksi.util.write.Printer;

import java.util.Collections;
import java.util.List;

public class WordExport {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Word> words;

        for (Lang lang : Lang.getAll()) {
            words = loadingService.loadAll(lang);
            for (Lang explang : Lang.getAll()) {
                Collections.sort(words, new WordComparator());
                DocxWrite.export(lang, explang, words);
                System.out.println("words (" + lang.getKey() + "/" + explang.getKey() + "): " + words.size());
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
