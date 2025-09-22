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
            throw new ConversionException("Kiểu chuyển đổi không hợp lệ (null).");
        }
        String v = (value == null) ? "" : value.trim();
        if (v.isEmpty()) {
            throw new ConversionException("Giá trị đầu vào không được để trống.");
        }

        // TEXT -> TEXT
        if (from == BaseType.TEXT && to == BaseType.TEXT) {
            return v;
        }

        // TEXT -> Numeric
        if (from == BaseType.TEXT) {
            int toBase = to.getBase();
            if (toBase < 2 || toBase > 36) {
                throw new ConversionException("Không thể chuyển TEXT sang " + to + ".");
            }
            // Mỗi code point -> 1 token ở cơ số đích (cách nhau khoảng trắng)
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
                throw new ConversionException("Không thể chuyển " + from + " sang TEXT.");
            }
            // Cho phép phân tách bởi khoảng trắng hoặc dấu phẩy
            String cleaned = v.replace(',', ' ').trim().replaceAll("\\s+", " ");
            if (cleaned.isEmpty()) {
                throw new ConversionException("Không tìm thấy token số để chuyển sang TEXT.");
            }

            StringBuilder sb = new StringBuilder();
            for (String token : cleaned.split(" ")) {
                if (token.isBlank()) continue;
                String digits = token.replaceAll("[\\s_]", ""); // bỏ space/underscore trong từng token
                BigInteger num;
                try {
                    num = new BigInteger(digits, fromBase);
                } catch (NumberFormatException ex) {
                    throw new ConversionException("Token \"" + token + "\" không hợp lệ cho base " + fromBase + ".", ex);
                }
                // Code point hợp lệ: 0..0x10FFFF
                if (num.signum() < 0 || num.compareTo(BigInteger.valueOf(0x10FFFF)) > 0) {
                    throw new ConversionException("Giá trị code point ngoài phạm vi Unicode: " + num);
                }
                int cp = num.intValue(); // an toàn vì đã giới hạn
                sb.appendCodePoint(cp);
            }
            return sb.toString();
        }

        // Numeric -> Numeric (BigInteger, không giới hạn độ dài; hỗ trợ dấu âm)
        int fromBase = from.getBase();
        int toBase   = to.getBase();
        if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
            throw new ConversionException("Base phải nằm trong khoảng 2..36.");
        }

        String digits = v.replaceAll("[\\s_]", ""); // chấp nhận "1111 1111" hoặc "7FFF_FFFF"
        try {
            String out = BaseConverterUtil.convertBase(digits, fromBase, toBase);
            return (toBase == 16) ? out.toUpperCase() : out;
        } catch (IllegalArgumentException ex) {
            // Bọc lại thành ConversionException để controller/handler xử lý thống nhất
            throw new ConversionException(ex.getMessage(), ex);
        }
    }
}
