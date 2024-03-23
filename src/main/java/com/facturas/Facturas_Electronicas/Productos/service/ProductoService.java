package com.facturas.Facturas_Electronicas.Productos.service;

import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;
import com.facturas.Facturas_Electronicas.Productos.repository.ProductoRepository;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ArrayList<ProductoEntity> getProductosByProveedor(ProveedorEntity userLogged) {
        return productoRepository.findAllByIdProveedor(userLogged.getIdProveedor());
    }
}
