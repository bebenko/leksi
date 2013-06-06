package sk.portugal.leksi.util;

import org.apache.commons.io.FileUtils;
import sk.portugal.leksi.model.Homonym;
import sk.portugal.leksi.model.Word;
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

    public static List<Homonym> addExtraWords(Lang lang) {
        List<Homonym> homonyms = new ArrayList<>();

        if (lang == Lang.PT) {

            //as
            Homonym as = new Homonym("as");
            as.setEnabled(false);
            Word aswt = new Word();
            aswt.setWordClass(WordClass.PRONPESS);
            aswt.setNumberGender(NumberGender.FPL);
            aswt.setCaseType(CaseType.ACC);
            as.addWord(aswt);
            homonyms.add(as);

        }

        return homonyms;
    }

    private static Homonym getWord(List<Homonym> homonymList, String wrd) {
        for (Homonym w: homonymList) {
            if (w.getOrig().equals(wrd)) return w;
        }
        return null;
    }

    public static void updatePtWords(List<Homonym> homonymList) {

        //words used in contractions
        Homonym com = getWord(homonymList, "com"), a = getWord(homonymList, "a"), o = getWord(homonymList, "o"),
        em = getWord(homonymList, "em"), por = getWord(homonymList, "por"), de = getWord(homonymList, "de"),
        isso = getWord(homonymList, "isso"), isto = getWord(homonymList, "isto"), aquilo = getWord(homonymList, "aquilo"),
        esse = getWord(homonymList, "esse"), este = getWord(homonymList, "este"), aquele = getWord(homonymList, "aquele"),
        algum = getWord(homonymList, "algum"), me = getWord(homonymList, "me"), te = getWord(homonymList, "te"),
        lhe = getWord(homonymList, "lhe"), nos = getWord(homonymList, "nos"), vos = getWord(homonymList, "vos"),
        as = getWord(homonymList, "as");

        for (Homonym homonym : homonymList) {
            if (homonym.getLang() == Lang.PT) {
                switch (homonym.getOrig()) {
                    case "algum":
                    case "nenhum":
                    case "cada": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        break;
                    }
                    case "certo": { //4
                        homonym.getWords().get(2).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "o quê": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "aquilo":
                    case "isto":
                    case "isso": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.INV);
                        break;
                    }
                    case "aquele":
                    case "esse":
                    case "outro": //2
                    case "mesmo": { //2
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "este": { //2
                        homonym.getWords().get(1).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(1).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "essa": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "tal": { //5
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.SG);
                        homonym.getWords().get(2).setWordClass(WordClass.PRONQUANT);
                        break;
                    }
                    case "meu":
                    case "seu":
                    case "teu":
                    case "dele":
                    case "nosso":
                    case "vosso": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "dela": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "a": { //4
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        homonym.getWords().get(2).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(2).setCaseType(CaseType.ACC);
                        homonym.getWords().get(2).setNumberGender(NumberGender.FSG);
                        homonym.getWords().get(3).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(3).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "o": { //3
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        homonym.getWords().get(1).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(1).setCaseType(CaseType.ACC);
                        homonym.getWords().get(1).setNumberGender(NumberGender.MSG);
                        homonym.getWords().get(2).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(2).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "minha":
                    case "sua":  //2
                    case "tua": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
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
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        break;
                    }
                    case "se": {
                        homonym.getWords().get(1).setWordClass(WordClass.PRONPESS);
                        break;
                    }
                    case "eu":
                    case "nós":
                    case "tu":
                    case "você":
                    case "vós": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setCaseType(CaseType.NOM, false);
                        break;
                    }
                    case "ele": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG, false);
                        break;
                    }
                    case "ela": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "aonde": //2
                    case "onde": //2
                    case "quem": //2
                    case "quanto": { //3
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        homonym.getWords().get(1).setWordClass(WordClass.PRONREL);
                        break;
                    }
                    case "que": { //4
                        homonym.getWords().get(0).setWordClass(WordClass.PRONREL);
                        homonym.getWords().get(1).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "quê": { //2
                        homonym.getWords().get(1).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "cujo": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONREL);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "qual": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        homonym.getWords().get(0).setNumberGender(NumberGender.SG);
                        break;
                    }
                    case "o qual": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONREL);
                        homonym.getWords().get(0).setNumberGender(NumberGender.SG);
                        break;
                    }
                    case "quais": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        homonym.getWords().get(0).setNumberGender(NumberGender.PL);
                        break;
                    }
                    case "os quais": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONREL);
                        homonym.getWords().get(0).setNumberGender(NumberGender.PL);
                        break;
                    }
                    case "ambos":
                    case "vários": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MPL);
                        break;
                    }
                    case "muito": //2
                    case "pouco": //2
                    case "todo": //4
                    case "tanto": { //3
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "bastante": { //2
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        homonym.getWords().get(0).setNumberGender(NumberGender.SG);
                        break;
                    }

                    case "porque":
                    case "quando":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "porquê":
                    {
                        homonym.getWords().get(1).setWordClass(WordClass.PRONINT);
                        break;
                    }

                    case "algo":
                    case "alguém":
                    case "nada":
                    case "ninguém":
                    case "qualquer":
                    case "tudo":
                    case "outrem": {
                        //homonym.getWords().get(0).setWordClass(WordClass.PRONINDEF);
                        homonym.getWords().get(0).setWordClass(WordClass.NONE);
                        break;
                    }
                    case "um": { //4
                        homonym.getWords().get(0).setWordClass(WordClass.NUMCARD);
                        //homonym.getWords().get(3).setWordClass(WordClass.PRONINDEF);
                        //homonym.getWords().get(3).setWordClass(WordClass.NONE);
                        break;
                    }
                    case "abaixo de":
                    case "acima de": {
                        homonym.getWords().get(0).setWordClass(WordClass.LOCPREP);
                        break;
                    }
                    case "por acaso": {
                        homonym.getWords().get(0).setWordClass(WordClass.LOCADV);
                        break;
                    }


                    //CONTRACTIONS
                    case "comigo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(homonymList, "eu"), 0,
                                "so mnou"));
                        break;
                    }
                    case "connosco": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(homonymList, "nós"), 0,
                                "s nami"));
                        break;
                    }
                    case "consigo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(homonymList, "ele"), 0,
                                "so sebou, s vami (vykanie)"));
                        break;
                    }
                    case "contigo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(homonymList, "tu"), 0,
                                "s tebou"));
                        break;
                    }
                    case "convosco": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(com, 0, getWord(homonymList, "vós"), 0,
                                "s vami"));
                        break;
                    }
                    case "à": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(a, 1, a, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(a, 1, a, 3));
                        break;
                    }
                    case "ao": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(a, 1, o, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(a, 1, o, 2));
                        break;
                    }
                    case "na": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, a, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(em, 0, a, 3));
                        break;
                    }
                    case "no": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, o, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(em, 0, o, 2));
                        break;
                    }
                    case "da": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, a, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(de, 0, a, 3));
                        break;
                    }
                    case "do": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, o, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(de, 0, o, 2));
                        break;
                    }
                    case "pela": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(por, 0, a, 0));
                        homonym.getWords().get(0).getMeanings().get(1).setContraction(new Contraction(por, 0, a, 3));
                        break;
                    }
                    case "pelo": { //2
                        homonym.getWords().get(1).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(1).getMeanings().get(0).setContraction(new Contraction(por, 0, o, 0));
                        homonym.getWords().get(1).getMeanings().get(1).setContraction(new Contraction(por, 0, o, 2));
                        break;
                    }
                    case "nisso": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, isso, 0));
                        break;
                    }
                    case "nisto": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, isto, 0));
                        break;
                    }
                    case "naquilo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, aquilo, 0));
                        break;
                    }
                    case "nesse": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, esse, 0));
                        break;
                    }
                    case "neste": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, este, 1));
                        break;
                    }
                    case "naquele": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, aquele, 0));
                        break;
                    }
                    case "nalgum": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(em, 0, algum, 0));
                        break;
                    }
                    case "disso": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, isso, 0));
                        break;
                    }
                    case "disto": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, isto, 0));
                        break;
                    }
                    case "daquilo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, aquilo, 0));
                        break;
                    }
                    case "desse": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, esse, 0));
                        break;
                    }
                    case "deste": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, este, 1));
                        break;
                    }
                    case "daquele": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, aquele, 0));
                        break;
                    }
                    case "dalgum": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0, algum, 0));
                        break;
                    }
                    case "dali": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0,
                                getWord(homonymList, "ali"), 0, "odtiaľ"));
                        break;
                    }
                    case "daqui": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(de, 0,
                                getWord(homonymList, "aqui"), 0, "odtiaľto"));
                        break;
                    }
                    case "ma": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, a, 2,
                                "mi ho, mi ju"));
                        break;
                    }
                    case "ta": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, a, 2,
                                "ti ho, ti ju"));
                        break;
                    }
                    case "lha": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, a, 2,
                                "(PT: a ele) mu ho, mu ju, (PT: a ela) jej ho, jej ju, (PT: a si) vám ho, vám ju"));
                        break;
                    }
                    case "no-la": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, a, 2,
                                "nám ju, nám ho"));
                        break;
                    }
                    case "vo-la": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, a, 2,
                                "vám ju, vám ho"));
                        break;
                    }
                    case "mo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(me, 0, o, 1,
                                "mi ho, mi ju"));
                        break;
                    }
                    case "to": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(te, 0, o, 1,
                                "ti ho, ti ju"));
                        break;
                    }
                    case "lho": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(lhe, 0, o, 1,
                                "(PT: a ele) mu ho, mu ju, (PT: a ela) jej ho, jej ju, (PT: a você) vám ho, vám ju"));
                        break;
                    }
                    case "no-lo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(nos, 0, o, 1,
                                "nám ho, nám ju"));
                        break;
                    }
                    case "vo-lo": {
                        homonym.getWords().get(0).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(0).getMeanings().get(0).setContraction(new Contraction(vos, 0, o, 1,
                                "vám ho, vám ju"));
                        break;
                    }
                    case "mas": { //remove plural contraction (only singular are included)
                        homonym.getWords().get(1).setWordClass(WordClass.CONTR);
                        homonym.getWords().get(1).getMeanings().get(0).setContraction(new Contraction(me, 0, as, 0,
                                "mi ich"));
                        break;
                    }

                    //numbers
                    case "bilião":
                    case "catorze":
                    case "cem":
                    case "cento":
                    case "cinco":
                    case "cinquenta":
                    case "dez":
                    case "dezanove":
                    case "dezasseis":
                    case "dezassete":
                    case "dezoito":
                    case "dois":
                    case "doze":
                    case "duzentos":
                    case "mil":
                    case "milhão":
                    case "nove":
                    case "novecentos":
                    case "noventa":
                    case "oitenta":
                    case "oito":
                    case "oitocentos":
                    case "onze":
                    case "quarenta":
                    case "quatro":
                    case "quatrocentos":
                    case "quinhentos":
                    case "quinze":
                    case "seis":
                    case "seiscentos":
                    case "sessenta":
                    case "sete":
                    case "setecentos":
                    case "setenta":
                    case "três":
                    case "treze":
                    case "trezentos":
                    case "trinta":
                    case "vinte":
                    case "zero":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.NUMCARD);
                        break;
                    }
                    case "meio":
                    {
                        homonym.getWords().get(2).setWordClass(WordClass.NUMFRAC);
                        break;
                    }
                    case "primeiro":
                    case "segundo":
                    {
                        homonym.getWords().get(2).setWordClass(WordClass.NUMORD);
                        break;
                    }
                    case "terceiro":
                    {
                        homonym.getWords().get(1).setWordClass(WordClass.NUMORD);
                        break;
                    }
                    case "terço":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.NUMFRAC);
                        break;
                    }
                    case "quarto":
                    {
                        homonym.getWords().get(1).setWordClass(WordClass.NUMORD);
                        homonym.getWords().get(2).setWordClass(WordClass.NUMFRAC);
                        break;
                    }
                    case "milésimo":
                    {
                        homonym.getWords().get(1).setWordClass(WordClass.NUMORD);
                        homonym.getWords().get(2).setWordClass(WordClass.NUMFRAC);
                        break;
                    }
                    case "centésimo":
                    case "décimo":
                    case "nonagésimo":
                    case "nono":
                    case "oitavo":
                    case "quinto":
                    case "sétimo":
                    case "sexto":
                    case "octogésimo":
                    case "trigésimo":
                    case "quadragésimo":
                    case "quinquagésimo":
                    case "vigésimo":
                    case "septuagésimo":
                    case "sexagésimo":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.NUMORD);
                        homonym.getWords().get(1).setWordClass(WordClass.NUMFRAC);
                        break;
                    }

                    //extra
                    case "diabetes": {
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSGMPL);
                        break;
                    }
                    case "cor de rosa": {
                        homonym.getWords().get(1).setNumberGender(NumberGender.SGPL);
                        break;
                    }
                    case "grátis": {
                        homonym.getWords().get(0).setNumberGender(NumberGender.MFSGPL);
                    }
                }
            }
        }
    }

    public static void updateSkWords(List<Homonym> homonymList) {
        impList = loadFile("imp");
        perfList = loadFile("perf");
        impperfList = loadFile("impperf");
        pronimpList = loadFile("pronimp");
        pronperfList = loadFile("pronperf");

        for (Homonym homonym : homonymList) {
            if (homonym.getLang() == Lang.SK) {
                switch (homonym.getOrig()) {
                    case "ako": //2
                    case "aký":
                    case "kedy":
                    case "ktorý":
                    case "koľko":
                    case "kto":
                    case "čo":
                    case "kde": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINT);
                        break;
                    }
                    case "akýkoľvek":
                    case "ktorýkoľvek":
                    case "ktokoľvek":
                    //case "ľubovoľný":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINDEF);
                        //homonym.getWords().get(0).setWordClass(null);
                        break;
                    }
                    case "každý":
                    //case "nikto":
                    case "nič":
                    case "nijaký":
                    case "žiadny":
                    case "vždy":
                    case "všetci": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        break;
                    }
                    case "všetko": {
                        homonym.getWords().get(0).setWordClass(null);
                        break;
                    }
                    case "veľa": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        break;
                    }
                    case "sám": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.M);
                        break;
                    }
                    case "ktosi":
                    case "niekto": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONINDEF);
                        break;
                    }
                    case "niečo":
                    case "niečí":
                    case "nikto": {
                        homonym.getWords().get(0).setWordClass(null);
                        break;
                    }
                    case "niektorý":
                    case "nejaký": {
                        //homonym.getWords().get(0).setWordClass(WordClass.PRONINDEF);
                        homonym.getWords().get(0).setWordClass(WordClass.PRONQUANT);
                        break;
                    }
                    case "ja":
                    case "ty":
                    case "on":
                    case "ona":
                    case "my":
                    case "vy":
                    case "oni": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setCaseType(CaseType.NOM);
                        break;
                    }
                    case "sebe": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setCaseType(CaseType.LOC);
                        break;
                    }
                    case "ony": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setCaseType(CaseType.NOM);
                        //homonym.getWords().get(0).setNumberGender(NumberGender.FPL);
                        break;
                    }
                    case "môj":
                    case "tvoj":
                    case "svoj": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "jeho": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MNSG);
                        break;
                    }
                    case "moja":
                    case "tvoja":
                    case "svoja":
                    case "jej": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "moje":
                    case "tvoje": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.NSG);
                        break;
                    }
                    case "svoje": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.NSGPL);
                        break;
                    }
                    case "náš":
                    case "váš": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "naša":
                    case "vaša": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "naše":
                    case "vaše": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.N);
                        break;
                    }
                    case "ich": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPOSS);
                        homonym.getWords().get(0).setNumberGender(NumberGender.PL);
                        break;
                    }

                    case "ten":
                    case "tento":
                    case "tamten":
                    case "taký": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MSG);
                        break;
                    }
                    case "tá":
                    case "táto": { //case "tamtá":
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FSG);
                        break;
                    }
                    case "to":
                    case "toto":
                    case "tamto": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.NSG);
                        break;
                    }
                    case "tí":
                    case "títo": { //case "tamtí":
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.MPL);
                        break;
                    }
                    case "tie":
                    case "tieto": { //case "tamtie":
                        homonym.getWords().get(0).setWordClass(WordClass.PRONDEM);
                        homonym.getWords().get(0).setNumberGender(NumberGender.FPL);
                        break;
                    }
                    case "vás": {
                        homonym.getWords().get(0).setWordClass(WordClass.PRONPESS);
                        homonym.getWords().get(0).setCaseType(CaseType.GAL);
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
                        homonym.getWords().get(0).setWordClass(WordClass.NUMCARD);
                        break;
                    }
                    case "desatina":
                    case "stotina":
                    case "tisícina":
                    {
                        homonym.getWords().get(0).setWordClass(WordClass.NUMFRAC);
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
                        homonym.getWords().get(0).setWordClass(WordClass.NUMORD);
                        break;
                    }
                    default:
                        if (homonym.getWords().get(0).isVerb() ||
                                (homonym.getWords().size() > 1 && homonym.getWords().get(1).isVerb())) {
                            updateSkVerb(homonym);
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

    private static void updateSkVerb(Homonym verb) {
        if (impList.contains(verb.getOrig())) {
            verb.getWords().get(0).setWordClass(WordClass.VIMP);
        } else if (perfList.contains(verb.getOrig())) {
            verb.getWords().get(0).setWordClass(WordClass.VPERF);
        } else if (impperfList.contains(verb.getOrig())) {
            verb.getWords().get(0).setWordClass(WordClass.VIMPPERF);
        } else if (pronimpList.contains(verb.getOrig())) {
            verb.getWords().get(0).setWordClass(WordClass.VPRONIMP);
        } else if (pronperfList.contains(verb.getOrig())) {
            verb.getWords().get(0).setWordClass(WordClass.VPRONPERF);
        }
    }

    public static WordClass updateWordClass(Homonym tran, Word word, WordClass dbwc) {
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
