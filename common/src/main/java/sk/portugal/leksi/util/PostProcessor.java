package sk.portugal.leksi.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.CaseType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;
import sk.portugal.leksi.model.extra.Contraction;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class PostProcessor {

    private static List<String> impList, perfList, impperfList, pronimpList, pronperfList;

    public static List<Word> addExtraWords(Lang lang) {
        List<Word> words = new ArrayList<>();

        if (lang == Lang.PT) {

            //as
            Word as = new Word("as");
            as.setEnabled(false);
            WordType aswt = new WordType();
            aswt.setWordClass(WordClass.PRONPESS);
            aswt.setNumGend(NumberGender.FPL);
            aswt.setCaseType(CaseType.ACC);
            as.addWordType(aswt);
            words.add(as);

        }

        return words;
    }

    private static Word getWord(List<Word> wordList, String wrd) {
        for (Word w: wordList) {
            if (w.getOrig().equals(wrd)) return w;
        }
        return null;
    }

    public static void updatePtWords(List<Word> wordList) {

        //words used in contractions
        Word com = getWord(wordList, "com"), a = getWord(wordList, "a"), o = getWord(wordList, "o"),
        em = getWord(wordList, "em"), por = getWord(wordList, "por"), de = getWord(wordList, "de"),
        isso = getWord(wordList, "isso"), isto = getWord(wordList, "isto"), aquilo = getWord(wordList, "aquilo"),
        esse = getWord(wordList, "esse"), este = getWord(wordList, "este"), aquele = getWord(wordList, "aquele"),
        algum = getWord(wordList, "algum"), me = getWord(wordList, "me"), te = getWord(wordList, "te"),
        lhe = getWord(wordList, "lhe"), nos = getWord(wordList, "nos"), vos = getWord(wordList, "vos"),
        as = getWord(wordList, "as");

        for (Word word: wordList) {
            if (word.getLang() == Lang.PT) {
                switch (word.getOrig()) {
                    case "algum":
                    case "nenhum":
                    case "cada": {
                        word.getWordTypes().get(0).setWordClass(WordClass.QUANT);
                        break;
                    }
                    case "certo": { //4
                        word.getWordTypes().get(0).setNumGend(NumberGender.SG);
                        break;
                    }
                    case "o quê": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "aquilo":
                    case "isto":
                    case "isso": {
                        word.getWordTypes().get(0).setWordClass(WordClass.DEM);
                        word.getWordTypes().get(0).setNumGend(NumberGender.INV);
                        break;
                    }
                    case "aquele":
                    case "este":
                    case "esse":
                    case "outro": { //2
                        word.getWordTypes().get(0).setWordClass(WordClass.DEM);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "tal": { //5
                        word.getWordTypes().get(0).setWordClass(WordClass.DEM);
                        word.getWordTypes().get(0).setNumGend(NumberGender.SG);
                        word.getWordTypes().get(2).setWordClass(WordClass.QUANT);
                        break;
                    }
                    case "meu":
                    case "seu":
                    case "teu":
                    case "dele":
                    case "nosso":
                    case "vosso": {
                        word.getWordTypes().get(0).setWordClass(WordClass.POSS);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "a": { //4
                        word.getWordTypes().get(0).setNumGend(NumberGender.FSG);
                        word.getWordTypes().get(2).setWordClass(WordClass.PRONPESS);
                        word.getWordTypes().get(2).setCaseType(CaseType.ACC);
                        word.getWordTypes().get(2).setNumGend(NumberGender.FSG);
                        word.getWordTypes().get(3).setWordClass(WordClass.DEM);
                        word.getWordTypes().get(3).setNumGend(NumberGender.FSG);
                        break;
                    }
                    case "o": { //3
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        word.getWordTypes().get(1).setWordClass(WordClass.PRONPESS);
                        word.getWordTypes().get(1).setCaseType(CaseType.ACC);
                        word.getWordTypes().get(1).setNumGend(NumberGender.MSG);
                        word.getWordTypes().get(2).setWordClass(WordClass.DEM);
                        word.getWordTypes().get(2).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "minha":
                    case "sua":  //2
                    case "tua": {
                        word.getWordTypes().get(0).setWordClass(WordClass.POSS);
                        word.getWordTypes().get(0).setNumGend(NumberGender.FSG);
                        break;
                    }
                    case "me":
                    case "mim":
                    case "nos":
                    case "si":
                    case "te":
                    case "ti":
                    case "vos":
                    case "lhe": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONPESS);
                        break;
                    }
                    case "se": {
                        word.getWordTypes().get(1).setWordClass(WordClass.PRONPESS);
                        break;
                    }
                    case "eu":
                    case "nós":
                    case "tu":
                    case "você":
                    case "vós": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONPESS);
                        word.getWordTypes().get(0).setCaseType(CaseType.NOM);
                        break;
                    }
                    case "ele": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONPESS);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "aonde": //2
                    case "onde": //2
                    case "quem": //2
                    case "quanto": { //3
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONINT);
                        word.getWordTypes().get(1).setWordClass(WordClass.PRONREL);
                        break;
                    }
                    case "que": { //4
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONREL);
                        word.getWordTypes().get(1).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "cujo": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONREL);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "qual": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONINT);
                        word.getWordTypes().get(0).setNumGend(NumberGender.SG);
                        break;
                    }
                    case "o qual": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONREL);
                        word.getWordTypes().get(0).setNumGend(NumberGender.SG);
                        break;
                    }
                    case "quais": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONINT);
                        word.getWordTypes().get(0).setNumGend(NumberGender.PL);
                        break;
                    }
                    case "os quais": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONREL);
                        word.getWordTypes().get(0).setNumGend(NumberGender.PL);
                        break;
                    }
                    case "ambos":
                    case "vários": {
                        word.getWordTypes().get(0).setWordClass(WordClass.QUANT);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MPL);
                        break;
                    }
                    case "muito": //2
                    case "pouco": //2
                    case "todo": //4
                    case "tanto": { //3
                        word.getWordTypes().get(0).setWordClass(WordClass.QUANT);
                        word.getWordTypes().get(0).setNumGend(NumberGender.MSG);
                        break;
                    }
                    case "bastante": { //2
                        word.getWordTypes().get(0).setWordClass(WordClass.QUANT);
                        word.getWordTypes().get(0).setNumGend(NumberGender.SG);
                        break;
                    }
                    case "abaixo de":
                    case "acima de": {
                        word.getWordTypes().get(0).setWordClass(WordClass.LOCPREP);
                        break;
                    }
                    case "por acaso": {
                        word.getWordTypes().get(0).setWordClass(WordClass.LOCADV);
                        break;
                    }


                    //CONTRACTIONS
                    case "comigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "eu"), 0,
                                "so mnou"));
                        break;
                    }
                    case "connosco": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "nós"), 0,
                                "s nami"));
                        break;
                    }
                    case "consigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "ele"), 0,
                                "so sebou, s vami (vykanie)"));
                        break;
                    }
                    case "contigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "tu"), 0,
                                "s tebou"));
                        break;
                    }
                    case "convosco": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "vós"), 0,
                                "s vami"));
                        break;
                    }
                    case "à": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(a, 1, a, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(a, 1, a, 3));
                        break;
                    }
                    case "ao": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(a, 1, o, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(a, 1, o, 2));
                        break;
                    }
                    case "na": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, a, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(em, 0, a, 3));
                        break;
                    }
                    case "no": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, o, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(em, 0, o, 2));
                        break;
                    }
                    case "da": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, a, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(de, 0, a, 3));
                        break;
                    }
                    case "do": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, o, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(de, 0, o, 2));
                        break;
                    }
                    case "pela": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(por, 0, a, 0));
                        word.getWordTypes().get(0).getMeanings().get(1).setContraction(new Contraction(por, 0, a, 3));
                        break;
                    }
                    case "pelo": { //2
                        word.getWordTypes().get(1).setWordClass(null);
                        word.getWordTypes().get(1).getMeanings().get(0).setContraction(new Contraction(por, 0, o, 0));
                        word.getWordTypes().get(1).getMeanings().get(1).setContraction(new Contraction(por, 0, o, 2));
                        break;
                    }
                    case "nisso": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, isso, 0));
                        break;
                    }
                    case "nisto": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, isto, 0));
                        break;
                    }
                    case "naquilo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, aquilo, 0));
                        break;
                    }
                    case "nesse": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, esse, 0));
                        break;
                    }
                    case "neste": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, este, 0));
                        break;
                    }
                    case "naquele": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, aquele, 0));
                        break;
                    }
                    case "nalgum": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, algum, 0));
                        break;
                    }
                    case "disso": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, isso, 0));
                        break;
                    }
                    case "disto": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, isto, 0));
                        break;
                    }
                    case "daquilo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, aquilo, 0));
                        break;
                    }
                    case "desse": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, esse, 0));
                        break;
                    }
                    case "deste": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, este, 0));
                        break;
                    }
                    case "daquele": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, aquele, 0));
                        break;
                    }
                    case "dalgum": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, algum, 0));
                        break;
                    }
                    case "ma": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, a, 1,
                                "mi ho, mi ju"));
                        break;
                    }
                    case "ta": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, a, 1,
                                "ti ho, ti ju"));
                        break;
                    }
                    case "lha": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, a, 1,
                                "(PT: a ele) mu ho, mu ju, (PT: a ela) jej ho, jej ju, (PT: a você) vám ho, vám ju"));
                        break;
                    }
                    case "no-la": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, a, 1,
                                "nám ju, nám ho"));
                        break;
                    }
                    case "vo-la": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, a, 1,
                                "vám ju, vám ho"));
                        break;
                    }
                    case "mo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, o, 1,
                                "mi ho, mi ju"));
                        break;
                    }
                    case "to": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, o, 1,
                                "ti ho, ti ju"));
                        break;
                    }
                    case "lho": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, o, 1,
                                "(PT: a ele) mu ho, mu ju, (PT: a ela) jej ho, jej ju, (PT: a você) vám ho, vám ju"));
                        break;
                    }
                    case "no-lo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, o, 1,
                                "nám ho, nám ju"));
                        break;
                    }
                    case "vo-lo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, o, 1,
                                "vám ho, vám ju"));
                        break;
                    }
                    case "mas": { //remove plural contraction (only singular are included)
                        word.getWordTypes().get(1).setWordClass(null);
                        word.getWordTypes().get(1).getMeanings().get(0).setContraction(new Contraction(me, 0, as, 0,
                                "mi ich"));
                        break;
                    }

                }
            }
        }
    }

    public static void updateSkWords(List<Word> wordList) {
        impList = loadFile("imp");
        perfList = loadFile("perf");
        impperfList = loadFile("impperf");
        pronimpList = loadFile("pronimp");
        pronperfList = loadFile("pronperf");

        for (Word word: wordList) {
            if (word.getLang() == Lang.SK) {
                switch (word.getOrig()) {
                    case "ako":
                    case "aký": {
                        word.getWordTypes().get(0).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "akýkoľvek":
                    case "ktorýkoľvek":
                    case "ktokoľvek":
                    {
                        word.getWordTypes().get(0).setWordClass(null);
                        break;
                    }

                    //NUMBERS
                    case "nula":
                    case "jeden":
                    case "dva":
                    case "tri":
                    case "štyri":
                    case "päť":
                    case "šesť":
                    case "sedem":
                    case "osem":
                    case "deväť":
                    case "desať":
                    case "jedenásť":
                    case "dvanásť":
                    case "trinásť":
                    case "štrnásť":
                    case "pätnásť":
                    case "šestnásť":
                    case "sedemnásť":
                    case "osemnásť":
                    case "devätnásť":
                    case "dvadsať":
                    case "tridsať":
                    case "štyridsať":
                    case "päťdesiat":
                    case "šesťdesiat":
                    case "sedemdesiat":
                    case "osemdesiat":
                    case "deväťdesiat":
                    case "sto":
                    case "dvesto":
                    case "tristo":
                    case "štyristo":
                    case "päťsto":
                    case "šesťsto":
                    case "sedemsto":
                    case "osemsto":
                    case "tisíc":
                    case "milión":
                    case "miliarda":
                    case "bilión":
                    {
                        word.getWordTypes().get(0).setWordClass(WordClass.NUMCARD);
                        break;
                    }
                    case "desatina":
                    case "stotina":
                    case "tisícina":
                    {
                        word.getWordTypes().get(0).setWordClass(WordClass.NUMFRAC);
                        break;
                    }
                    case "prvý":
                    case "druhý":
                    case "tretí":
                    case "štvrtý":
                    case "piaty":
                    case "šiesty":
                    case "siedmy":
                    case "ôsmy":
                    case "deviaty":
                    case "desiaty":
                    {
                        word.getWordTypes().get(0).setWordClass(WordClass.NUMORD);
                        break;
                    }
                    default:
                        if (word.getWordTypes().get(0).isVerb() ||
                                (word.getWordTypes().size() > 1 && word.getWordTypes().get(1).isVerb())) {
                            updateSkVerb(word);
                        }
                }
            }
        }
    }

    private static List<String> loadFile(String filename) {
        List<String> result = new ArrayList<>(), lines = null;
        try {
            lines = FileUtils.readLines(new File(filename + ".txt"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line: lines) {
            if (!StringHelper.EMPTY.equals(line))
                result.add(line);
        }
        return result;
    }

    private static void updateSkVerb(Word verb) {
        //TODO exceptions - 2nd word type (strip numbers?)
        if (impList.contains(verb.getOrig())) {
            verb.getWordTypes().get(0).setWordClass(WordClass.VIMP);
        } else if (perfList.contains(verb.getOrig())) {
            verb.getWordTypes().get(0).setWordClass(WordClass.VPERF);
        } else if (impperfList.contains(verb.getOrig())) {
            verb.getWordTypes().get(0).setWordClass(WordClass.VIMPPERF);
        } else if (pronimpList.contains(verb.getOrig())) {
            verb.getWordTypes().get(0).setWordClass(WordClass.VPRONIMP);
        } else if (pronperfList.contains(verb.getOrig())) {
            verb.getWordTypes().get(0).setWordClass(WordClass.VPRONPERF);
        }
    }

    public static WordClass updateWordClass(Word tran, WordType wordType, WordClass dbwc) {
        //special overwrite for particip. in PT (part - particle (SK), pt - particip (PT))
        if (tran.getLang() == Lang.PT) {
            switch (dbwc) {
                case PT: {
                    return WordClass.P;
                }
            }
        } else if (tran.getLang() == Lang.SK) {
            switch (dbwc) {
                case PT: {
                    return WordClass.PART;
                }
            }

        }
        return dbwc;
    }

}
