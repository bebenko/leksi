package sk.portugal.leksi.util;

import sk.portugal.leksi.model.Word;
import sk.portugal.leksi.model.WordType;
import sk.portugal.leksi.model.enums.CaseType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.enums.NumberGender;
import sk.portugal.leksi.model.enums.WordClass;
import sk.portugal.leksi.model.extra.Contraction;

import java.util.List;

/**
 */
public class PostProcessor {

    private static Word getWord(List<Word> wordList, String wrd) {
        for (Word w: wordList) {
            if (w.getOrig().equals(wrd)) return w;
        }
        return null;
    }

    public static void updatePtWords(List<Word> wordList) {
        Word com = getWord(wordList, "com");
        Word a = getWord(wordList, "a");
        Word o = getWord(wordList, "o");
        Word em = getWord(wordList, "em");
        Word por = getWord(wordList, "por");
        Word de = getWord(wordList, "de");
        Word isso = getWord(wordList, "isso");
        Word isto = getWord(wordList, "isto");
        Word aquilo = getWord(wordList, "aquilo");
        Word esse = getWord(wordList, "esse");
        Word este = getWord(wordList, "este");
        Word aquele = getWord(wordList, "aquele");
        Word algum = getWord(wordList, "algum");
        Word me = getWord(wordList, "me");
        Word te = getWord(wordList, "te");
        Word lhe = getWord(wordList, "lhe");
        Word nos = getWord(wordList, "nos");
        Word vos = getWord(wordList, "vos");
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
                    case "sua":
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
                    case "comigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "eu"), 0));
                        break;
                    }
                    case "connosco": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "nós"), 0));
                        break;
                    }
                    case "consigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "ele"), 0));
                        break;
                    }
                    case "contigo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "tu"), 0));
                        break;
                    }
                    case "convosco": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(wordList, "vós"), 0));
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
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, a, 1));
                        break;
                    }
                    case "ta": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, a, 1));
                        break;
                    }
                    case "lha": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, a, 1));
                        break;
                    }
                    case "no-la": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, a, 1));
                        break;
                    }
                    case "vo-la": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, a, 1));
                        break;
                    }
                    case "mo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, o, 1));
                        break;
                    }
                    case "to": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, o, 1));
                        break;
                    }
                    case "lho": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, o, 1));
                        break;
                    }
                    case "no-lo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, o, 1));
                        break;
                    }
                    case "vo-lo": {
                        word.getWordTypes().get(0).setWordClass(null);
                        word.getWordTypes().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, o, 1));
                        break;
                    }
                    case "mas": { //remove plural contraction (only singular are included)
                        word.getWordTypes().remove(1);
                        break;
                    }

                }
            }
        }
    }

    public static void updateSkWords(List<Word> wordList) {

    }

    public static WordClass updateWordClass(Word tran, WordType wordType, WordClass dbwc) {
        //special overwrite for particip. in PT (pt - particle (SK), pt - particip (PT))
        if (tran.getLang() == Lang.PT && dbwc == WordClass.PT) return WordClass.P;

        return dbwc;
    }
}
