package com.facturas.Facturas_Electronicas.Index.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/login"; // redirige a la página de inicio de sesión
    }
}
