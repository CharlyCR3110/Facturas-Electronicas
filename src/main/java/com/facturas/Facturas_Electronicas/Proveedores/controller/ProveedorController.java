package com.facturas.Facturas_Electronicas.Proveedores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProveedorController {
    @GetMapping("/register")
    public String getRegisterProveedorPage() {
        return "register";
    }

    @GetMapping("/login")
    public String getLoginProveedorPage() {
        return "login";
    }
}