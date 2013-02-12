package sk.portugal.leksi.dictexp.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
//import sk.portugal.leksi.util.helper.EscapeHelper;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class V2ExportServiceImpl implements ExportService {

    private int counter = 1;

    private String escapeHtml(String str) {
        return str;
        //return EscapeHelper.escapeHtml(str);
    }

    private String addSectionStart(String cls) {
        return "<div class=\"" + cls + "\">";
    }

    private String addSectionEnd() {
        return "</div>";
    }

    private String addFmtStart(String cls) {
        return "<span class=\"" + cls + "\">";
    }

    private String addFmtEnd() {
        return "</span>";
    }

    private String addBreak() {
        return "<br/>";
    }

    private String addWord(String str) {
        return addFmtStart("wrddef") + escapeHtml(str) + addFmtEnd();
    }

    private String addPronunciation(String str) {
        return addFmtStart("pronun") + StringHelper.SPACE + StringHelper.LEFTSQUAREBRACKET
                + escapeHtml(str) + StringHelper.RIGHTSQUAREBRACKET + addFmtEnd();
    }

    private String addClassNumGend(WordType wt) {
        String res = addFmtStart("clng");
        boolean wc = false;
        if (wt.getWordClass() != null) {
            res += escapeHtml(wt.getWordClass().getKey());
            wc = true;
        }
        if (wt.getNumGend() != null) {
            if (wc) {
                res += StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getNumGend().getKey());
        }
        return res + addFmtEnd();
    }

    private String addNumber(String str) {
        return addFmtStart("meannum") + escapeHtml(str) + addFmtEnd();
    }

    /*private String addPluralMeaning(String str) {
        String res = "<span class=\"obli\">" + escapeHtml("pl") + "</span> ";
        String s = str.substring(5);
        s = s.substring(3, StringHelper.findMatchingBracket(s) - 1);
        res += "<span class=\"plmean\">" + escapeHtml(s) + "</span>";
        return res;
    }*/

    private String addFieldStyle(Field field, Style style) {
        if (field == null && style == null) return "";

        String s = addFmtStart("fiesty");
        boolean fi = false;
        if (field != null) {
            s += escapeHtml(field.getKey());
            fi = true;
        }
        if (style != null) {
            if (fi) s += escapeHtml(StringHelper.COMMASPACE);
            s += escapeHtml(style.getKey());
        }
        return s + addFmtEnd();
    }

    private String addFormattedTran(String str) {

        if (str.startsWith("(-")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            return addTranslation(StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1);
            return addFmtStart("obli") + escapeHtml(s) + addFmtEnd();
        //} else if (str.matches("\\(pl\\) \\(@.*")) { //special condition for separate plural meaning
            //return addPluralMeaning(str); //FIXME needs some workaround to modify also context around
        } else if (str.equals("(pl)")) {
            return addFmtStart("obli") + escapeHtml("pl") + addFmtEnd();
        } else if (str.startsWith("(@")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 1);
            return addFmtStart("capit") + escapeHtml(s) + addFmtEnd();
        }
        return addFmtStart("spec") + escapeHtml(str) + addFmtEnd();
    }

    private String addSpecification(String str) {
        if (str.equals(StringHelper.PERF) || str.equals(StringHelper.IMP)) {
            return addFmtStart("aspect") + escapeHtml(str) + addFmtEnd();
        } else {
            return addFormattedTran(str);
        }
    }

    private String addTranslation(String str) {
        return addFmtStart("tran") + escapeHtml(str) + addFmtEnd();
    }

    //TODO work on this
    private String addFormattedLine(String str) {
        String res = "";

        while (StringUtils.isNotEmpty(str)) {
            if (str.startsWith(StringHelper.LEFTPARENTHESIS)) {
                String par = str.substring(0, StringHelper.findMatchingBracket(str));
                str = StringUtils.substring(str, StringHelper.findMatchingBracket(str));
                res += addSpecification(par);
            }
            int endIndex = (str.indexOf(StringHelper.LEFTPARENTHESISCHAR) > 0) ? str.indexOf(StringHelper.LEFTPARENTHESISCHAR) : str.length();
            res += addTranslation(StringUtils.substring(str, 0, endIndex));

            str = StringUtils.removeStart(str, StringUtils.substring(str, 0, endIndex));
        }

        return res;
    }

    private String addMeaning(Meaning m) {
        return addFmtStart("synonyms") + addFieldStyle(m.getField(), m.getStyle()) + addFormattedLine(m.getSynonyms()) + addFmtEnd();
    }

    private String addVariant(Form v) {
        String res = addFmtStart("vartype") + v.getType().getPrint() + addFmtEnd();
        if (StringUtils.isNotBlank(v.getValues())) {
            res += StringHelper.SPACE + addFmtStart("varval") + v.getValues() + addFmtEnd();
        }
        return res;
    }

    private String addForms(List<Form> forms) {
        String res = addFmtStart("forms") + StringHelper.SPACE + StringHelper.LEFTPARENTHESIS;
        for (Form v: forms) {
            if (v.getType() != FormType.PRON && v.getType() != FormType.UNDEF) {
                res += addVariant(v);
            }
        }
        return res + StringHelper.RIGHTPARENTHESIS + addFmtEnd();
    }

    private String addPronominal(String str) {
        return addFmtStart("pronom") + escapeHtml(str) + addFmtEnd();
    }

    private String addVerbPronominal(List<Form> forms) {
        String res = ""; //addFmtStart("pron");
        for (Form v: forms) {
            if (v.getType()  == FormType.PRON) { //pronominal only
                res += addPronominal(v.getValues());
            }
        }
        return res; // + addFmtEnd();
    }

    private String addPhrasemes(List<Phraseme> phrasemes) {
        String res = "";
        for (Phraseme ph : phrasemes) {
            res += addSectionStart("expr");
            res += addFmtStart("exprleft") + escapeHtml(ph.getOrig()) + addFmtEnd() + StringHelper.SPACE;
            if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                res += addFmtStart("exprtype") + StringHelper.LEFTPARENTHESIS +
                        escapeHtml(ph.getType().getPrint()) + StringHelper.RIGHTPARENTHESIS +
                        addFmtEnd() + StringHelper.SPACE;
            }
            res += addFormattedLine(ph.getTran());
            res += addSectionEnd();
            //res += addBreak();
        }
        return res;
    }

    private String addExpressions(List<Phraseme> expressions) {
        return addSectionStart("expressions") + addPhrasemes(expressions) + addSectionEnd();
    }

    private String addIdioms(List<Phraseme> idioms) {
        return addSectionStart("idioms") + addPhrasemes(idioms) + addSectionEnd();
    }

    private String getFormattedWordWithTranslation(Word w) {
        String str = addSectionStart("wrdsect");

        str += addSectionStart("wrdline");

        //construct word w/ translations formatting
        str += addWord(w.getOrig());

        //pronunciation
        if (StringUtils.isNotBlank(w.getPronunciation())) {
            str += addPronunciation(w.getPronunciation());
        }
        str += addSectionEnd();

        int number;

        for (WordType wt: w.getWordTypes()) {

            //pp, f, pl, ...
            if (wt.hasForms()) {
                str += addForms(wt.getForms());
                str += addVerbPronominal(wt.getForms());
            }

            //str += addBreak();

            if (wt.hasClassOrNumGend(wt)) {
                str += addSectionStart("wrdclng");
                str += addClassNumGend(wt);
                //str += addBreak();
                str += addSectionEnd();
            }

            number = 1;

            for (Meaning m: wt.getMeanings()) {
                str += addSectionStart("mean");
                str += addSectionStart("meanln");
                if (wt.getMeanings().size() > 1) {
                    str += addNumber(number++ + ". ");
                }

                str += addMeaning(m);
                str += addSectionEnd();
                //str += addBreak();

                //expressions
                if (m.getExpressions() != null && !m.getExpressions().isEmpty()) {
                    str += addExpressions(m.getExpressions());
                    //str += addBreak();
                }
                str += addSectionEnd();

            }
        }
        str += addSectionEnd();

        return str;
    }

    private String getFormattedWordIdioms(Word w) {
        String str = "";
        str += addSectionStart("idisect");

        //idioms
        if (w.getIdioms() != null && !w.getIdioms().isEmpty()) {

            str += addIdioms(w.getIdioms());
        }

        str += addSectionEnd();
        return str;
    }

    private String getTab(Lang lang) {
        if (lang == Lang.PT) return "portugal";
        else if (lang == Lang.SK) return "slovak";
        return null;
    }

    private String generateInsert(Word w) {
        String sqli = "INSERT INTO `dict_" + getTab(w.getLang()) + "` (`ID`, `word`, `nodiacr`, `meaningsfmt`, `idiomsfmt`) " +
                "VALUES (" + counter++ + ", ";

        sqli += "'" + w.getOrig().toLowerCase() + "', ";
        sqli += "'" + StringUtils.stripAccents(w.getOrig()).toLowerCase() + "', ";
        sqli += "'";

        //System.out.println(w.getOrig());
        sqli += getFormattedWordWithTranslation(w);
        sqli += "', '";
        sqli += getFormattedWordIdioms(w);
        sqli += "');";

        return sqli;
    }


    @Override
    public void generateV2Export(List<Word> words) {
        if (words == null || words.isEmpty()) return;

        File out = new File("export_" + getTab(words.get(0).getLang())+ ".sql");

        try {
            FileUtils.writeStringToFile(out, "", "UTF-8", false);
            for (Word word: words) {
//                if (word.getOrig().equals("cabine") || word.getOrig().equals("cabina")
//                        || word.getOrig().equals("racha") || word.getOrig().equals("rachadura"))
//                if (word.getOrig().equals("aberto") || word.getOrig().equals("abrir") || word.getOrig().equals("anexo")
//                        || word.getOrig().equals("cabo") || word.getOrig().equals("racha")
//                        || word.getOrig().equals("agentúra") || word.getOrig().equals("asi") || word.getOrig().equals("cukor")
//                        || word.getOrig().equals("hlavný"))
                    FileUtils.writeStringToFile(out, generateInsert(word) + "\n", "UTF-8", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
