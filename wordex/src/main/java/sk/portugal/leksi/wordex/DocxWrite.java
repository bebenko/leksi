package sk.portugal.leksi.wordex;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.model.extra.Alternative;
import sk.portugal.leksi.model.extra.Contraction;
import sk.portugal.leksi.util.helper.LangHelper;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 */
public class DocxWrite {

    public static final String IDIOMDELIMITER = "♦";
    public static final String WORDTYPEDELIMITER = "●";

    private static final String GRAY = "gray";
    private static final String COL_GRAY = "989898";


    private static XWPFRun newRun(XWPFParagraph p) {
        XWPFRun r = p.createRun();
        r.setFontFamily("Times New Roman");
        r.setFontSize(9);
        return r;
    }

    private static void addSpace(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SPACE);
    }

    private static void addSemicolon(XWPFParagraph p) {
        addSemicolon(p, null);
    }

    private static void addSemicolon(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SEMICOLON);
        if (color != null) setColor(r, color);
    }

    private static void addCommaSpace(XWPFParagraph p) {
        addCommaSpace(p, null);
    }

    private static void addCommaSpace(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.COMMASPACE);
        if (color != null) setColor(r, color);
    }

    private static void addSlash(XWPFParagraph p) {
        addSlash(p, null);
    }

    private static void addSlash(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SLASH);
        if (color != null) setColor(r, color);
    }

    private static void addLeftParenthesis(XWPFParagraph p) {
        addLeftParenthesis(p, null);
    }

    private static void addLeftParenthesis(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTPARENTHESIS);
        if (color != null) setColor(r, color);
    }

    private static void addRightParenthesis(XWPFParagraph p) {
        addRightParenthesis(p, null);
    }

    private static void addRightParenthesis(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTPARENTHESIS);
        if (color != null) setColor(r, color);
    }

    private static void addLeftSquareBracket(XWPFParagraph p) {
        addLeftSquareBracket(p, null);
    }

    private static void addLeftSquareBracket(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTSQUAREBRACKET);
        if (color != null) setColor(r, color);
    }

    private static void addRightSquareBracket(XWPFParagraph p) {
        addRightSquareBracket(p, null);
    }

    private static void addRightSquareBracket(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTSQUAREBRACKET);
        if (color != null) setColor(r, color);
    }

    private static void addWordTypeDelimiter(XWPFParagraph p) {
        addWordTypeDelimiter(p, null);
    }

    private static void addWordTypeDelimiter(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(WORDTYPEDELIMITER);
        if (color != null) setColor(r, color);
    }

    private static void addIdiomDelimiter(XWPFParagraph p) {
        addIdiomDelimiter(p, null);
    }

    private static void addIdiomDelimiter(XWPFParagraph p, String color) {
        XWPFRun r = newRun(p);
        r.setText(IDIOMDELIMITER);
        if (color != null) setColor(r, color);
    }

    private static void setColor(XWPFRun r, String color) {
        switch (color) {
            case GRAY: {
                r.setColor(COL_GRAY);
                break;
            }
        }
    }

    private static void addNormal(XWPFParagraph p, String str) {
        addNormal(p, str, null);
    }

    private static void addNormal(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setText(str);
        if (color != null) setColor(r, color);
    }

    private static void addBold(XWPFParagraph p, String str) {
        addBold(p, str, null);
    }

    private static void addBold(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setBold(true);
        r.setText(str);
        if (color != null) setColor(r, color);
    }

    private static void addUnderline(XWPFParagraph p, String str) {
        addUnderline(p, str, null);
    }

    private static void addUnderline(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setUnderline(UnderlinePatterns.SINGLE);
        r.setText(str);
        if (color != null) setColor(r, color);
    }

    private static void addSuper(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setSubscript(VerticalAlign.SUPERSCRIPT);
        r.setText(str);
    }

    private static void addItalic(XWPFParagraph p, String str) {
        addItalic(p, str, null);
    }

    private static void addItalic(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setText(str);
        if (color != null) setColor(r, color);
    }

    private static void addItalicUnderline(XWPFParagraph p, String str) {
        addItalicUnderline(p, str, null);
    }

    private static void addItalicUnderline(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setUnderline(UnderlinePatterns.SINGLE);
        r.setText(str);
        if (color != null) setColor(r, color);
    }

    private static void addItalicWithParentheses(XWPFParagraph p, String str) {
        addItalicWithParentheses(p, str, null);
    }

    private static void addItalicWithParentheses(XWPFParagraph p, String str, String color) {
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setText(StringHelper.LEFTPARENTHESIS + str + StringHelper.RIGHTPARENTHESIS);
        if (color != null) setColor(r, color);
    }

    private static String addParentheses(XWPFParagraph p, String str, String color, boolean print, Lang explang, NumberGender ng) {
        if (str.startsWith(StringHelper.LEFTPARENDASH)) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            addSpace(p);
            addNormal(p, StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS, color);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1).trim();
            NumberGender sng;
            if ((sng = NumberGender.valueOfKey(s)) != NumberGender.UNDEF) {
                addSpace(p);
                addItalic(p, sng.getPrint(explang), color);
            } else if (print) {
                addSpace(p);
                addItalic(p, s.trim(), color);
            }
        } else if (str.matches("\\(pl\\) \\(@.*")) { //extra condition for separate plural meaning
            addSpace(p);
            addItalic(p, NumberGender.valueOfKey("pl").getPrint(explang), color);
            addSpace(p);
            String s = str.substring(5);
            s = s.substring(3, StringHelper.findMatchingBracket(s) - 1);
            addBold(p, s, color);
            return StringUtils.substring(str, StringHelper.findMatchingBracket(StringUtils.removeStart(str, "(pl) ")) + 5);
        } else if (str.startsWith("(@")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 1);
            addSpace(p);
            addBold(p, s.trim(), color);
        } else {
            String s = str.substring(0, StringHelper.findMatchingBracket(str));
            if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
                NumberGender nng = NumberGender.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT));
                if (ng != nng) {
                    s = StringHelper.LEFTPARENTHESIS + nng.getPrint(explang) + StringHelper.RIGHTPARENTHESIS;
                    addSpace(p);
                    addItalic(p, s, color);
                }
            } else if (StringHelper.containsTwo(s, StringHelper.DASH) && !StringUtils.startsWith(s, "(!-")) {
                if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGSLEFT)) {
                    s = StringUtils.removeStart(StringUtils.removeEnd(s, StringHelper.RIGHTPARENTHESIS), StringHelper.LEFTPARENTHESIS);
                    addSpace(p);
                    addNormal(p, StringHelper.LEFTPARENTHESIS, color);

                    String sxx; NumberGender sng;
                    String[] sx = s.split(StringHelper.COMMA);
                    for (int i = 0; i < sx.length; i++) {
                        sxx = StringUtils.substringBefore(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                        if ((sng = NumberGender.valueOfKey(sxx.trim())) != NumberGender.UNDEF) sxx = sng.getPrint(explang);
                        addItalic(p, sxx + StringHelper.SPACE, color);
                        sxx = StringUtils.substringAfter(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                        sxx = StringUtils.removeStart(StringUtils.removeEnd(sxx, StringHelper.DASH), StringHelper.DASH);
                        addNormal(p, sxx, color);
                        if (i < sx.length - 1) addCommaSpace(p, color);
                    }
                    addNormal(p, StringHelper.RIGHTPARENTHESIS, color);
                } else if (print) { //works only for two dashes
                    addSpace(p);
                    addItalic(p, StringUtils.substringBefore(s, StringHelper.DASH), color);
                    addNormal(p, StringUtils.substringBetween(s, StringHelper.DASH), color);
                    addItalic(p, StringUtils.substringAfterLast(s, StringHelper.DASH), color);
                }
            } else if (s.equals(StringHelper.PERF) || s.equals(StringHelper.IMP) || s.equals(StringHelper.IMPPERF)) {
                addSpace(p);
                addItalic(p, StringHelper.LEFTPARENTHESIS
                        + SignificanceType.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT)).getPrint(explang)
                        + StringHelper.RIGHTPARENTHESIS, color);
            } else if (s.contains(StringHelper.COLON)) {
                if (s.startsWith(StringHelper.LEFTPARENTHESIS + explang.getKey() + StringHelper.COLON)) {
                    addSpace(p);
                    addItalic(p, StringHelper.LEFTPARENTHESIS + StringUtils.stripStart(StringUtils.substringAfter(s, StringHelper.COLON), null), color);
                }
            } else if (print) {
                s = StringUtils.replaceOnce(s, "(!-", "(");
                addSpace(p);
                addItalic(p, s, color);
            }
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str));
    }

    private static void addParticip(XWPFParagraph p, String str, String color, Lang explang) {
        String s = str, ss;
        s = StringUtils.replaceEach(s, new String[] {FormType.P.getKey() + StringHelper.SPACE, FormType.PP.getKey() + StringHelper.SPACE},
                new String[] {FormType.P.getPrint(explang) + StringHelper.SPACE, FormType.PP.getPrint(explang) + StringHelper.SPACE});
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS + StringHelper.DOUBLEHASH)) {
                ss = s.substring(3, StringHelper.findMatchingBracket(s) - 1);
                addItalic(p, ss.trim(), color);
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s));
            } else {
                //TODO underline (linkify) particips
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                addNormal(p, ss, color);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }

            if (StringUtils.isNotBlank(s) && !StringUtils.startsWithAny(s, StringHelper.DELIMITERS)
                    && !StringUtils.startsWith(s, StringHelper.RIGHTSQUAREBRACKET)) {
                addSpace(p);
            }
        }
    }

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

    private static String addSquareBrackets(XWPFParagraph p, String str, String color, Lang lang, Lang explang, NumberGender ng) {
        String s = StringUtils.substring(str, 0, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
        if (StringUtils.startsWithAny(s.substring(1), StringHelper.GENDERSTRINGS)) {
            addSpace(p);
            addLeftSquareBracket(p, color);
            addFormattedLite(p, s.substring(1, s.length() - 1), color, explang);
            addRightSquareBracket(p, color);
        } else if (s.indexOf(StringHelper.COLON) > 0) {
            Lang intflang = Lang.valueOfKey(StringUtils.substringBetween(s, StringHelper.LEFTSQUAREBRACKET, StringHelper.COLON));
            if (intflang == explang) {
                addSpace(p);
                addNormal(p, StringUtils.remove(s, intflang.getKey() + StringHelper.COLON + StringHelper.SPACE), color);
            }
        } else if (s.contains(FormType.P.getKey() + StringHelper.SPACE)
                || s.contains(FormType.PP.getKey() + StringHelper.SPACE)) {
            addSpace(p);
            addParticip(p, s, color, explang);
        } else {
            addSpace(p);
            addNormal(p, s, color);
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
    }

    private static void addFormatted(XWPFParagraph p, String str, String color, Lang lang, Lang explang, NumberGender ng) {
        String s = str, ss; boolean first = true;
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                //intf.-lang.-based content
                s = StringUtils.stripStart(addParentheses(p, s, color,
                        (first && lang == explang) || (!first && lang != explang), explang, ng), null);
            } else if (s.startsWith(StringHelper.LEFTSQUAREBRACKET)) {
                s = StringUtils.stripStart(addSquareBrackets(p, s, color, lang, explang, ng), null);
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                if (!StringUtils.startsWithAny(s, StringHelper.DELIMITERS)) addSpace(p);
                addNormal(p, ss, color);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }

            first = false;
        }
    }

    private static void addContraction(XWPFParagraph p, Contraction c, String color, Lang lang, Lang explang, NumberGender ng) {
        String str = PhrasemeType.CONTR.getPrint(explang) + StringHelper.SPACE
                + c.getFirstWord().getWordClass().getPrint(explang) + StringHelper.SPACE;
        addSpace(p);
        addItalic(p, str, color);
        addItalicUnderline(p, c.getFirstHomonym().getOrig(), color);
        str = StringHelper.SPACE + LangHelper.getAnd(explang) + StringHelper.SPACE
                + c.getSecondWord().getWordClass().getPrint(explang) + StringHelper.SPACE;
        if (c.getSecondWord().getCaseType(true) != null && StringUtils.isNotBlank(c.getSecondWord().getCaseType(true).getPrint(explang))) {
            str += c.getSecondWord().getCaseType(true).getPrint(explang) + StringHelper.SPACE;
        }
        if (c.getSecondWord().getNumberGender(true) != null && StringUtils.isNotBlank(c.getSecondWord().getNumberGender(true).getPrint(explang))) {
            str += c.getSecondWord().getNumberGender(true).getPrint(explang) + StringHelper.SPACE;
        }
        addItalic(p, str, color);
        addItalicUnderline(p, c.getSecondHomonym().getOrig(), color);
        if (c.getAdditionalText() != null) {
            addFormatted(p, c.getAdditionalText(), color, lang, explang, ng);
        }
    }

    private static void addVerbForm(XWPFParagraph p, Form vf, String color, Lang explang) {
        String lbr = StringHelper.LEFTPARENTHESIS, rbr = StringHelper.RIGHTPARENTHESIS, txt = vf.getType().getPrint(explang);
        if (vf.getType() == FormType.VERBFORM) {
            lbr = StringHelper.LEFTSQUAREBRACKET;
            rbr = StringHelper.RIGHTSQUAREBRACKET;
            txt = LangHelper.getFormOfVerb(explang);
        }
        addNormal(p, lbr, color);
        addItalic(p, txt, color);
        addNormal(p, StringHelper.SPACE);
        if (vf.getType() == FormType.VERBFORM) {
            addUnderline(p, vf.getValues(), color);
        } else {
            addNormal(p, vf.getValues(), color);
        }
        addNormal(p, rbr, color);
    }

    private static void addClass(XWPFParagraph p, Word wt, String color, Lang explang) {
        addSpace(p);
        if (wt.getWordClass() != null && (wt.getWordClass() == WordClass.P || wt.getWordClass() == WordClass.PP)) {
            //for particips
            addItalic(p, wt.getWordClass().getPrint(explang) + StringHelper.SPACE + LangHelper.getOfVerb(explang) + StringHelper.SPACE, color);
            addItalicUnderline(p, wt.getForms().get(0).getValues(), color);
        } else {
            addItalic(p, wt.getWordClass().getPrint(explang), color);
            if (wt.getWordClass() == WordClass.VPRONSA || wt.getWordClass() == WordClass.VPRONSI) {
                addSpace(p);
                addNormal(p, wt.getWordClass().getAddition(explang), color);
            }
        }
    }

    private static void addClassCaseNumberGender(XWPFParagraph p, Word wt, String color, Lang explang) {
        if (wt.getWordClass() != null && StringUtils.isNotBlank(wt.getWordClass().getPrint(explang))) {
            addClass(p, wt, color, explang);
        }
        if (wt.hasComparison()) {
            addSpace(p);
            addItalic(p, wt.getComparison().getDegree().getPrint(explang), color);
            addSpace(p);
            addItalic(p, LangHelper.getOf(explang), color);
            addSpace(p);
            addUnderline(p, wt.getComparison().getPositive(), color);
        }
        if (wt.getCaseType() != null && StringUtils.isNotBlank(wt.getCaseType().getPrint(explang))) {
            addSpace(p);
            addItalic(p, wt.getCaseType().getPrint(explang), color);
        }
        if (wt.getNumberGender() != null && StringUtils.isNotBlank(wt.getNumberGender().getPrint(explang))) {
            addSpace(p);
            addItalic(p, wt.getNumberGender().getPrint(explang), color);
        }
        if (wt.hasNumberGender2()) {
            addSlash(p, color);
            if (wt.getWordClass() != null && StringUtils.isNotBlank(wt.getWordClass().getPrint(explang))) {
                addClass(p, wt, color, explang);
            }
            addSpace(p);
            addItalic(p, wt.getNumberGender2().getPrint(explang), color);
        }
    }

    private static boolean contains(WordClass[] wcs, Homonym homonym) {
        for (Word wt: homonym.getWords()) {
            for (WordClass wc: wcs) {
                if (wt.getWordClass() == wc || (wc.isNone() && wt.getWordClass() == null)) return true;
            }
        }
        return false;
    }

    public static int fullExport(Lang lang, Lang explang, List<Homonym> homonyms, WordClass[] wcs) {
        return genDocx(lang, explang, homonyms, wcs, true);
    }

    public static int export(Lang lang, Lang explang, List<Homonym> homonyms, WordClass[] wcs) {
        return genDocx(lang, explang, homonyms, wcs, false);
    }

    private static int genDocx(Lang lang, Lang explang, List<Homonym> homonyms, WordClass[] wcs, boolean full) {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p;

        int counter = 0;

        for (Homonym w: homonyms) {

            if (w.isEnabled() && contains(wcs, w)) {
                counter++;

                p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.BOTH);
                p.setIndentationHanging(300);
                p.setSpacingLineRule(LineSpacingRule.EXACT);
                p.setSpacingBeforeLines(0);
                p.setSpacingBefore(0);
                p.setSpacingAfterLines(0);
                p.setSpacingAfter(0);

                System.out.println(w.getOrig());
                addBold(p, w.getOrig());

                if (full) {
                    //paradigm
                    if (StringUtils.isNotBlank(w.getWords().get(0).getParadigm())){
                        addSuper(p, w.getWords().get(0).getParadigm());
                    }
                }

                //pronunciation
                if (StringUtils.isNotBlank(w.getPronunciation())) {
                    addSpace(p);
                    addLeftSquareBracket(p);
                    addNormal(p, w.getPronunciation());
                    addRightSquareBracket(p);
                }

                for (int wti = 0; wti < w.getWords().size(); wti++) {
                    Word wt = w.getWords().get(wti);

                    boolean wc, numbering = false; String color = null;

                    if (!ArrayUtils.contains(wcs, wt.getWordClass())) {// && (wcs[0].isNone() && wt.getWordClass() == null)) { //wcs.get(0).isAll() ||
                        color = GRAY;
                    }

                    //ignore everything for old orthography
                    if (wt.getForms() != null && wt.hasLinkForms()) {

                        if (wt.hasForms()) {
                            addForms(p, wt, color, explang);
                        }

                        addClassCaseNumberGender(p, wt, color, explang);
                        addSpace(p);
                        addItalic(p, wt.getLinkForm().getType().getPrint(explang), color);
                        addSpace(p);
                        String str;
                        if (wt.hasGrafForm()) {
                            str = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(),
                                StringHelper.LINK + StringHelper.SPACE);
                        } else { //form FormType.LINK_SK_VERB_IMP
                            str = StringUtils.substringBetween(wt.getMeanings().get(0).getSynonyms(),
                                    StringHelper.LINK + StringHelper.SPACE,
                                    StringHelper.SPACE + StringHelper.LEFTPARENTHESIS + SignificanceType.PERF.getKey());
                        }
                        addUnderline(p, str, color);
                        if (!wt.hasGrafForm()) {
                            String k = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(), str + StringHelper.SPACE);
                            k = StringUtils.removeEnd(k.substring(1, k.length() - 1), StringHelper.DOT);
                            addItalic(p, StringHelper.SPACE + StringHelper.LEFTPARENTHESIS
                                    + SignificanceType.valueOfKey(k).getPrint(explang)
                                    + StringHelper.RIGHTPARENTHESIS, color);
                        }

                    } else { //proceed with normal formatting

                        if (wt.hasForms()) {
                            addForms(p, wt, color, explang);
                        }

                        addClassCaseNumberGender(p, wt, color, explang);

                        //alternative spelling
                        if (w.getAlternatives() != null && !w.getAlternatives().isEmpty()) {
                            for (Alternative alt: w.getAlternatives()) {

                                if (alt.getType() == AltType.ALTERNATIVE) {

                                    addCommaSpace(p, color);
                                    addBold(p, alt.getValue().trim(), color);

                                    if (alt.getWordClass() != null || alt.getNumberGender() != null) {
                                        addSpace(p);
                                        wc = false;
                                        if (alt.getWordClass() != null) {
                                            addItalic(p, alt.getWordClass().getPrint(explang), color);
                                            wc = true;
                                        }
                                        if (alt.getNumberGender() != null) {
                                            if (wc) addSpace(p); //addCommaSpace(p);
                                            addItalic(p, alt.getNumberGender().getPrint(explang), color);
                                        }
                                    }
                                }
                            }
                        }

                        if (wt.hasVerbForm()) {
                            addSpace(p);
                            addVerbForm(p, wt.getForms().get(0), color, explang);
                        }

                        if (wt.getMeanings().size() > 1) numbering = true;

                        for (int i = 0; i < wt.getMeanings().size(); i++) {
                            Meaning m = wt.getMeanings().get(i);

                            if (numbering) {
                                addSpace(p);
                                addBold(p, String.valueOf(i + 1) + StringHelper.DOT,  color);
                            }

                            if (m.getFieldType() != null) {
                                addSpace(p);
                                addItalic(p, Style.ODB.getPrint(explang) + StringHelper.SPACE + m.getFieldType().getPrint(explang), color);
                            } else if (m.getStyle() != null) {
                                addSpace(p);
                                addItalic(p, m.getStyle().getPrint(explang), color);
                            }

                            //addSpace(p);
                            if (m.isContraction()) {
                                addContraction(p, m.getContraction(), color, lang, explang, wt.getNumberGender());
                            } else {
                                addFormatted(p, m.getSynonyms(), color, lang, explang, wt.getNumberGender());
                            }

                            if (full) {
                                //expressions
                                if (m.getExpressions() != null && !m.getExpressions().isEmpty()) {
                                    addSemicolon(p, color);
                                    addSpace(p);
                                    for (int j = 0; j < m.getExpressions().size(); j++) {
                                        Phraseme ph = m.getExpressions().get(j);
                                        //specifics for phrasemes
                                        addBold(p, ph.getOrig(), color);
                                        if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                                            addSpace(p);
                                            addItalicWithParentheses(p, ph.getType().getPrint(explang), color);
                                        }
                                        //addSpace(p);
                                        addFormatted(p, ph.getTran(), color, lang, explang, wt.getNumberGender());
                                        if (j < m.getExpressions().size() - 1) {
                                            addSemicolon(p, color);
                                            addSpace(p);
                                        }
                                    }
                                }
                            }
                        }

                        //next homonym type delimiter
                        if (wti < w.getWords().size() - 1) {
                            addSpace(p);
                            addWordTypeDelimiter(p, color);
                        }
                    }
                }

                if (full) {
                    //idioms
                    if (w.getIdioms() != null && !w.getIdioms().isEmpty()) {
                        addSpace(p);
                        addIdiomDelimiter(p);

                        for (int i = 0; i < w.getIdioms().size(); i++) {
                            Phraseme idiom = w.getIdioms().get(i);
                            addSpace(p);
                            addBold(p, idiom.getOrig());
                            if (idiom.getType() == PhrasemeType.FIX || idiom.getType() == PhrasemeType.SEMIFIX) {
                                addSpace(p);
                                addItalicWithParentheses(p, idiom.getType().getPrint(explang));
                            }
                            //addSpace(p);
                            addFormatted(p, idiom.getTran(), null, lang, explang, null);

                            if (i < w.getIdioms().size() - 1) {
                                addSemicolon(p);
                            }
                        }
                    }
                }
            }
        }

        FileOutputStream out;
        if (counter > 0) {
            try {
                out = new FileOutputStream("_wordexports\\" + (full ? "full_" : "") + "export_" + wcs[0].getGroup() + "_" + lang.getKey() + "_ver_" + explang.getKey() + ".docx");
                doc.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return counter;
    }

    private static void addForms(XWPFParagraph p, Word wt, String color, Lang explang) {
        addSpace(p);
        addLeftParenthesis(p, color);
        for (int i = 0; i < wt.getForms().size(); i++) {
            Form f = wt.getForms().get(i);
            if (f.getType() != FormType.UNDEF && f.getType() != FormType.PARTVERB
                    && f.getType() != FormType.LINK_GRAFDUPL && f.getType() != FormType.LINK_GRAFANT && f.getType() != FormType.LINK_SK_VERB_IMP) {
                addItalic(p, f.getType().getPrint(explang));
                if (StringUtils.isNotBlank(f.getValues())) {
                    addSpace(p);
                    addBold(p, f.getValues(), color);
                }
                if (i < wt.getFormsSize() - 1) {
                    addCommaSpace(p);
                }
            }
        }
        addRightParenthesis(p, color);
    }

}
