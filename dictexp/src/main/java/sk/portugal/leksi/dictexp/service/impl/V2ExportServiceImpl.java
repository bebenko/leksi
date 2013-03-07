package sk.portugal.leksi.dictexp.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class V2ExportServiceImpl implements ExportService {

    private static final String EMPTY = "";
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

    private String addClassNumGend(WordType wt, Lang explang) {
        String res = addFmtStart("clng");
        boolean wc = false, ct = false;
        if (wt.getWordClass() != null) {
            res += escapeHtml(wt.getWordClass().getPrint(explang));
            wc = true;
        }
        if (wt.getCaseType() != null) {
            if (wc) {
                res += StringHelper.SPACE; //StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getCaseType().getPrint(explang));
            ct = true;
        }
        if (wt.getNumGend() != null) {
            if (wc || ct) {
                res += StringHelper.SPACE; //StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getNumGend().getPrint(explang));
        }
        return res + addFmtEnd();
    }

    private String addNumber(String str) {
        return addFmtStart("meannum") + escapeHtml(str) + addFmtEnd();
    }

    private String addFieldStyle(FieldType fieldType, Style style, Lang explang) {
        if (fieldType == null && style == null) return "";

        String s = addFmtStart("fiesty");
        boolean fi = false;
        if (fieldType != null) {
            s += escapeHtml(Style.ODB.getPrint(explang) + StringHelper.SPACE + fieldType.getPrint(explang));
            fi = true;
        }
        if (style != null) {
            if (fi) s += escapeHtml(StringHelper.SPACE); //StringHelper.COMMASPACE);
            s += escapeHtml(style.getPrint(explang));
        }
        return s + StringHelper.SPACE + addFmtEnd();
    }

    private String addFormattedTran(String str, boolean print, Lang explang) {

        if (str.startsWith("(-")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            return addTranslation(StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1);
            if (print)
                return addFmtStart("obli") + escapeHtml(s) + addFmtEnd();
            else
                return EMPTY;
        } else if (str.equals("(pl)")) {
            return addFmtStart("obli") + escapeHtml(NumberGender.valueOfKey("pl").getPrint(explang)) + addFmtEnd();
        } else if (str.startsWith("(@")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 1);
            return addFmtStart("capit") + escapeHtml(s) + addFmtEnd();
        }
        String s = str; boolean gend = false;
        //!FORMATTING
        if (StringUtils.startsWithAny(str, StringHelper.GENDERSTRINGS)) {
            s = s.replace("/", "./").replace(")", ".)");
            gend = true;
        }
        if (print || gend) {
            return addFmtStart("spec") + escapeHtml(s) + addFmtEnd();
        }
        return "";
    }

    private String addSpecification(String str, boolean print, Lang explang) {
        if (str.equals(StringHelper.PERF) || str.equals(StringHelper.IMP)) {
            return addFmtStart("aspect") + escapeHtml(StringHelper.LEFTPARENTHESIS
                    + SignificanceType.valueOfKey(str.substring(1, str.length() - 2)).getPrint(explang)
                    + StringHelper.RIGHTPARENTHESIS)
                    + addFmtEnd();
        } else {
            return addFormattedTran(str, print, explang);
        }
    }

    private String addTranslation(String str) {
        return addFmtStart("tran") + escapeHtml(str) + addFmtEnd();
    }

    //TODO work on this
    private String addFormattedLine(String str, Lang lang, Lang explang) {
        String res = "", s = str, ss; boolean first = true;

        while (StringUtils.isNotEmpty(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                String par = s.substring(0, StringHelper.findMatchingBracket(s));
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s));
                res += addSpecification(par, (first && lang == explang) || (!first && lang != explang), explang);
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                res += addTranslation(ss);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }
            //add space
            if (StringUtils.isNotBlank(s)) {
                if (!StringUtils.startsWithAny(s, StringHelper.DELIMITERS)) {
                    if (StringUtils.startsWith(s, StringHelper.LEFTPARENTHESIS)) {
                        if (lang != explang || s.startsWith(StringHelper.PERF) || s.startsWith(StringHelper.IMP)
                                || StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
                            res += StringHelper.SPACE;
                        }
                    } else if (!(str.startsWith(StringHelper.LEFTPARENTHESIS) && first && lang != explang)) { //just removed parentheses, (don't) add space
                        res += StringHelper.SPACE;
                    }
                }
            }

            first = false;

//            int endIndex = (str.indexOf(StringHelper.LEFTPARENTHESISCHAR) > 0) ? str.indexOf(StringHelper.LEFTPARENTHESISCHAR) : str.length();
//            res += addTranslation(StringUtils.substring(str, 0, endIndex));

//            str = StringUtils.removeStart(str, StringUtils.substring(str, 0, endIndex));
        }

        return res;
    }

    private String addMeaning(Meaning m, Lang lang, Lang explang) {
        return addFmtStart("synonyms") + addFieldStyle(m.getFieldType(), m.getStyle(), explang) + addFormattedLine(m.getSynonyms(), lang, explang) + addFmtEnd();
    }

    private String addForm(Form v, Lang explang) {
        String res = addFmtStart("vartype") + v.getType().getPrint(explang) + addFmtEnd();
        if (StringUtils.isNotBlank(v.getValues())) {
            res += StringHelper.SPACE + addFmtStart("varval") + v.getValues() + addFmtEnd();
        }
        return res;
    }

    private String addForms(List<Form> forms, Lang explang) {
        String res = addFmtStart("forms") + StringHelper.SPACE + StringHelper.LEFTPARENTHESIS;
        for (Form v: forms) {
            if (v.getType().getId() < 10) { //leaves out LINKs and UNDEF
                res += addForm(v, explang) + StringHelper.COMMASPACE;
            }
        }
        return StringUtils.removeEnd(res, StringHelper.COMMASPACE) + StringHelper.RIGHTPARENTHESIS + addFmtEnd();
    }

    /*private String addPronominal(String str) {
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
    }*/

    private String addPhrasemes(List<Phraseme> phrasemes, Lang lang, Lang explang) {
        String res = "";
        for (Phraseme ph : phrasemes) {
            res += addSectionStart("expr");
            res += addFmtStart("exprleft") + escapeHtml(ph.getOrig()) + addFmtEnd() + StringHelper.SPACE;
            if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                res += addFmtStart("exprtype") + StringHelper.LEFTPARENTHESIS +
                        escapeHtml(ph.getType().getPrint(explang)) + StringHelper.RIGHTPARENTHESIS +
                        addFmtEnd() + StringHelper.SPACE;
            }
            res += addFormattedLine(ph.getTran(), lang, explang);
            res += addSectionEnd();
            //res += addBreak();
        }
        return res;
    }

    private String addExpressions(List<Phraseme> expressions, Lang lang, Lang explang) {
        return addSectionStart("expressions") + addPhrasemes(expressions, lang, explang) + addSectionEnd();
    }

    private String addIdioms(List<Phraseme> idioms, Lang lang, Lang explang) {
        return addSectionStart("idioms") + addPhrasemes(idioms, lang, explang) + addSectionEnd();
    }

    private String getFormattedWordWithTranslation(Word w, Lang lang, Lang explang) {
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

            //ignore everything for old orthography
            if (wt.getForms() != null && wt.getForms().get(0).getType() != null
                    && wt.getForms().get(0).getType() == FormType.LINK_ORT) {
                str += addSectionStart("mean");
                str += addSectionStart("meanln");
                str += addSpecification(StringUtils.substringBefore(wt.getForms().get(0).getType().getPrint(explang), StringHelper.SPACE + StringHelper.LINK), true, explang);
                str += addFmtStart("synonyms") + addFormattedLine(wt.getMeanings().get(0).getSynonyms(), lang, explang) + addFmtEnd();
                str += addSectionEnd();
                str += addSectionEnd();

            } else { //proceed with "normal" formatting

                //pp, f, pl, ...
                if (wt.hasForms()) {
                    str += addForms(wt.getForms(), explang);
                }

                //str += addBreak();

                if (wt.hasClassOrNumGend(wt)) {
                    str += addSectionStart("wrdclng");
                    str += addClassNumGend(wt, explang);
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

                    str += addMeaning(m, lang, explang);
                    str += addSectionEnd();
                    //str += addBreak();

                    //expressions
                    /* IGNORE
                    if (m.getExpressions() != null && !m.getExpressions().isEmpty()) {
                        str += addExpressions(m.getExpressions(), explang);
                        //str += addBreak();
                    } */
                    str += addSectionEnd();
                }
            }
        }
        str += addSectionEnd();

        return str;
    }

    private String getFormattedWordIdioms(Word w, Lang explang) {
        String str = "";
        str += addSectionStart("idisect");

        //idioms
        /* IGNORE
        if (w.getIdioms() != null && !w.getIdioms().isEmpty()) {

            str += addIdioms(w.getIdioms(), explang);
        } */

        str += addSectionEnd();
        return str;
    }

    private String getTab(Lang lang, Lang explang) {
        if (lang == Lang.PT) return explang.getKey() + "_portugal";
        else if (lang == Lang.SK) return explang.getKey() + "_slovak";
        return null;
    }

    private String generateInsert(Word w, Lang lang, Lang explang) {
        String sqli = "INSERT INTO `dict_" + getTab(lang, explang) + "` (`ID`, `word`, `nodiacr`, `meaningsfmt`, `idiomsfmt`) " +
                "VALUES (" + counter++ + ", ";

        sqli += "'" + w.getOrig().toLowerCase() + "', ";
        sqli += "'" + StringUtils.stripAccents(w.getOrig()).toLowerCase() + "', ";
        sqli += "'";

        //System.out.println(w.getOrig());
        sqli += getFormattedWordWithTranslation(w, lang, explang);
        sqli += "', '";
        sqli += getFormattedWordIdioms(w, explang);
        sqli += "');";

        return sqli;
    }


    @Override
    public void generateV2Export(Lang lang, Lang explang, List<Word> words) {
        if (words == null || words.isEmpty()) return;

        File out = new File("export_" + getTab(words.get(0).getLang(), explang) + ".sql");

        try {
            FileUtils.writeStringToFile(out, "", "UTF-8", false);
            for (Word word: words) {
//                if (word.getOrig().equals("cabine") || word.getOrig().equals("cabina")
//                        || word.getOrig().equals("racha") || word.getOrig().equals("rachadura"))
//                if (word.getOrig().equals("aberto") || word.getOrig().equals("abrir") || word.getOrig().equals("anexo")
//                        || word.getOrig().equals("cabo") || word.getOrig().equals("racha")
//                        || word.getOrig().equals("agentúra") || word.getOrig().equals("asi") || word.getOrig().equals("cukor")
//                        || word.getOrig().equals("hlavný"))
                    FileUtils.writeStringToFile(out, generateInsert(word, lang, explang) + "\n", "UTF-8", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
