package rsshive.prac.numberconversiontool.util;

import java.math.BigInteger;

public class BaseConverterUtil {

    public static String convertBase(String value, int fromBase, int toBase) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Giá trị đầu vào không được để trống.");
        }
        try {
            BigInteger number = new BigInteger(value, fromBase);
            return number.toString(toBase);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Giá trị \"" + value + "\" không hợp lệ cho base " + fromBase);
        }
    }

    public static String textToBase(String text, int base) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(Integer.toString(c, base));
        }
        return sb.toString();
    }

    public static String baseToText(String encoded, int base) {
        StringBuilder sb = new StringBuilder();
        for (String token : encoded.trim().split("\\s+")) {
            int code = Integer.parseInt(token, base);
            sb.append((char) code);
        }
        return sb.toString();
    }
}
