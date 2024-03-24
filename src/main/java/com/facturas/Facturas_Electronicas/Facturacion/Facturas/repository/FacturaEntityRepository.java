package com.facturas.Facturas_Electronicas.Facturacion.Facturas.repository;


import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface FacturaEntityRepository extends JpaRepository<FacturaEntity, Integer> {
}
