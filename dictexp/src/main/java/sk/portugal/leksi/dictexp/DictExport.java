package sk.portugal.leksi.dictexp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.loader.service.LoadingService;
//import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;
//import sk.portugal.leksi.util.WordComparator;
//import sk.portugal.leksi.util.write.Printer;

//import java.util.Collections;
//import java.util.List;

public class DictExport {

    //private static final Lang lang = Lang.SK;

    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");
        ExportService exportService = (ExportService) ctx.getBean("exportService");

        //List<Word> words = loadingService.loadAll(lang);

        //Collections.sort(words, new WordComparator());

        exportService.generateV2Export(loadingService.loadAll(Lang.PT));
        exportService.generateV2Export(loadingService.loadAll(Lang.SK));
        //Printer.printWordList(words, false);
    }
}