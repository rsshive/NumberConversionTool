package rsshive.prac.numberconversiontool.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rsshive.prac.numberconversiontool.model.BaseType;
import rsshive.prac.numberconversiontool.model.ConvertRequest;
import rsshive.prac.numberconversiontool.model.ConvertResponse;
import rsshive.prac.numberconversiontool.service.BaseConvertService;

@Controller
public class ConverterViewController {

    private final BaseConvertService service;

    public ConverterViewController(BaseConvertService service) {
        this.service = service;
    }

    /** Cung cấp danh sách mode cho mọi request (dropdown) */
    @ModelAttribute("modes")
    public BaseType[] modes() {
        return BaseType.values();
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        // Chỉ tạo form-backing bean, KHÔNG gọi service ở GET
        if (!model.containsAttribute("convertRequest")) {
            ConvertRequest req = new ConvertRequest();
            req.setFromBase(BaseType.TEXT);
            req.setToBase(BaseType.DECIMAL);
            model.addAttribute("convertRequest", req);
        }
        // Không set "result" khi chưa convert để tránh null pointer trong view
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@Valid @ModelAttribute("convertRequest") ConvertRequest req,
                          BindingResult bindingResult,
                          Model model) {
        model.addAttribute("modes", BaseType.values());

        if (bindingResult.hasErrors()) {
            model.addAttribute("result", null);
            model.addAttribute("error", "Vui lòng kiểm tra dữ liệu nhập.");
            return "index";
        }

        try {
            String out = service.convert(req.getValue(), req.getFromBase(), req.getToBase());
            model.addAttribute("result",
                    new ConvertResponse(req.getValue(), req.getFromBase(), out, req.getToBase()));
            model.addAttribute("error", null);
        } catch (rsshive.prac.numberconversiontool.exception.ConversionException ex) {
            // 🟢 bắt ConversionException và hiển thị lỗi đẹp trên trang
            model.addAttribute("result", null);
            model.addAttribute("error", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("result", null);
            model.addAttribute("error", ex.getMessage());
        }
        return "index";
    }
}
