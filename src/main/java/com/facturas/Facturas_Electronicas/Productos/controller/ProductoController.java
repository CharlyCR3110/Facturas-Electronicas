package com.facturas.Facturas_Electronicas.Productos.controller;

import com.facturas.Facturas_Electronicas.Productos.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    private HttpSession httpSession;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
}
