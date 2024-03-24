package com.facturas.Facturas_Electronicas.globals;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage() {
        return "errors/error_404";   // devuelve el view de error (templates/error.html)
    }
}
