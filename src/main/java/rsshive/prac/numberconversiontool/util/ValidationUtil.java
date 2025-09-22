package rsshive.prac.numberconversiontool.util;

public class ValidationUtil {

    public static boolean isValidForBase(String value, int base) {
        String pattern;
        switch (base) {
            case 2:  pattern = "[01]+"; break;
            case 8:  pattern = "[0-7]+"; break;
            case 10: pattern = "\\d+"; break;
            case 16: pattern = "[0-9a-fA-F]+"; break;
            default: return true;
        }
        return value.matches(pattern);
    }
}
