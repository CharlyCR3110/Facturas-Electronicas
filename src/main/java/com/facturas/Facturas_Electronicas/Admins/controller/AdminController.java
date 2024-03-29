package com.facturas.Facturas_Electronicas.Admins.controller;

import com.facturas.Facturas_Electronicas.Admins.model.AdminEntity;
import com.facturas.Facturas_Electronicas.Admins.service.AdminService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admins/login")
    public String loginAdmin(@ModelAttribute("adminLoginRequest") AdminEntity adminEntity, Model model) {
        try {
            AdminEntity admin = adminService.loginAdmin(adminEntity.getNombre(), adminEntity.getContrasena());
            httpSession.setAttribute("adminLogged", admin);
            return "redirect:/admins/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admins/adminLogin";
        }
    }

    @GetMapping("/admins/dashboard")
    public String getAdminDashboard(Model model) {
        AdminEntity admin = (AdminEntity) httpSession.getAttribute("adminLogged");
        if (admin == null) {
            return "redirect:/admins/login";
        }
        model.addAttribute("admin", admin);

        model.addAttribute("currentProviderList", proveedorService.getAllProviders());
        return "admins/adminDashboard";
    }

    @GetMapping("/provider/changeState/{id}")
    public String changeProviderState(@PathVariable("id") Integer providerId) {
        proveedorService.changeProviderState(providerId);
        return "redirect:/admins/dashboard";
    }
}
