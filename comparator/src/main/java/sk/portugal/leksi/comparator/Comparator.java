package sk.portugal.leksi.comparator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.comparator.service.CompareService;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.enums.Lang;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Comparator {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");
        LoadingService loadingService2 = (LoadingService) ctx.getBean("loadingService2");
        CompareService compareService = (CompareService) ctx.getBean("compareService");

        File out = new File("compr.txt");
        try {
            FileUtils.writeStringToFile(out, "", "UTF-8", false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Lang lang: Lang.getAll()) {
            List<Homonym> homonyms = loadingService.loadAll(lang);
            List<Homonym> words2 = loadingService2.loadAll(lang);
            //Printer.printAll(homonyms);
            //System.out.println("homonyms: " + homonyms.size());
            //System.out.println("words2: " + words2.size());

            compareService.compare(homonyms, words2, out);

            try {
                FileUtils.writeStringToFile(out, "\n\n" + StringUtils.repeat("X", 100) + "\n\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
