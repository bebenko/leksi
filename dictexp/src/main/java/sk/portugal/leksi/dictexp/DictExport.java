package sk.portugal.leksi.dictexp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;
//import sk.portugal.leksi.util.WordComparator;
//import sk.portugal.leksi.util.write.Printer;

public class DictExport {

    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");
        ExportService exportService = (ExportService) ctx.getBean("exportService");

        List<Homonym> homonyms;

        for (Lang lang : Lang.getAll()) {
            homonyms = loadingService.loadAll(lang);
            for (Lang explang : Lang.getAll()) {
                //Collections.sort(homonyms, new WordComparator());
                exportService.generateV2Export(lang, explang, homonyms);
                System.out.println("homonyms (" + lang.getKey() + "/" + explang.getKey() + "): " + homonyms.size());
            }
        }
        //Collections.sort(homonyms, new WordComparator());

        //Printer.printWordList(homonyms, false);
    }
}
