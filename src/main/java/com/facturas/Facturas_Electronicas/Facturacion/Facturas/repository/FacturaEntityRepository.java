package com.facturas.Facturas_Electronicas.Facturacion.Facturas.repository;


import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface FacturaEntityRepository extends JpaRepository<FacturaEntity, Integer> {
    ArrayList<FacturaEntity> getFacturasByIdProveedor(int idProveedor);
}
