package sk.portugal.leksi.dictexp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;
//import sk.portugal.leksi.util.WordComparator;
//import sk.portugal.leksi.util.write.Printer;

public class DictExport {

    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");
        ExportService exportService = (ExportService) ctx.getBean("exportService");

        List<Word> words;

        for (Lang lang : Lang.getAll()) {
            words = loadingService.loadAll(lang);
            for (Lang explang : Lang.getAll()) {
                //Collections.sort(words, new WordComparator());
                exportService.generateV2Export(lang, explang, words);
                System.out.println("words (" + lang.getKey() + "/" + explang.getKey() + "): " + words.size());
            }
        }
        //Collections.sort(words, new WordComparator());

        //Printer.printWordList(words, false);
    }
}
