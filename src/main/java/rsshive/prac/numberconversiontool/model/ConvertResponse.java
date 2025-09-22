package rsshive.prac.numberconversiontool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ConvertResponse {
    private String original;     // <— tên field PHẢI trùng với bạn dùng trong template
    private BaseType fromBase;
    private String result;
    private BaseType toBase;
}
