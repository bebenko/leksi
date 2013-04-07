package sk.portugal.leksi.mirror;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.mirror.service.DictionaryService;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.mirror.processing.MirroringProcessor;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.util.write.Printer;

import java.util.List;

public class Mirror {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        DictionaryService dictionaryService = (DictionaryService) ctx.getBean("surDictionaryService");
        List<Homonym> homonyms = dictionaryService.getAllPTWords();

/*        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.setOut(out);*/

        //Printer.printAll(homonyms);

        //some cleaning over existing translation synonyms
        MirroringProcessor.cleanseTranslations(homonyms);
        List<Homonym> mirroredHomonyms = MirroringProcessor.getMirroredTranslations(homonyms);

        //System.out.println("");System.out.println("");System.out.println("");System.out.println("");
        //dictionaryService.saveAllSKWords(mirroredHomonyms);
        Printer.printAll(Lang.PT, mirroredHomonyms);

        System.out.println("homonyms: " + homonyms.size());
        System.out.println("mirrored homonyms: " + mirroredHomonyms.size());
    }

}
