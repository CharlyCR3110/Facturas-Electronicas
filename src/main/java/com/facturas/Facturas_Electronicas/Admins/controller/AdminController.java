package com.facturas.Facturas_Electronicas.Admins.controller;

import com.facturas.Facturas_Electronicas.Admins.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"currentProvider", "currentProviderList"})
public class AdminController {
    private final AdminService adminService;

    @Autowired
    HttpSession httpSession;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
