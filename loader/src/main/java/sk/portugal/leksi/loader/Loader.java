package sk.portugal.leksi.loader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.enums.Lang;

import java.util.List;

/**
 */
public class Loader {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Homonym> homonyms = loadingService.loadAll(Lang.PT);
        //Printer.printAll(homonyms);
        System.out.println("homonyms: " + homonyms.size());
    }
}
