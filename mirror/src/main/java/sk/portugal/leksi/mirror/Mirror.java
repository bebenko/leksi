package sk.portugal.leksi.mirror;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.portugal.leksi.mirror.service.DictionaryService;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.mirror.processing.MirroringProcessor;
import sk.portugal.leksi.util.write.Printer;

import java.util.List;

public class Mirror {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/beans.xml");
        DictionaryService dictionaryService = (DictionaryService) ctx.getBean("surDictionaryService");
        List<Word> words = dictionaryService.getAllPTWords();

/*        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.setOut(out);*/

        //Printer.printAll(words);

        //some cleaning over existing translation synonyms
        MirroringProcessor.cleanseTranslations(words);
        List<Word> mirroredWords = MirroringProcessor.getMirroredTranslations(words);

        //System.out.println("");System.out.println("");System.out.println("");System.out.println("");
        //dictionaryService.saveAllSKWords(mirroredWords);
        Printer.printAll(mirroredWords);

        System.out.println("words: " + words.size());
        System.out.println("mirrored words: " + mirroredWords.size());
    }

}
