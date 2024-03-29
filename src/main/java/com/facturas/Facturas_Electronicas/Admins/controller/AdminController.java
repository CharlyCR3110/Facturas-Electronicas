package com.facturas.Facturas_Electronicas.Admins.controller;

import com.facturas.Facturas_Electronicas.Admins.model.AdminEntity;
import com.facturas.Facturas_Electronicas.Admins.service.AdminService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;

@Controller
@SessionAttributes({"currentProvider", "currentProviderList"})
public class AdminController {
    @ModelAttribute("currentProvider") public ProveedorEntity currentProvider() { return new ProveedorEntity(); }
    @ModelAttribute("currentProviderList") public ArrayList<ProveedorEntity> currentProviderList() { return new ArrayList<>(); }


    private final AdminService adminService;
    private final ProveedorService proveedorService;

    @Autowired
    HttpSession httpSession;

    public AdminController(AdminService adminService, ProveedorService proveedorService) {
        this.adminService = adminService;
        this.proveedorService = proveedorService;
    }

    @GetMapping("/admins/login")
    public String getAdminLoginPage(Model model) {
        model.addAttribute("adminLoginRequest", new AdminEntity());
        return "admins/adminLogin";
    }
}
