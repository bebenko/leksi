package sk.portugal.leksi.oldimp.model;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Frazi {

    private String portugal;
    private List<Double> frazy;


    public String getPortugal() {
        return portugal;
    }

    public void setPortugal(String portugal) {
        this.portugal = portugal;
    }

    public List<Double> getFrazy() {
        return frazy;
    }

    public void addFrazy(List<Double> frazy) {
        this.frazy = frazy;
    }
    public void addFrazy(String p, String s, String skr, String skr2) {
        if (frazy == null) {
            frazy = new ArrayList<>();
        }
        frazy.add(new Double(p, s, skr, skr2));
    }

}
