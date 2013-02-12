package sk.portugal.leksi.util.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: juraj.benko
 * Date: 20/01/13
 * Time: 16:56
 */
public class StringHelper {

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

    public static final char LEFTPARENTHESISCHAR = '(';
    public static final char RIGHTPARENTHESISCHAR = ')';

    public static final String IMP = "(imp.)";
    public static final String PERF = "(perf.)";
    public static final String EXPRFIX = "expr. fix.";
    public static final String EXPRSEMIFIX = "expr. semi-fix.";

    public static final String PRONOMINAL = "pronominal";

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
        boolean foundMatching = false;
        int depthCounter = 0;
        int ind = 0;
        while (!foundMatching) {
            if (str.charAt(ind) == LEFTPARENTHESISCHAR) depthCounter++;
            else if (str.charAt(ind) == RIGHTPARENTHESISCHAR) depthCounter--;
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
}
