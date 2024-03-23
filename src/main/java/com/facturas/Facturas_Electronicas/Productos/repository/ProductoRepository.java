package com.facturas.Facturas_Electronicas.Productos.repository;

import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ProductoRepository  extends JpaRepository<ProductoEntity, Integer> {
    // metoodo para obtener la lista de productos de un proveedor
    ArrayList<ProductoEntity> findAllByIdProveedor(int idProveedor);
}
