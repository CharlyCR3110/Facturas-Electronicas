package com.facturas.Facturas_Electronicas.Proveedores.repository;

import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Integer> {
    Optional<ProveedorEntity> findProveedorEntityByCorreoAndContrasena(String correo, String contrasena);
}
