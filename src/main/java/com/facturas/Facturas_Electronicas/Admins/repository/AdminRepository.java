package com.facturas.Facturas_Electronicas.Admins.repository;

import com.facturas.Facturas_Electronicas.Admins.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByNombreAndContrasena(String nombre, String contrasena);
}
