package com.facturas.Facturas_Electronicas.Productos.controller;

import com.facturas.Facturas_Electronicas.Productos.service.ProductoService;
import org.springframework.stereotype.Controller;

@Controller
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
}
