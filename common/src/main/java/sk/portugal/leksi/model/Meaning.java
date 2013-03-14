package sk.portugal.leksi.model;

import org.apache.commons.lang3.StringUtils;
import sk.portugal.leksi.model.enums.FieldType;
import sk.portugal.leksi.model.enums.Style;
import sk.portugal.leksi.model.extra.Contraction;
import sk.portugal.leksi.util.helper.StringHelper;
import sk.portugal.leksi.util.helper.VariantHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Single meaning of the word
 */
public class Meaning {

    private FieldType fieldType;
    private Style style;
    private String synonyms;
    private List<Phraseme> expressions;
    private Contraction contraction;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public String getSynonymsSpec() {
        return StringHelper.extractSpecification(synonyms);
    }

    public String getSynonymsSyn() {
        return StringHelper.stripSpecification(synonyms, StringHelper.extractSpecification(synonyms));
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms.trim();
    }

    public List<Phraseme> getExpressions() {
        return expressions;
    }

    public void addExpression(Phraseme expr) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<>();
        }

        if (expr.getOrig().contains("###")) {
            String[] origSplits = StringUtils.splitByWholeSeparator(expr.getOrig(), "###");
            String[] tranSplits = StringUtils.splitByWholeSeparator(expr.getTran(), "###");
            for (int i = 0; i < origSplits.length; i++) {
                this.expressions.add(new Phraseme(origSplits[i], VariantHelper.getPhrasemeType(origSplits[i]),
                        tranSplits[i], expr.getFieldType(), expr.getStyle()));
            }
        } else {
            this.expressions.add(expr);
        }
    }

    public void setContraction(Contraction contraction) {
        this.contraction = contraction;
    }

    public Contraction getContraction() {
        return contraction;
    }

    public boolean isContraction() {
        if (contraction != null) return true;
        return false;
    }

}