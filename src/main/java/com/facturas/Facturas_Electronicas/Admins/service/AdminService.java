package com.facturas.Facturas_Electronicas.Admins.service;

import com.facturas.Facturas_Electronicas.Admins.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}
