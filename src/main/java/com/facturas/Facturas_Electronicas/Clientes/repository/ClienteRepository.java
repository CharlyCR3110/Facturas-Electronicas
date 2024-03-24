package com.facturas.Facturas_Electronicas.Clientes.repository;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    ArrayList<ClienteEntity> findAllByIdProveedor(int idProveedor);

    ArrayList<ClienteEntity> findAllByIdProveedorAndNombreContaining(int idProveedor, String searchName);
}
