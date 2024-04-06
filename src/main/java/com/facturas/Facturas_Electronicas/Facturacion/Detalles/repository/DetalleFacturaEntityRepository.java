package com.facturas.Facturas_Electronicas.Facturacion.Detalles.repository;

import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleFacturaEntityRepository extends JpaRepository<DetalleFacturaEntity, DetalleFacturaEntityPK> {
    List<DetalleFacturaEntity> getDetallesByIdFactura(int idFactura);
}