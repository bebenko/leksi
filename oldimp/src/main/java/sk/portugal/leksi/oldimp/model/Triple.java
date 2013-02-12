package sk.portugal.leksi.oldimp.model;

/**
 */
public class Triple {

    private String slovak;
    private String skratka;
    private String skratkab;

    public Triple (String slovak, String skratka, String skratkab) {
        this.slovak = slovak;
        this.skratka = skratka;
        this.skratkab = skratkab;
    }

    public String getSlovak() {
        return slovak;
    }

    public void setSlovak(String slovak) {
        this.slovak = slovak;
    }

    public String getSkratka() {
        return skratka;
    }

    public void setSkratka(String skratka) {
        this.skratka = skratka;
    }

    public String getSkratkab() {
        return skratkab;
    }

    public void setSkratkab(String skratkab) {
        this.skratkab = skratkab;
    }
}
