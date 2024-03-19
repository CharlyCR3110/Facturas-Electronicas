package com.facturas.Facturas_Electronicas.Proveedores.service;

import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public ProveedorEntity registerProveedor(ProveedorEntity proveedor) {
        try {
            return proveedorRepository.save(proveedor);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("correo")) {
                throw new IllegalArgumentException("Correo already exists");
            } else if (e.getMessage().contains("chk_contrasena_complexity")) {
                throw new IllegalArgumentException("Contrasena does not meet complexity requirements");
            } else {
                throw new IllegalArgumentException("Invalid data provided");
            }
        }
    }

    // para hacer login
    public ProveedorEntity loginProveedor(String correo, String contrasena) {
        return proveedorRepository.findByCorreoAndContrasena(correo, contrasena).orElse(null);
    }
}

