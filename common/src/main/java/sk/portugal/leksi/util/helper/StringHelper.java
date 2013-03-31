package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: juraj.benko
 * Date: 20/01/13
 * Time: 16:56
 */
public class StringHelper {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String COMMASPACE = COMMA + SPACE;
    public static final String LEFTPARENTHESIS = "(";
    public static final String RIGHTPARENTHESIS = ")";
    public static final String LEFTSQUAREBRACKET = "[";
    public static final String RIGHTSQUAREBRACKET = "]";
    public static final String SLASH = "/";
    public static final String DASH = "-";
    public static final String DOUBLEHASH = "##";
    public static final String LEFTPARENDASH = LEFTPARENTHESIS + DASH;

    public static final String[] DELIMITERS = new String[] {DOT, COMMA, COLON, SEMICOLON};
    public static final String[] GENDERSTRINGS = new String[] {"(f)", "(m)", "(m/f)", "(f/pl)", "(m/pl)", "(pl)", "(n)", "(nt)", "(n/pl)"};
    public static final String[] GENDERSTRINGSLEFT = new String[] {"(f ", "(m ", "(pl ", "(n ", "(nt ", "(cf. "};

    public static final char LEFTPARENTHESISCHAR = '(';
    public static final char RIGHTPARENTHESISCHAR = ')';
    public static final char LEFTSQUAREBRACKETCHAR = '[';
    public static final char RIGHTSQUAREBRACKETCHAR = ']';

    public static final String IMP = "(imp.)";
    public static final String PERF = "(perf.)";
    public static final String IMPPERF = "(imp./perf.)";
    public static final String EXPRFIX = "expr. fix.";
    public static final String EXPRSEMIFIX = "expr. semi-fix.";
    public static final String GRAFANT = "graf. ant.";
    public static final String STPRAV = "starý pravopis";

    public static final String PLINV = "pl inv";

    public static final String LINK = "→";


    public static String extractSpecification(String str) {
        str = StringUtils.trimToEmpty(str);
        if (str.startsWith("(")) {
            return str.substring(0, findMatchingBracket(str));
        }
        return "";
    }

    public static String stripSpecification(String str, String spec) {
        return StringUtils.trimToEmpty(StringUtils.removeStart(str, spec));
    }

    public static int findMatchingBracket(String str) {
        return findMatchingBracket(str, LEFTPARENTHESISCHAR, RIGHTPARENTHESISCHAR);
    }

    public static int findMatchingBracket(String str, char chr1, char chr2) {
        boolean foundMatching = false;
        int depthCounter = 0;
        int ind = 0;
        while (!foundMatching) {
            if (str.charAt(ind) == chr1) depthCounter++;
            else if (str.charAt(ind) == chr2) depthCounter--;
            ind++;
            if (depthCounter == 0) foundMatching = true;
        }
        return ind;
    }

    public static String removeExpr(String str) {
        String result = str;
        result = StringUtils.removeEnd(result, LEFTPARENTHESIS + EXPRFIX + RIGHTPARENTHESIS);
        result = StringUtils.removeEnd(result, EXPRFIX);
        result = StringUtils.removeEnd(result, LEFTPARENTHESIS + EXPRSEMIFIX + RIGHTPARENTHESIS);
        result = StringUtils.removeEnd(result, EXPRSEMIFIX);
        return result;
    }

    public static boolean startsWithDelimiter(String str) {
        if (str.startsWith(COMMA)) return true;
        if (str.startsWith(SEMICOLON)) return true;
        return false;
    }

    public static boolean containsTwo(String str, String cont) {
        int i1;
        if ((i1 = StringUtils.indexOf(str, cont)) > 0 && StringUtils.indexOf(str, cont, i1 + 1) > 0) return true;
        return false;
    }
}
