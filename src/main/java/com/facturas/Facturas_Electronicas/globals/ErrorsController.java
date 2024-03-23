package com.facturas.Facturas_Electronicas.globals;

import com.facturas.Facturas_Electronicas.Proveedores.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {
    private final ProveedorService proveedorService;

    @Autowired
    private HttpSession httpSession;

    // Constructor para "inyectar" el servicio (se usa en lugar de @Autowired)
    public ErrorsController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "errors/error_404";   // devuelve el view de error (templates/error.html)
    }
}
