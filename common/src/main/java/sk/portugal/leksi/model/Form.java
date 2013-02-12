package sk.portugal.leksi.model;

import sk.portugal.leksi.model.enums.FormType;

/**
 */
public class Form {

    private FormType type;
    private String values;

    public Form() {}

    public Form(String values) {
        this.values = values;
    }

    public Form(FormType type, String values) {
        this.type = type;
        this.values = values;
    }

    public FormType getType() {
        return type;
    }

    public void setType(FormType type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
