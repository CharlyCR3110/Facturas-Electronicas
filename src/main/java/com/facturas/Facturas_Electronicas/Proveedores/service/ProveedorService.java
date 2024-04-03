package com.facturas.Facturas_Electronicas.Proveedores.service;

import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.repository.ProveedorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public ProveedorEntity registerProveedor(ProveedorEntity proveedor) {
        try {
            proveedor.setEstado("en espera");
            return proveedorRepository.save(proveedor);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("correo")) {
                throw new IllegalArgumentException("Parece que ya existe una cuenta con este correo.");
            } else {
                throw new IllegalArgumentException("Hubo un error al registrar el proveedor. Intenta de nuevo.");
            }
        }
    }

    // para hacer login
    public ProveedorEntity loginProveedor(String correo, String contrasena) {
        Optional<ProveedorEntity> proveedorOptional = proveedorRepository.findByCorreo(correo);

        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            if (proveedor.getContrasena().equals(contrasena)) {
                if (!proveedor.getEstado().equals("activo")) {
                    throw new IllegalArgumentException("Tu cuenta no se encuentra activa. Contacta con el administrador.");
                }
                return proveedor;
            } else {
                throw new IllegalArgumentException("La contraseña proporcionada no es válida.");
            }
        } else {
            throw new IllegalArgumentException("No se encontró un proveedor con el correo electrónico proporcionado.");
        }
    }

    // changeEmail(ProveedorEntity userLogged, String correo)
    public ProveedorEntity changeEmail(ProveedorEntity proveedor, String correo) {
        // se intenta actualizar el correo del proveedor en la base de datos para el proveedor loggeado (se hace primero para evitar errores)
        String correoAnterior = proveedor.getCorreo();
        if (correo.equals(correoAnterior)) {
            throw new IllegalArgumentException("El correo proporcionado es igual al actual.");
        }
        proveedor.setCorreo(correo);
        try {
            proveedorRepository.save(proveedor);
        } catch (DataIntegrityViolationException e) {
            proveedor.setCorreo(correoAnterior);
            if (e.getMessage().contains("Duplicate")) {
                throw new IllegalArgumentException("Parece que ya existe una cuenta con este correo.");
            } else {
                throw new IllegalArgumentException("Hubo un error al actualizar el correo. Intenta de nuevo.");
            }
        }
        return proveedor;
    }

    public ProveedorEntity changePassword(ProveedorEntity userLogged, String newPassword) {
        userLogged.setContrasena(newPassword);
        return proveedorRepository.save(userLogged);
    }

    public ProveedorEntity changeProviderInfo(ProveedorEntity userLogged, ProveedorEntity proveedorEntity) {
        if (proveedorEntity.getNombre().equals(userLogged.getNombre()) &&
                proveedorEntity.getTelefono().equals(userLogged.getTelefono()) &&
                proveedorEntity.getDireccion().equals(userLogged.getDireccion())) {
            throw new IllegalArgumentException("No se realizaron cambios.");
        }

        userLogged.setNombre(proveedorEntity.getNombre());
        userLogged.setTelefono(proveedorEntity.getTelefono());
        userLogged.setDireccion(proveedorEntity.getDireccion());
        return proveedorRepository.save(userLogged);
    }

    public List<ProveedorEntity> getAllProviders() {
        return proveedorRepository.findAll();
    }

    public void changeProviderState(int idProveedor) {
        Optional<ProveedorEntity> proveedorOptional = proveedorRepository.findById(idProveedor);
        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            if (proveedor.getEstado().equals("activo")) {
                proveedor.setEstado("inactivo");
            } else {
                proveedor.setEstado("activo");
            }
            proveedorRepository.save(proveedor);
        } else {
            throw new IllegalArgumentException("No se encontró un proveedor con el ID proporcionado.");
        }
    }
}

