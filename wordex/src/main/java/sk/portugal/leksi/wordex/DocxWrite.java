/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package sk.portugal.leksi.wordex;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import sk.portugal.leksi.model.*;
import sk.portugal.leksi.model.enums.AltType;
import sk.portugal.leksi.model.enums.FormType;
import sk.portugal.leksi.model.enums.Lang;
import sk.portugal.leksi.model.enums.PhrasemeType;
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

    public static String addFormattedParentheses(XWPFParagraph p, String str) {
        if (str.startsWith("(-")) {
            String s = str.substring(2, StringHelper.findMatchingBracket(str) - 2);
            addNormal(p, StringHelper.LEFTPARENTHESIS + s + StringHelper.RIGHTPARENTHESIS);
        } else if (str.startsWith("(##")) {
            String s = str.substring(3, StringHelper.findMatchingBracket(str) - 1);
            addItalic(p, s.trim());
        } else if (str.matches("\\(pl\\) \\(@.*")) { //special condition for separate plural meaning
            addItalic(p, "pl");
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
            addItalic(p, s);
        }
        return StringUtils.substring(str, StringHelper.findMatchingBracket(str));
    }

    public static void addFormatted(XWPFParagraph p, String str) {
        String s = str;
        while (StringUtils.isNotEmpty(s)) {
            if (s.startsWith(StringHelper.LEFTPARENTHESIS)) {
               s = addFormattedParentheses(p, s);
            }
            int endIndex = (s.indexOf(StringHelper.LEFTPARENTHESISCHAR) > 0) ? s.indexOf(StringHelper.LEFTPARENTHESISCHAR) : s.length();

            if (!StringHelper.startsWithDelimiter(s) && StringUtils.isNotEmpty(s)
                    && !s.startsWith(StringHelper.SPACE)&& !s.equals(str)) addSpace(p);
            addNormal(p, StringUtils.substring(s, 0, endIndex));
            s = StringUtils.removeStart(s, StringUtils.substring(s, 0, endIndex));
        }
    }

    public static void export(Lang lang, List<Word> words) {
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
            if (StringUtils.isNotBlank(w.getWordTypes().get(0).getParadigm())){
                addSuper(p, w.getWordTypes().get(0).getParadigm());
            }

            //pronunciation
            if (StringUtils.isNotBlank(w.getPronunciation())) {
                addSpace(p);
                addLeftSquareBracket(p);
                addNormal(p, w.getPronunciation());
                addRightSquareBracket(p);
            }

            for (int wti = 0; wti < w.getWordTypes().size(); wti++) {
                WordType wt = w.getWordTypes().get(wti);
                boolean wc = false, numbering = false;

                if (wt.hasForms()) {
                    addSpace(p);
                    addLeftParenthesis(p);
                    for (int i = 0; i < wt.getForms().size(); i++) {
                        Form f = wt.getForms().get(i);
                        if (/*f.getType() != FormType.PRON &&*/ f.getType() != FormType.UNDEF) {
                            addItalic(p, f.getType().getPrint());
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
                    addItalic(p, wt.getWordClass().getKey());
                    wc = true;
                }
                if (wt.getNumGend() != null) {
                    if (wc) addCommaSpace(p);
                    addItalic(p, wt.getNumGend().getKey());
                }

                /*if (wt.hasForms()) {
                    for (int i = 0; i < wt.getForms().size(); i++) {
                        Form v = wt.getForms().get(i);
                        if (v.getType() == FormType.PRON) { //pronominal only
                            addSpace(p);
                            addItalic(p, v.getValues());
                        }
                    }
                }*/

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
                                    addItalic(p, alt.getWordClass().getKey());
                                    wc = true;
                                }
                                if (alt.getNumberGender() != null) {
                                    if (wc) addCommaSpace(p);
                                    addItalic(p, alt.getNumberGender().getKey());
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

                    if (m.getField() != null) {
                        addSpace(p);
                        addItalic(p, m.getField().getKey());
                    } else if (m.getStyle() != null) {
                        addSpace(p);
                        addItalic(p, m.getStyle().getKey());
                    }

                    addSpace(p);
                    addFormatted(p, m.getSynonyms());

                    //expressions
                    if (m.getExpressions() != null && !m.getExpressions().isEmpty()) {
                        addSemicolon(p);
                        addSpace(p);
                        for (int j = 0; j < m.getExpressions().size(); j++) {
                            Phraseme ph = m.getExpressions().get(j);
                            //specifics for phrasemes
                            addBold(p, ph.getOrig());
                            if (ph.getType() == PhrasemeType.FIX || ph.getType() == PhrasemeType.SEMIFIX) {
                                addSpace(p);
                                addItalicWithParentheses(p, ph.getType().getPrint());
                            }
                            addSpace(p);
                            addFormatted(p, ph.getTran());
                            if (j < m.getExpressions().size() - 1) {
                                addSemicolon(p);
                                addSpace(p);
                            }
                        }
                    }
                }

                //next word type delimiter
                if (wti < w.getWordTypes().size() - 1) {
                    addSpace(p);
                    addWordTypeDelimiter(p);
                }
            }

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
                        addItalicWithParentheses(p, idiom.getType().getPrint());
                    }
                    addSpace(p);
                    addFormatted(p, idiom.getTran());

                    if (i < w.getIdioms().size() - 1) {
                        addSemicolon(p);
                    }
                }
            }
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream("export_" + lang.getKey() + ".docx");
            doc.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
