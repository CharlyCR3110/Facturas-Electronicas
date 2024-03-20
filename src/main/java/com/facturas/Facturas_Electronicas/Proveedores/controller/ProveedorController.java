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

    @PostMapping("/register")
    public String registerProveedor(@ModelAttribute("registerRequest") ProveedorEntity proveedorEntity, Model model) {
        try {
            ProveedorEntity registered = proveedorService.registerProveedor(proveedorEntity);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "proveedor_auth/register";
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginProveedor(@ModelAttribute("loginRequest") ProveedorEntity proveedorEntity, Model model) {
        ProveedorEntity logged = proveedorService.loginProveedor(proveedorEntity.getCorreo(), proveedorEntity.getContrasena());
        if (logged == null) {
            return "proveedor_auth/error_page";
        }

        model.addAttribute("userLogged", logged);
        return "proveedor_auth/success_page";
    }
}