package rsshive.prac.numberconversiontool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvertRequest {
    private String value;
    private BaseType fromBase;
    private BaseType toBase;
}
