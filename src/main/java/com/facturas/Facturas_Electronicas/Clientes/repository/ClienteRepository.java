package com.facturas.Facturas_Electronicas.Clientes.repository;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
}
