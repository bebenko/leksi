package sk.portugal.leksi.dictexp.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.dictexp.service.ExportService;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.model.extra.Contraction;
import sk.portugal.leksi.util.helper.EscapeHelper;
import sk.portugal.leksi.util.helper.LangHelper;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class V2ExportServiceImpl implements ExportService {

    private static final boolean USE_NBSP = true;

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
        return addFmtStart("pronun") + space() + StringHelper.LEFTSQUAREBRACKET
                + escapeHtml(str) + StringHelper.RIGHTSQUAREBRACKET + addFmtEnd();
    }

    private String addClass(Word wt, Lang explang) {
        String res = null;
        if (wt.getWordClass() != null && (wt.getWordClass() == WordClass.P || wt.getWordClass() == WordClass.PP)) {
            //for particips
            res += escapeHtml(wt.getWordClass().getPrint(explang) + StringHelper.SPACE + LangHelper.getOfVerb(explang) + StringHelper.SPACE);
            res += addWordReference(wt.getForms().get(0).getValues());
        } else {
            res += escapeHtml(wt.getWordClass().getPrint(explang));
            if (wt.getWordClass() == WordClass.VPRONSA || wt.getWordClass() == WordClass.VPRONSI) {
                res += addFmtEnd() + space();
                res += addTranslation(escapeHtml(wt.getWordClass().getAddition(explang)));
                res += addFmtStart("clng");
            }
        }
        return res;
    }

    private String addClassCaseNumGend(Word wt, Lang explang) {
        String res = addFmtStart("clng");
        boolean wc = false, ct = false;
        if (wt.getWordClass() != null && StringUtils.isNotBlank(wt.getWordClass().getPrint(explang))) {
            res = addClass(wt, explang);
            wc = true;
        }
        if (wt.getCaseType() != null && StringUtils.isNotBlank(wt.getCaseType().getPrint(explang))) {
            if (wc) {
                res += space(); //StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getCaseType().getPrint(explang));
            ct = true;
        }
        if (wt.getNumberGender() != null && StringUtils.isNotBlank(wt.getNumberGender().getPrint(explang))) {
            if (wc || ct) {
                res += space(); //StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getNumberGender().getPrint(explang));
        }
        if (wt.hasNumberGender2()) {
            res += StringHelper.SLASH + space();
            if (wt.getWordClass() != null && StringUtils.isNotBlank(wt.getWordClass().getPrint(explang))) {
                res = addClass(wt, explang);
                res += space(); //StringHelper.COMMASPACE;
            }
            res += escapeHtml(wt.getNumberGender2().getPrint(explang));
        }
        res += addFmtEnd();
        if (wt.hasComparison()) {
            res += space();
            res += escapeHtml(wt.getComparison().getDegree().getPrint(explang));
            res += space();
            res += escapeHtml(LangHelper.getOf(explang));
            res += space() + addFmtEnd();
            res += addWordReference(wt.getComparison().getPositive());
        }
        return res;
    }

    private String addNumber(String str) {
        return addFmtStart("meannum") + escapeHtml(str) + addFmtEnd();
    }

    private String addFieldStyle(FieldType fieldType, Style style, Lang explang) {
        if (fieldType == null && style == null) return StringHelper.EMPTY;

        String s = addFmtStart("fiesty");
        boolean fi = false;
        if (fieldType != null) {
            s += escapeHtml(Style.ODB.getPrint(explang) + space() + fieldType.getPrint(explang));
            fi = true;
        }
        if (style != null) {
            if (fi) s += escapeHtml(space()); //StringHelper.COMMASPACE);
            s += escapeHtml(style.getPrint(explang));
        }
        return s + space() + addFmtEnd();
    }

    private String addFormattedTran(String str, boolean print, Lang explang, NumberGender ng) {

        if (str.startsWith("(-")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            return addTranslation(StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1);
            NumberGender sng;
            if ((sng = NumberGender.valueOfKey(s.trim())) != NumberGender.UNDEF) {
                return addFmtStart("obli") + escapeHtml(sng.getPrint(explang)) + addFmtEnd();
            } else if (print) {
                return addFmtStart("obli") + escapeHtml(s) + addFmtEnd();
            } else
                return StringHelper.EMPTY;
        } else if (str.equals("(pl)")) {
            return addFmtStart("obli") + escapeHtml(NumberGender.valueOfKey("pl").getPrint(explang)) + addFmtEnd();
        } else if (str.startsWith("(@")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 1);
            return addFmtStart("capit") + escapeHtml(s) + addFmtEnd();
        }
        if (StringUtils.startsWithAny(str, StringHelper.GENDERSTRINGS)) {
            NumberGender nng = NumberGender.valueOfKey(StringUtils.remove(str.substring(1, str.length() - 1), StringHelper.DOT));
            if (ng != nng) {
                return addFmtStart("spec") + escapeHtml(StringHelper.LEFTPARENTHESIS + nng.getPrint(explang) + StringHelper.RIGHTPARENTHESIS) + addFmtEnd();
            }
        } else if (StringHelper.containsTwo(str, StringHelper.DASH) && !StringUtils.startsWith(str, "(!-")) {
            String res = "";
            if (StringUtils.startsWithAny(str, StringHelper.GENDERSTRINGSLEFT)) {
                String s = StringUtils.removeStart(StringUtils.removeEnd(str, StringHelper.RIGHTPARENTHESIS), StringHelper.LEFTPARENTHESIS);
                res += addTranslation(StringHelper.LEFTPARENTHESIS);

                String sxx; NumberGender sng;
                String[] sx = s.split(StringHelper.COMMA);
                for (int i = 0; i < sx.length; i++) {
                    sxx = StringUtils.substringBefore(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                    if ((sng = NumberGender.valueOfKey(sxx.trim())) != NumberGender.UNDEF) sxx = sng.getPrint(explang);
                    res += addFmtStart("spec") + escapeHtml(sxx) + space() + addFmtEnd();
                    sxx = StringUtils.substringAfter(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                    sxx = StringUtils.removeStart(StringUtils.removeEnd(sxx, StringHelper.DASH), StringHelper.DASH);
                    res += addTranslation(sxx);
                    if (i < sx.length - 1) res += addTranslation(StringHelper.COMMASPACE);
                }
                res += addTranslation(StringHelper.RIGHTPARENTHESIS);
                return res;
            } else if (print) { //works only for two dashes
                res += addFmtStart("spec") + escapeHtml(StringUtils.substringBefore(str, StringHelper.DASH)) + addFmtEnd();
                res += addTranslation(StringUtils.substringBetween(str, StringHelper.DASH));
                res += addFmtStart("spec") + escapeHtml(StringUtils.substringAfterLast(str, StringHelper.DASH)) + addFmtEnd();
                return res;
            }
        } else if (str.indexOf(StringHelper.COLON) > 0) {
            if (str.startsWith(StringHelper.LEFTPARENTHESIS + explang.getKey() + StringHelper.COLON)) {
                String s = StringHelper.LEFTPARENTHESIS + StringUtils.trim(StringUtils.substringAfter(str, StringHelper.COLON));
                return addFmtStart("spec") + escapeHtml(s) + addFmtEnd();
            }
        }

        if (print) {
            return addFmtStart("spec") + escapeHtml(StringUtils.replaceOnce(str, "(!-", "(")) + addFmtEnd();
        }
        return StringHelper.EMPTY;
    }

    private String addSpecification(String str, boolean print, Lang explang, NumberGender ng) {
        if (str.equals(StringHelper.PERF) || str.equals(StringHelper.IMP) || str.equals(StringHelper.IMPPERF)) {
            return addFmtStart("aspect") + escapeHtml(StringHelper.LEFTPARENTHESIS
                    + SignificanceType.valueOfKey(StringUtils.remove(str.substring(1, str.length() - 1), StringHelper.DOT)).getPrint(explang)
                    + StringHelper.RIGHTPARENTHESIS)
                    + addFmtEnd();
        } else {
            return addFormattedTran(str, print, explang, ng);
        }
    }

    private String addTranslation(String str) {
        return addFmtStart("tran") + escapeHtml(str) + addFmtEnd();
    }

    private String addParticip(String str, Lang explang) {
        String s = str, ss, res = "";
        s = StringUtils.replaceEach(s, new String[] {FormType.P.getKey() + StringHelper.SPACE, FormType.PP.getKey() + StringHelper.SPACE},
                new String[] {FormType.P.getPrint(explang) + StringHelper.SPACE, FormType.PP.getPrint(explang) + StringHelper.SPACE});
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS + StringHelper.DOUBLEHASH)) {
                //s = addFormattedParentheses(p, s, true, explang);
                res += addFmtStart("particip obli") + escapeHtml(s.substring(3, StringHelper.findMatchingBracket(s) - 1).trim()) + addFmtEnd();
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s));
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                res += addFmtStart("particip tran") + escapeHtml(ss) + addFmtEnd();
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }

            if (StringUtils.isNotBlank(s) && !StringUtils.startsWithAny(s, StringHelper.DELIMITERS)
                    && !StringUtils.startsWith(s, StringHelper.RIGHTSQUAREBRACKET)) {
                res += space();
            }
        }
        return res;
    }

    /*
    private static String addParenthesesLite(XWPFParagraph p, String str, String color, Lang explang) {
        String s = str.substring(0, StringHelper.findMatchingBracket(str));
        if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
            NumberGender nng = NumberGender.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT));
            addItalic(p, nng.getPrint(explang), color);
        } else {
            addItalic(p, s, color);
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str));
    }

    private static void addFormattedLite(XWPFParagraph p, String str, String color, Lang explang) {
        String s = str, ss; boolean afterDelim = false;
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                if (afterDelim) {
                    addSpace(p);
                    afterDelim = false;
                }
                s = StringUtils.stripStart(addParenthesesLite(p, s, color, explang), null);
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                if (!StringUtils.startsWithAny(s, StringHelper.DELIMITERS)) {
                    addSpace(p);
                }
                if (StringUtils.endsWithAny(ss, StringHelper.DELIMITERS)) {
                    afterDelim = true;
                }
                addNormal(p, ss, color);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }
        }
    }

     */

    private String addSquareBracketsForLang(String str, Lang explang) {
        if (StringUtils.startsWithAny(str.substring(1), StringHelper.GENDERSTRINGS)) {
            space();
            addTranslation(str.substring(0, 1));
            //TODO finish this addFormattedLite(p, str.substring(1, s.length() - 1), color, explang);
            addTranslation(str.substring(str.length() - 1));
        } else if (str.indexOf(StringHelper.COLON) > 0) {
            Lang lang = Lang.valueOfKey(StringUtils.substringBetween(str, StringHelper.LEFTSQUAREBRACKET, StringHelper.COLON));
            if (lang == explang) {
                return addTranslation(StringUtils.remove(str, lang.getKey() + StringHelper.COLON + StringHelper.SPACE));
            }
        } else if (str.contains(FormType.P.getKey() + StringHelper.SPACE)
                || str.contains(FormType.PP.getKey() + StringHelper.SPACE)) {
            return addParticip(str, explang);
        } else {
            return addTranslation(str);
        }
        return StringHelper.EMPTY;
    }

    private String addFormattedLine(String str, Lang lang, Lang explang, NumberGender ng) {
        String res = "", s = str, ss; boolean first = true;

        while (StringUtils.isNotEmpty(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                String par = s.substring(0, StringHelper.findMatchingBracket(s));
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s));
                res += addSpecification(par, (first && lang == explang) || (!first && lang != explang), explang, ng);
            } else if (s.startsWith(StringHelper.LEFTSQUAREBRACKET)) {
                String par = s.substring(0, StringHelper.findMatchingBracket(s, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
                res += addSquareBracketsForLang(par, explang);
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                res += addTranslation(ss);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }
            //add (non-breaking)space
            if (StringUtils.isNotBlank(s)) {
                if (!StringUtils.startsWithAny(s, StringHelper.DELIMITERS)) {
                    if (StringUtils.startsWith(s, StringHelper.LEFTPARENDASH)) {
                        res += space();
                    } else  if (StringUtils.startsWith(s, StringHelper.LEFTPARENTHESIS)) {
                        if (lang != explang || s.startsWith(StringHelper.PERF) || s.startsWith(StringHelper.IMP) || s.startsWith(StringHelper.IMPPERF)
                                || StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
                            res += space();
                        }
                    } else if (!(str.startsWith(StringHelper.LEFTPARENTHESIS) && first && lang != explang)) { //just removed parentheses, (don't) add space
                        res += space();
                    }
                }
            }

            first = false;
        }

        return res;
    }

    private String space() {
        if (USE_NBSP) {
            return EscapeHelper.NBSP;
        }
        return StringHelper.SPACE;
    }

    private String addWordReference(String str) {
        return addFmtStart("wrdref") + escapeHtml(str) + addFmtEnd();
    }

    private String addContraction(Contraction c, Lang lang, Lang explang, NumberGender ng) {
        String str = PhrasemeType.CONTR.getPrint(explang) + space()
                + c.getFirstWord().getWordClass().getPrint(explang) + space();
        String res = addFmtStart("clng") + escapeHtml(str) + addFmtEnd();
        res += addWordReference(c.getFirstHomonym().getOrig());
        str = space() + LangHelper.getAnd(explang) + space()
                + c.getSecondWord().getWordClass().getPrint(explang) + space();
        if (c.getSecondWord().getCaseType(true) != null) {
            str += c.getSecondWord().getCaseType(true).getPrint(explang) + space();
        }
        if (c.getSecondWord().getNumberGender(true) != null) {
            str += c.getSecondWord().getNumberGender(true).getPrint(explang) + space();
        }
        res += addFmtStart("clng") + escapeHtml(str) + addFmtEnd();
        res += addWordReference(c.getSecondHomonym().getOrig());
        if (c.getAdditionalText() != null) {
            res += addTranslation(space() + c.getAdditionalText());
            res += addFormattedLine(c.getAdditionalText(), lang, explang, ng);
        }
        return res;
    }

    private String addVerbForm(Form vf, Lang explang) {
        String lbr = StringHelper.LEFTPARENTHESIS, rbr = StringHelper.RIGHTPARENTHESIS, txt = vf.getType().getPrint(explang);
        if (vf.getType() == FormType.VERBFORM) {
            lbr = StringHelper.LEFTSQUAREBRACKET;
            rbr = StringHelper.RIGHTSQUAREBRACKET;
            txt = LangHelper.getFormOfVerb(explang);
        }

        String res = addTranslation(lbr);
        res += addFmtStart("obli") + escapeHtml(txt) + addFmtEnd();
        res += addTranslation(space());
        if (vf.getType() == FormType.VERBFORM) {
            res += addWordReference(vf.getValues());
        } else {
            res += addTranslation(vf.getValues());
        }
        res += addTranslation(rbr);
        return res;
    }


    private String addMeaning(Meaning m, Lang lang, Lang explang, NumberGender ng) {
        return addFmtStart("synonyms") + addFieldStyle(m.getFieldType(), m.getStyle(), explang)
                + (m.isContraction() ? addContraction(m.getContraction(), lang, explang, ng) : addFormattedLine(m.getSynonyms(), lang, explang, ng))
                + addFmtEnd();
    }

    private String addForm(Form v, Lang explang) {
        String res = addFmtStart("vartype") + v.getType().getPrint(explang) + addFmtEnd();
        if (StringUtils.isNotBlank(v.getValues())) {
            res += space() + addFmtStart("varval") + v.getValues() + addFmtEnd();
        }
        return res;
    }

    private String addForms(List<Form> forms, Lang explang) {
        String res = addFmtStart("forms") + space() + StringHelper.LEFTPARENTHESIS;
        for (Form v: forms) {
            if (v.getType().getId() < 10) { //leaves out LINKs and UNDEF
                res += addForm(v, explang) + StringHelper.COMMASPACE;
            }
        }
        return StringUtils.removeEnd(res, StringHelper.COMMASPACE) + StringHelper.RIGHTPARENTHESIS + addFmtEnd();
    }

    private String addPhrasemes(List<Phraseme> phrasemes, Lang lang, Lang explang, NumberGender ng) {
        String res = "";
        for (Phraseme ph : phrasemes) {
            res += addSectionStart("expr");
            res += addFmtStart("exprleft") + escapeHtml(ph.getOrig()) + addFmtEnd() + space();
            if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                res += addFmtStart("exprtype") + StringHelper.LEFTPARENTHESIS +
                        escapeHtml(ph.getType().getPrint(explang)) + StringHelper.RIGHTPARENTHESIS +
                        addFmtEnd() + space();
            }
            res += addFormattedLine(ph.getTran(), lang, explang, ng);
            res += addSectionEnd();
        }
        return res;
    }

    private String addExpressions(List<Phraseme> expressions, Lang lang, Lang explang, NumberGender ng) {
        return addSectionStart("expressions") + addPhrasemes(expressions, lang, explang, ng) + addSectionEnd();
    }

    private String addIdioms(List<Phraseme> idioms, Lang lang, Lang explang, NumberGender ng) {
        return addSectionStart("idioms") + addPhrasemes(idioms, lang, explang, ng) + addSectionEnd();
    }

    private String getFormattedWordWithTranslation(Homonym w, Lang lang, Lang explang) {
        String str = addSectionStart("wrdsect");

        str += addSectionStart("wrdline");

        //construct homonym w/ translations formatting
        str += addWord(w.getOrig());

        //pronunciation
        if (StringUtils.isNotBlank(w.getPronunciation())) {
            str += addPronunciation(w.getPronunciation());
        }
        str += addSectionEnd();

        int number;

        for (Word wt: w.getWords()) {

            //ignore everything for old orthography
            if (wt.getForms() != null && wt.hasLinkForms()) {

                if (wt.hasForms()) {
                    str += addForms(wt.getForms(), explang);
                }

                str += space();
                str += addClassCaseNumGend(wt, explang);

                str += addSectionStart("mean");
                str += addSectionStart("meanln");
                str += addSpecification(StringUtils.substringBefore(wt.getLinkForm().getType().getPrint(explang),
                        StringHelper.SPACE + StringHelper.LINK), true, explang, wt.getNumberGender());

                String s;
                if (wt.hasGrafForm()) {
                    s = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(),
                            StringHelper.LINK + space());
                } else { //form: FormType.LINK_SK_VERB_IMP
                    s = StringUtils.substringBetween(wt.getMeanings().get(0).getSynonyms(),
                            StringHelper.LINK + StringHelper.SPACE,
                            StringHelper.SPACE + StringHelper.LEFTPARENTHESIS + SignificanceType.PERF.getKey());
                }
                str += addFmtStart("synonyms") + space() + addWordReference(s) + addFmtEnd() + space();

                if (!wt.hasGrafForm()) {
                    String k = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(), s + StringHelper.SPACE);
                    k = StringUtils.removeEnd(k.substring(1, k.length() - 1), StringHelper.DOT);
                    str += addSpecification(StringHelper.LEFTPARENTHESIS
                            + SignificanceType.valueOfKey(k).getPrint(explang)
                            + StringHelper.RIGHTPARENTHESIS, true, explang, wt.getNumberGender());
                }


                str += addSectionEnd();
                str += addSectionEnd();

            } else { //proceed with "normal" formatting

                //pp, f, pl, ...
                if (wt.hasForms()) {
                    str += addForms(wt.getForms(), explang);
                }

                if (wt.hasClassOrNumGend(wt)) {
                    str += addSectionStart("wrdclng");
                    str += addClassCaseNumGend(wt, explang);
                    //str += addBreak();
                    str += addSectionEnd();
                }

                if (wt.hasVerbForm()) {
                    space();
                    str += addVerbForm(wt.getForms().get(0), explang);
                }

                number = 1;

                for (Meaning m: wt.getMeanings()) {
                    str += addSectionStart("mean");
                    str += addSectionStart("meanln");
                    if (wt.getMeanings().size() > 1) {
                        str += addNumber(number++ + StringHelper.DOT + space());
                    }

                    str += addMeaning(m, lang, explang, wt.getNumberGender());
                    str += addSectionEnd();
                    //str += addBreak();

                    //expressions
                    /* IGNORE FOR NOW
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

    private String getFormattedWordIdioms(Homonym w, Lang explang) {
        String str = "";
        str += addSectionStart("idisect");

        //idioms
        /* IGNORE FOR NOW
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

    private String generateInsert(Homonym w, Lang lang, Lang explang) {
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
    public void generateV2Export(Lang lang, Lang explang, List<Homonym> homonyms) {
        if (homonyms == null || homonyms.isEmpty()) return;

        File out = new File("_sqlexports\\export_" + getTab(homonyms.get(0).getLang(), explang) + ".sql");

        try {
            FileUtils.writeStringToFile(out, "", "UTF-8", false);
            for (Homonym homonym : homonyms) {
                if (homonym.isEnabled())
                    FileUtils.writeStringToFile(out, generateInsert(homonym, lang, explang) + "\n", "UTF-8", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
