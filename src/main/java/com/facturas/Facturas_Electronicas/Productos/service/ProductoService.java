package com.facturas.Facturas_Electronicas.Productos.service;

import com.facturas.Facturas_Electronicas.Productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
}
