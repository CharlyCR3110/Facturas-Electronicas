package com.facturas.Facturas_Electronicas.Facturacion.Facturas.service;

import com.facturas.Facturas_Electronicas.Facturacion.Facturas.repository.FacturaEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaEntityService {
    private final FacturaEntityRepository facturaEntityRepository;

    public FacturaEntityService(FacturaEntityRepository facturaEntityRepository) {
        this.facturaEntityRepository = facturaEntityRepository;
    }
}
