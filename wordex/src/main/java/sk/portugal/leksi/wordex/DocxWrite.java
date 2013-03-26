package sk.portugal.leksi.wordex;

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
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SEMICOLON);
    }

    private static void addCommaSpace(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.COMMASPACE);
    }

    private static void addLeftParenthesis(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTPARENTHESIS);
    }

    private static void addRightParenthesis(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTPARENTHESIS);
    }

    private static void addLeftSquareBracket(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTSQUAREBRACKET);
    }

    private static void addRightSquareBracket(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTSQUAREBRACKET);
    }

    private static void addWordTypeDelimiter(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(WORDTYPEDELIMITER);
    }

    private static void addIdiomDelimiter(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(IDIOMDELIMITER);
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
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setText(StringHelper.LEFTPARENTHESIS + str + StringHelper.RIGHTPARENTHESIS);
    }

    private static String addFormattedParentheses(XWPFParagraph p, String str, boolean print, Lang explang, NumberGender ng) {
        if (str.startsWith(StringHelper.LEFTPARENDASH)) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            addNormal(p, StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1);
            if (print)
                addItalic(p, s.trim());
        } else if (str.matches("\\(pl\\) \\(@.*")) { //extra condition for separate plural meaning
            addItalic(p, NumberGender.valueOfKey("pl").getPrint(explang));
            addSpace(p);
            String s = str.substring(5);
            s = s.substring(3, StringHelper.findMatchingBracket(s) - 1);
            addBold(p, s);
            return StringUtils.substring(str, StringHelper.findMatchingBracket(StringUtils.removeStart(str, "(pl) ")) + 5);
        } else if (str.startsWith("(@")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 1);
            addBold(p, s.trim());
        } else {
            String s = str.substring(0, StringHelper.findMatchingBracket(str));
            //!FORMATTING
            if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
                NumberGender nng = NumberGender.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT));
                if (ng != nng) {
                    s = StringHelper.LEFTPARENTHESIS + nng.getPrint(explang) + StringHelper.RIGHTPARENTHESIS;
                    addItalic(p, s);
                }
            } else if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGSLEFT) && s.contains(StringHelper.DASH)) {
                s = StringUtils.removeStart(StringUtils.removeEnd(s, StringHelper.RIGHTPARENTHESIS), StringHelper.LEFTPARENTHESIS);
                addNormal(p, StringHelper.LEFTPARENTHESIS);

                String sxx; NumberGender sng;
                String[] sx = s.split(StringHelper.COMMA);
                for (int i = 0; i < sx.length; i++) {
                    sxx = StringUtils.substringBefore(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                    if ((sng = NumberGender.valueOfKey(sxx.trim())) != NumberGender.UNDEF) sxx = sng.getPrint(explang);
                    addNormal(p, sxx + StringHelper.SPACE);
                    sxx = StringUtils.substringAfter(StringUtils.removeStart(sx[i], StringHelper.SPACE), StringHelper.SPACE);
                    sxx = StringUtils.removeStart(StringUtils.removeEnd(sxx, StringHelper.DASH), StringHelper.DASH);
                    addItalic(p, sxx);
                    if (i < sx.length - 1) addCommaSpace(p);
                }
                addNormal(p, StringHelper.RIGHTPARENTHESIS);
            } else if (s.equals(StringHelper.PERF) || s.equals(StringHelper.IMP) || s.equals(StringHelper.IMPPERF)) {
                addItalic(p, StringHelper.LEFTPARENTHESIS
                        + SignificanceType.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT)).getPrint(explang)
                        + StringHelper.RIGHTPARENTHESIS);
            } else if (s.contains(StringHelper.COLON)) {
                if (s.startsWith(StringHelper.LEFTPARENTHESIS + explang.getKey() + StringHelper.COLON)) {
                    addItalic(p, StringHelper.LEFTPARENTHESIS + StringUtils.stripStart(StringUtils.substringAfter(s, StringHelper.COLON), null));
                }
            } else if (print) {
                addItalic(p, s);
            }
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str));
    }

    private static void addParticip(XWPFParagraph p, String str, Lang explang) {
        String s = str, ss; //, color = GRAY;
        s = StringUtils.replaceEach(s, new String[] {FormType.P.getKey() + StringHelper.SPACE, FormType.PP.getKey() + StringHelper.SPACE},
                new String[] {FormType.P.getPrint(explang) + StringHelper.SPACE, FormType.PP.getPrint(explang) + StringHelper.SPACE});
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS + StringHelper.DOUBLEHASH)) {
                //s = addFormattedParentheses(p, s, true, explang);
                ss = s.substring(3, StringHelper.findMatchingBracket(s) - 1);
                addItalic(p, ss.trim()); //, color);
                s = StringUtils.substring(s, StringHelper.findMatchingBracket(s));
            } else {
                //TODO underline (linkify) particips
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                addNormal(p, ss); //, color);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }

            if (StringUtils.isNotBlank(s) && !StringUtils.startsWithAny(s, StringHelper.DELIMITERS)
                    && !StringUtils.startsWith(s, StringHelper.RIGHTSQUAREBRACKET)) {
                addSpace(p);
            }
        }
    }

    private static String addFormattedSquareBrackets(XWPFParagraph p, String str, Lang lang, Lang explang) {
        String s = StringUtils.substring(str, 0, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
        if (s.indexOf(StringHelper.COLON) > 0) {
            Lang intflang = Lang.valueOfKey(StringUtils.substringBetween(s, StringHelper.LEFTSQUAREBRACKET, StringHelper.COLON));
            if (intflang == explang) {
                addNormal(p, StringUtils.remove(s, intflang.getKey() + StringHelper.COLON + StringHelper.SPACE));
            }
        } else if (s.contains(FormType.P.getKey() + StringHelper.SPACE)
                || s.contains(FormType.PP.getKey() + StringHelper.SPACE)) {
            addParticip(p, s, explang);
        } else {
            addNormal(p, s);
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
    }

    private static void addFormatted(XWPFParagraph p, String str, Lang lang, Lang explang, NumberGender ng) {
        String s = str, ss; boolean first = true;
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                //intf.-lang.-based content
                s = StringUtils.stripStart(addFormattedParentheses(p, s,
                        (first && lang == explang) || (!first && lang != explang), explang, ng), null);
            } else if (s.startsWith(StringHelper.LEFTSQUAREBRACKET)) {
                s = StringUtils.stripStart(addFormattedSquareBrackets(p, s, lang, explang), null);
            } else {
                ss = StringUtils.substringBefore(s, StringHelper.SPACE);
                addNormal(p, ss);
                s = StringUtils.stripStart(StringUtils.removeStart(s, ss), null);
            }

            //add space
            if (StringUtils.isNotBlank(s)) {
                if (!StringUtils.startsWithAny(s, StringHelper.DELIMITERS)) {
                    if (StringUtils.startsWith(s, StringHelper.LEFTSQUAREBRACKET) && s.indexOf(StringHelper.COLON) > 0) {
                        if (Lang.valueOfKey(s.substring(1, 3)) == explang) {
                            addSpace(p);
                        }
                    } else if (StringUtils.startsWith(s, StringHelper.LEFTPARENDASH)) {
                        addSpace(p);
                    } else if (StringUtils.startsWith(s, StringHelper.LEFTPARENTHESIS)) {
                        if (s.startsWith(StringHelper.LEFTPARENTHESIS + explang.getKey() + StringHelper.COLON)) {
                            addSpace(p);
                        } else if (s.startsWith(StringHelper.LEFTPARENTHESIS + explang.getOtherLangKey() + StringHelper.COLON)) {
                            //don't add space
                        } else if (StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
                            if (ng != NumberGender.valueOfKey(StringUtils.removeEnd(StringUtils.removeStart(StringUtils.removeEnd(StringUtils.substringBefore(s, StringHelper.SPACE),
                                    StringHelper.DOT), StringHelper.LEFTPARENTHESIS), StringHelper.RIGHTPARENTHESIS + StringHelper.COMMA))) {
                                addSpace(p);
                            }
                        } else if (lang != explang || s.startsWith(StringHelper.PERF) || s.startsWith(StringHelper.IMP) || s.startsWith(StringHelper.IMPPERF)
                                || StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGSLEFT)) {
                            addSpace(p);
                        }
                    } else if (!(str.startsWith(StringHelper.LEFTPARENTHESIS) && first && lang != explang)) { //just removed parentheses, (don't) add space
                        addSpace(p);
                    }
                }
            }

            first = false;
        }
    }

    private static void addContraction(XWPFParagraph p, Contraction c, Lang explang) {
        String str = PhrasemeType.CONTR.getPrint(explang) + StringHelper.SPACE
                + c.getFirstWordType().getWordClass().getPrint(explang) + StringHelper.SPACE;
        addItalic(p, str);
        addItalicUnderline(p, c.getFirstWord().getOrig());
        str = StringHelper.SPACE + LangHelper.getAnd(explang) + StringHelper.SPACE
                + c.getSecondWordType().getWordClass().getPrint(explang) + StringHelper.SPACE;
        if (c.getSecondWordType().getCaseType() != null && StringUtils.isNotBlank(c.getSecondWordType().getCaseType().getPrint(explang))) {
            str += c.getSecondWordType().getCaseType().getPrint(explang) + StringHelper.SPACE;
        }
        if (c.getSecondWordType().getNumberGender() != null && StringUtils.isNotBlank(c.getSecondWordType().getNumberGender().getPrint(explang))) {
            str += c.getSecondWordType().getNumberGender().getPrint(explang) + StringHelper.SPACE;
        }
        addItalic(p, str);
        addItalicUnderline(p, c.getSecondWord().getOrig());
        if (c.getAdditionalText() != null) {
            addNormal(p, StringHelper.SPACE + c.getAdditionalText());
        }
    }

    private static void addVerbForm(XWPFParagraph p, Form vf, Lang explang) {
        addNormal(p, StringHelper.LEFTSQUAREBRACKET);
        addItalic(p, LangHelper.getFormOfVerb(explang));
        addNormal(p, StringHelper.SPACE);
        addUnderline(p, vf.getValues());
        addNormal(p, StringHelper.RIGHTSQUAREBRACKET);
    }

    private static void addClassCaseNumberGender(XWPFParagraph p, WordType wt, Lang explang) {
        boolean wc = false, ct = false;
        if (wt.getWordClass() != null && StringUtils.isNotBlank(wt.getWordClass().getPrint(explang))) {
            addSpace(p);
            if (wt.getWordClass() != null && (wt.getWordClass() == WordClass.P || wt.getWordClass() == WordClass.PP)) {
                //for particips
                addItalic(p, wt.getWordClass().getPrint(explang) + StringHelper.SPACE + LangHelper.getOf(explang) + StringHelper.SPACE );
                addItalicUnderline(p, wt.getForms().get(0).getValues());
            } else {
                addItalic(p, wt.getWordClass().getPrint(explang));
            }
            wc = true;
        }
        if (wt.getCaseType() != null && StringUtils.isNotBlank(wt.getCaseType().getPrint(explang))) {
            if (wc) addSpace(p);
            addItalic(p, wt.getCaseType().getPrint(explang));
            ct = true;
        }
        if (wt.getNumberGender() != null && StringUtils.isNotBlank(wt.getNumberGender().getPrint(explang))) {
            if (wc || ct) addSpace(p);
            addItalic(p, wt.getNumberGender().getPrint(explang));
        }
    }

    public static void export(Lang lang, Lang explang, List<Word> words) {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p;

        for (Word w: words) {

            if (w.isEnabled()) {

                p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.BOTH);
                p.setIndentationHanging(300);
                p.setSpacingLineRule(LineSpacingRule.EXACT);
                p.setSpacingBeforeLines(0);
                p.setSpacingBefore(0);
                p.setSpacingAfterLines(0);
                p.setSpacingAfter(0);

                //System.out.println(w.getOrig());
                addBold(p, w.getOrig());

                //paradigm
                //if (StringUtils.isNotBlank(w.getWordTypes().get(0).getParadigm())){
                //    addSuper(p, w.getWordTypes().get(0).getParadigm());
                //}

                //pronunciation
                if (StringUtils.isNotBlank(w.getPronunciation())) {
                    addSpace(p);
                    addLeftSquareBracket(p);
                    addNormal(p, w.getPronunciation());
                    addRightSquareBracket(p);
                }

                for (int wti = 0; wti < w.getWordTypes().size(); wti++) {
                    WordType wt = w.getWordTypes().get(wti);
                    boolean wc, numbering = false;

                    //ignore everything for old orthography
                    if (wt.getForms() != null && wt.getForms().get(0).getType() != null
                            && (wt.getForms().get(0).getType() == FormType.LINK_ORT || wt.getForms().get(0).getType() == FormType.LINK_SK_VERB_IMP)) {

                        if (wt.getForms().get(0).getType() == FormType.LINK_SK_VERB_IMP) {
                            addClassCaseNumberGender(p, wt, explang);
                        }
                        addSpace(p);
                        addItalic(p, wt.getForms().get(0).getType().getPrint(explang));
                        addSpace(p);
                        String str;
                        if (wt.getForms().get(0).getType() == FormType.LINK_ORT) {
                            str = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(),
                                StringHelper.LINK + StringHelper.SPACE);
                        } else { //wt.getForms().get(0).getType() == FormType.LINK_SK_VERB_IMP
                            str = StringUtils.substringBetween(wt.getMeanings().get(0).getSynonyms(),
                                    StringHelper.LINK + StringHelper.SPACE,
                                    StringHelper.SPACE + StringHelper.LEFTPARENTHESIS + SignificanceType.PERF.getKey());
                        }
                        addUnderline(p, str);
                        if (wt.getForms().get(0).getType() == FormType.LINK_SK_VERB_IMP) {
                            String k = StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(), str + StringHelper.SPACE);
                            k = StringUtils.removeEnd(k.substring(1, k.length() - 1), StringHelper.DOT);
                            addItalic(p, StringHelper.SPACE + StringHelper.LEFTPARENTHESIS
                                    + SignificanceType.valueOfKey(k).getPrint(explang)
                                    + StringHelper.RIGHTPARENTHESIS);
                        }

                    } else { //proceed with normal formatting

                        if (wt.hasForms()) {
                            addSpace(p);
                            addLeftParenthesis(p);
                            for (int i = 0; i < wt.getForms().size(); i++) {
                                Form f = wt.getForms().get(i);
                                if (f.getType() != FormType.UNDEF && f.getType() != FormType.PARTVERB) {
                                    addItalic(p, f.getType().getPrint(explang));
                                    if (StringUtils.isNotBlank(f.getValues())) {
                                        addSpace(p);
                                        addBold(p, f.getValues());
                                    }
                                    if (i < wt.getForms().size() - 1) {
                                        addCommaSpace(p);
                                    }
                                }
                            }
                            addRightParenthesis(p);
                        }

                        addClassCaseNumberGender(p, wt, explang);

                        //alternative spelling
                        if (w.getAlternatives() != null && !w.getAlternatives().isEmpty()) {
                            for (Alternative alt: w.getAlternatives()) {

                                if (alt.getType() == AltType.ALTERNATIVE) {

                                    addCommaSpace(p);
                                    addBold(p, alt.getValue().trim());

                                    if (alt.getWordClass() != null || alt.getNumberGender() != null) {
                                        addSpace(p);
                                        wc = false;
                                        if (alt.getWordClass() != null) {
                                            addItalic(p, alt.getWordClass().getPrint(explang));
                                            wc = true;
                                        }
                                        if (alt.getNumberGender() != null) {
                                            if (wc) addSpace(p); //addCommaSpace(p);
                                            addItalic(p, alt.getNumberGender().getPrint(explang));
                                        }
                                    }
                                }
                            }
                        }

                        if (wt.hasVerbForm()) {
                            addSpace(p);
                            addVerbForm(p, wt.getForms().get(0), explang);
                        }

                        if (wt.getMeanings().size() > 1) numbering = true;

                        for (int i = 0; i < wt.getMeanings().size(); i++) {
                            Meaning m = wt.getMeanings().get(i);

                            if (numbering) {
                                addSpace(p);
                                addBold(p, String.valueOf(i + 1) + StringHelper.DOT);
                            }

                            if (m.getFieldType() != null) {
                                addSpace(p);
                                addItalic(p, Style.ODB.getPrint(explang) + StringHelper.SPACE + m.getFieldType().getPrint(explang));
                            } else if (m.getStyle() != null) {
                                addSpace(p);
                                addItalic(p, m.getStyle().getPrint(explang));
                            }

                            addSpace(p);
                            if (m.isContraction()) {
                                addContraction(p, m.getContraction(), explang);
                            } else {
                                addFormatted(p, m.getSynonyms(), lang, explang, wt.getNumberGender());
                            }

                            //expressions
                            /* IGNORE EXAMPLES
                            if (m.getExpressions() != null && !m.getExpressions().isEmpty()) {
                                addSemicolon(p);
                                addSpace(p);
                                for (int j = 0; j < m.getExpressions().size(); j++) {
                                    Phraseme ph = m.getExpressions().get(j);
                                    //specifics for phrasemes
                                    addBold(p, ph.getOrig());
                                    if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                                        addSpace(p);
                                        addItalicWithParentheses(p, ph.getType().getPrint(explang));
                                    }
                                    addSpace(p);
                                    addFormatted(p, ph.getTran(), lang, explang);
                                    if (j < m.getExpressions().size() - 1) {
                                        addSemicolon(p);
                                        addSpace(p);
                                    }
                                }
                            } */
                        }

                        //next word type delimiter
                        if (wti < w.getWordTypes().size() - 1) {
                            addSpace(p);
                            addWordTypeDelimiter(p);
                        }
                    }
                }

                //idioms
                /* IGNORE EXAMPLES
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
                        addSpace(p);
                        addFormatted(p, idiom.getTran(), lang, explang);

                        if (i < w.getIdioms().size() - 1) {
                            addSemicolon(p);
                        }
                    }
                } */
            }
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream("export_" + lang.getKey() + "_ver_" + explang.getKey() + ".docx");
            doc.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
