package sk.portugal.leksi.wordex;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.*;
import sk.portugal.leksi.model.extra.Alternative;
import sk.portugal.leksi.model.extra.Contraction;
import sk.portugal.leksi.util.helper.StringHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 */
public class DocxWrite {

    public static final String IDIOMDELIMITER = "♦";
    public static final String WORDTYPEDELIMITER = "●";

    public static XWPFRun newRun(XWPFParagraph p) {
        XWPFRun r = p.createRun();
        r.setFontFamily("Times New Roman");
        r.setFontSize(9);
        return r;
    }

    public static void addSpace(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SPACE);
    }

    public static void addSemicolon(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.SEMICOLON);
    }

    public static void addCommaSpace(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.COMMASPACE);
    }

    public static void addLeftParenthesis(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTPARENTHESIS);
    }

    public static void addRightParenthesis(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTPARENTHESIS);
    }

    public static void addLeftSquareBracket(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.LEFTSQUAREBRACKET);
    }

    public static void addRightSquareBracket(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(StringHelper.RIGHTSQUAREBRACKET);
    }

    public static void addWordTypeDelimiter(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(WORDTYPEDELIMITER);
    }

    public static void addIdiomDelimiter(XWPFParagraph p) {
        XWPFRun r = newRun(p);
        r.setText(IDIOMDELIMITER);
    }

    public static void addNormal(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setText(str);
    }

    public static void addBold(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setBold(true);
        r.setText(str);
    }

    public static void addUnderline(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setUnderline(UnderlinePatterns.SINGLE);
        r.setText(str);
    }

    public static void addSuper(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setSubscript(VerticalAlign.SUPERSCRIPT);
        r.setText(str);
    }

    public static void addItalic(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setText(str);
    }

    public static void addItalicWithParentheses(XWPFParagraph p, String str) {
        XWPFRun r = newRun(p);
        r.setItalic(true);
        r.setText(StringHelper.LEFTPARENTHESIS + str + StringHelper.RIGHTPARENTHESIS);
    }

    public static String addFormattedParentheses(XWPFParagraph p, String str, boolean print, Lang explang) {
        if (str.startsWith("(-")) {
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
                s = StringHelper.LEFTPARENTHESIS
                        + NumberGender.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT)).getPrint(explang)
                        + StringHelper.RIGHTPARENTHESIS;
                addItalic(p, s);
            } else if (s.equals(StringHelper.PERF) || s.equals(StringHelper.IMP) || s.equals(StringHelper.IMPPERF)) {
                addItalic(p, StringHelper.LEFTPARENTHESIS
                        + SignificanceType.valueOfKey(StringUtils.remove(s.substring(1, s.length() - 1), StringHelper.DOT)).getPrint(explang)
                        + StringHelper.RIGHTPARENTHESIS);
            } else if (print) {
                addItalic(p, s);
            }
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str));
    }

    public static String addFormattedSquareBrackets(XWPFParagraph p, String str, Lang explang) {
        String s = StringUtils.substring(str, 0, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
        if (s.indexOf(StringHelper.COLON) > 0) {
            Lang lang = Lang.valueOfKey(StringUtils.substringBetween(s, StringHelper.LEFTSQUAREBRACKET, StringHelper.COLON));
            if (lang == explang) {
                addNormal(p, StringUtils.remove(s, lang.getKey() + StringHelper.COLON + StringHelper.SPACE));
            }
        } else {
            addNormal(p, s);
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str, StringHelper.LEFTSQUAREBRACKETCHAR, StringHelper.RIGHTSQUAREBRACKETCHAR));
    }

    public static void addFormatted(XWPFParagraph p, String str, Lang lang, Lang explang) {
        String s = str, ss; boolean first = true;
        while (StringUtils.isNotBlank(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
                //intf.-lang.-based content
                s = StringUtils.stripStart(addFormattedParentheses(p, s,
                        (first && lang == explang) || (!first && lang != explang), explang), null);
            } else if (s.startsWith(StringHelper.LEFTSQUAREBRACKET)) {
                s = StringUtils.stripStart(addFormattedSquareBrackets(p, s, explang), null);
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
                    } else if (StringUtils.startsWith(s, StringHelper.LEFTPARENTHESIS)) {
                        if (lang != explang || s.startsWith(StringHelper.PERF) || s.startsWith(StringHelper.IMP) || s.startsWith(StringHelper.IMPPERF)
                                || StringUtils.startsWithAny(s, StringHelper.GENDERSTRINGS)) {
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
        addNormal(p, str);
        addUnderline(p, c.getFirstWord().getOrig());
        str = StringHelper.SPACE + (explang == Lang.PT ? StringHelper.ANDPT : StringHelper.ANDSK) + StringHelper.SPACE
                + c.getSecondWordType().getWordClass().getPrint(explang) + StringHelper.SPACE;
        if (c.getSecondWordType().getCaseType() != null) {
            str += c.getSecondWordType().getCaseType().getPrint(explang) + StringHelper.SPACE;
        }
        if (c.getSecondWordType().getNumGend() != null) {
            str += c.getSecondWordType().getNumGend().getPrint(explang) + StringHelper.SPACE;
        }
        addNormal(p, str);
        addUnderline(p, c.getSecondWord().getOrig());
    }

    public static void export(Lang lang, Lang explang, List<Word> words) {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p;

        for (Word w: words) {

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
                boolean wc = false, ct = false, numbering = false;

                //ignore everything for old orthography
                if (wt.getForms() != null && wt.getForms().get(0).getType() != null
                        && wt.getForms().get(0).getType() == FormType.LINK_ORT) {
                    addSpace(p);
                    addItalic(p, wt.getForms().get(0).getType().getPrint(explang));
                    addSpace(p);
                    addUnderline(p, StringUtils.substringAfter(wt.getMeanings().get(0).getSynonyms(),
                            StringHelper.LINK + StringHelper.SPACE));

                } else { //proceed with normal formatting

                    if (wt.hasForms()) {
                        addSpace(p);
                        addLeftParenthesis(p);
                        for (int i = 0; i < wt.getForms().size(); i++) {
                            Form f = wt.getForms().get(i);
                            if (f.getType() != FormType.UNDEF) {
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

                    if (wt.getWordClass() != null) {
                        addSpace(p);
                        addItalic(p, wt.getWordClass().getPrint(explang));
                        wc = true;
                    }
                    if (wt.getCaseType() != null) {
                        if (wc) addSpace(p);
                        addItalic(p, wt.getCaseType().getPrint(explang));
                        ct = true;
                    }
                    if (wt.getNumGend() != null) {
                        if (wc || ct) addSpace(p);
                        addItalic(p, wt.getNumGend().getPrint(explang));
                    }

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
                            addFormatted(p, m.getSynonyms(), lang, explang);
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
