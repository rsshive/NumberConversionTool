
package rsshive.prac.numberconversiontool.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import rsshive.prac.numberconversiontool.model.ConvertRequest;
import rsshive.prac.numberconversiontool.model.ConvertResponse;
import rsshive.prac.numberconversiontool.service.BaseConvertService;

@RestController
@RequestMapping("/api")
public class ConverterApiController {

    private final BaseConvertService service;

    public ConverterApiController(BaseConvertService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@Valid @RequestBody ConvertRequest req) {
        try {
            String result = service.convert(req.getValue(), req.getFromBase(), req.getToBase());
            return ResponseEntity.ok(
                    new ConvertResponse(req.getValue(), req.getFromBase(), result, req.getToBase())
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
