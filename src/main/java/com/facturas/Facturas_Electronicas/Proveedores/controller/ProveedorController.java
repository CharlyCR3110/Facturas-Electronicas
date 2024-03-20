package com.facturas.Facturas_Electronicas.Proveedores.controller;

import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProveedorController {
    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/register")
    public String getRegisterProveedorPage(Model model) {
        model.addAttribute("registerRequest", new ProveedorEntity());
        return "proveedor_auth/register";
    }

    @GetMapping("/login")
    public String getLoginProveedorPage(Model model) {
        model.addAttribute("loginRequest", new ProveedorEntity());
        return "proveedor_auth/login";
    }
}
