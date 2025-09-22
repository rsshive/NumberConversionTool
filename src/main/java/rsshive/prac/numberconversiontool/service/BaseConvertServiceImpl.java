package rsshive.prac.numberconversiontool.service;

import org.springframework.stereotype.Service;
import rsshive.prac.numberconversiontool.exception.ConversionException;
import rsshive.prac.numberconversiontool.model.BaseType;
import rsshive.prac.numberconversiontool.util.BaseConverterUtil;

import java.math.BigInteger;

@Service
public class BaseConvertServiceImpl implements BaseConvertService {

    @Override
    public String convert(String value, BaseType from, BaseType to) {
        if (from == null || to == null) {
            throw new ConversionException("Invalid conversion types.");
        }
        String v = (value == null) ? "" : value.trim();
        if (v.isEmpty()) {
            throw new ConversionException("Input value cannot be empty.");
        }

        // TEXT -> TEXT
        if (from == BaseType.TEXT && to == BaseType.TEXT) {
            return v;
        }

        // TEXT -> Numeric
        if (from == BaseType.TEXT) {
            int toBase = to.getBase();
            if (toBase < 2 || toBase > 36) {
                throw new ConversionException("Unable to convert TEXT into " + to + ".");
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < v.length(); ) {
                int cp = v.codePointAt(i);
                i += Character.charCount(cp);
                String token = BigInteger.valueOf(cp).toString(toBase);
                if (toBase == 16) token = token.toUpperCase();
                if (sb.length() > 0) sb.append(' ');
                sb.append(token);
            }
            return sb.toString();
        }

        // Numeric -> TEXT
        if (to == BaseType.TEXT) {
            int fromBase = from.getBase();
            if (fromBase < 2 || fromBase > 36) {
                throw new ConversionException("Unable to convert " + from + " to TEXT.");
            }

            String cleaned = v.replace(',', ' ').trim().replaceAll("\\s+", " ");
            if (cleaned.isEmpty()) {
                throw new ConversionException("Unable to convert empty value to TEXT.");
            }

            StringBuilder sb = new StringBuilder();
            for (String token : cleaned.split(" ")) {
                if (token.isBlank()) continue;
                String digits = token.replaceAll("[\\s_]", ""); // bỏ space/underscore trong từng token
                BigInteger num;
                try {
                    num = new BigInteger(digits, fromBase);
                } catch (NumberFormatException ex) {
                    throw new ConversionException("Token \"" + token + "\" is invalid from " + fromBase + ".", ex);
                }

                if (num.signum() < 0 || num.compareTo(BigInteger.valueOf(0x10FFFF)) > 0) {
                    throw new ConversionException("Code point is out of Unicode range : " + num);
                }
                int cp = num.intValue();
                sb.appendCodePoint(cp);
            }
            return sb.toString();
        }

        // Numeric -> Numeric
        int fromBase = from.getBase();
        int toBase   = to.getBase();
        if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
            throw new ConversionException("Base must be in the range of 2..36.");
        }

        String digits = v.replaceAll("[\\s_]", "");
        try {
            String out = BaseConverterUtil.convertBase(digits, fromBase, toBase);
            return (toBase == 16) ? out.toUpperCase() : out;
        } catch (IllegalArgumentException ex) {

            throw new ConversionException(ex.getMessage(), ex);
        }
    }
}
