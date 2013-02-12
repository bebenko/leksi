package sk.portugal.leksi.oldimp.model;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Prtbl {

    private int id;
    private String portugal;
    private int popis;
    private int popis2;
    private List<Triple> vyznamy;
    private String tvar;
    private boolean chliev;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortugal() {
        return portugal;
    }

    public void setPortugal(String portugal) {
        this.portugal = portugal;
    }

    public int getPopis() {
        return popis;
    }

    public void setPopis(int popis) {
        this.popis = popis;
    }

    public int getPopis2() {
        return popis2;
    }

    public void setPopis2(int popis2) {
        this.popis2 = popis2;
    }

    public List<Triple> getVyznamy() {
        return vyznamy;
    }

    public void addVyznamy(String slovak, String skratka, String skratkab) {
        if (vyznamy == null) {
            vyznamy = new ArrayList<>();
        }
        vyznamy.add(new Triple(slovak, skratka, skratkab));
    }

    public String getTvar() {
        return tvar;
    }

    public void setTvar(String tvar) {
        this.tvar = tvar;
    }

    public boolean isChliev() {
        return chliev;
    }

    public void setChliev(boolean chliev) {
        this.chliev = chliev;
    }
}
