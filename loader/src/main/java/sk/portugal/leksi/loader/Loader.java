package sk.portugal.leksi.loader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.loader.service.LoadingService;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.util.write.Printer;

import java.util.List;

/**
 */
public class Loader {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        LoadingService loadingService = (LoadingService) ctx.getBean("loadingService");

        List<Word> words = loadingService.loadAll(Lang.PT);
        //Printer.printAll(words);
        System.out.println("words: " + words.size());
    }
}
