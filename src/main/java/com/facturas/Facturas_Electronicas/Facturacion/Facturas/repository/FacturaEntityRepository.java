package com.facturas.Facturas_Electronicas.Facturacion.Facturas.repository;


import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface FacturaEntityRepository extends JpaRepository<FacturaEntity, Integer> {
    ArrayList<FacturaEntity> getFacturasByIdProveedor(int idProveedor);
    List<FacturaEntity> getFacturasByIdProveedorAndIdCliente(Integer idProveedor, Integer searchClientID);
}
