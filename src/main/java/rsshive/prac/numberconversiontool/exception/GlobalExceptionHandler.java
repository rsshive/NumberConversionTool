
package rsshive.prac.numberconversiontool.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rsshive.prac.numberconversiontool.model.BaseType;
import rsshive.prac.numberconversiontool.model.ConvertRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConversionException.class)
    public String handleConversion(ConversionException ex, Model model) {

        if (!model.containsAttribute("convertRequest")) {
            ConvertRequest req = new ConvertRequest();
            req.setFromBase(BaseType.TEXT);
            req.setToBase(BaseType.DECIMAL);
            model.addAttribute("convertRequest", req);
        }
        model.addAttribute("modes", BaseType.values());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("result", null);
        return "index";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegal(IllegalArgumentException ex, Model model) {
        return handleConversion(new ConversionException(ex.getMessage()), model);
    }
}
