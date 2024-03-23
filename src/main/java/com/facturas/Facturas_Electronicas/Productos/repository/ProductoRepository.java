package com.facturas.Facturas_Electronicas.Productos.repository;

import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository  extends JpaRepository<ProductoEntity, Integer> {

}
