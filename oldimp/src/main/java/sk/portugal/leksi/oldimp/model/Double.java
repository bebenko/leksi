package sk.portugal.leksi.oldimp.model;

/**
 */
public class Double {

    private String p;
    private String s;
    private String skr;
    private String skr2;

    public Double(String p, String s, String skr, String skr2) {
        this.p = p;
        this.s = s;
        this.skr = skr;
        this.skr2 = skr2;
    }


    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getSkr() {
        return skr;
    }

    public void setSkr(String skr) {
        this.skr = skr;
    }

    public String getSkr2() {
        return skr2;
    }

    public void setSkr2(String skr2) {
        this.skr2 = skr2;
    }
}
