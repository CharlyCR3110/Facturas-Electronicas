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
    @GetMapping("/register")
    public String getRegisterProveedorPage() {
        return "proveedor_auth/register";
    }

    @GetMapping("/login")
    public String getLoginProveedorPage() {
        return "proveedor_auth/login";
    }
}
