package com.facturas.Facturas_Electronicas.Facturacion.Facturas.service;

import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Detalles.repository.DetalleFacturaEntityRepository;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.repository.FacturaEntityRepository;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaEntityService {
    private final FacturaEntityRepository facturaEntityRepository;
    private final DetalleFacturaEntityRepository detalleFacturaEntityRepository;

    public FacturaEntityService(FacturaEntityRepository facturaEntityRepository, DetalleFacturaEntityRepository detalleFacturaEntityRepository) {
        this.facturaEntityRepository = facturaEntityRepository;
        this.detalleFacturaEntityRepository = detalleFacturaEntityRepository;
    }

    public ArrayList<FacturaConDetallesDTO> getFacturasByProveedor(ProveedorEntity userLogged) {
        ArrayList<FacturaConDetallesDTO> facturas = new ArrayList<>();
        List<FacturaEntity> facturasDelProveedor = facturaEntityRepository.getFacturasByIdProveedor(userLogged.getIdProveedor());

        for (FacturaEntity factura : facturasDelProveedor) {
            List<DetalleFacturaEntity> detalles = detalleFacturaEntityRepository.getDetallesByIdFactura(factura.getIdFactura());
            FacturaConDetallesDTO facturaConDetallesDTO = new FacturaConDetallesDTO();
            facturaConDetallesDTO.setFactura(factura);
            facturaConDetallesDTO.setDetalles(detalles);
            facturas.add(facturaConDetallesDTO);
        }

        return facturas;
    }

    public void deleteFactura(Integer id) {
        facturaEntityRepository.deleteById(id);
    }

    public ArrayList<FacturaConDetallesDTO> getFacturasByProveedorAndClientID(ProveedorEntity userLogged, Integer searchClientID) {

        System.out.println("searchClientID: " + searchClientID);
        if (searchClientID == null || searchClientID == -1) {
            return getFacturasByProveedor(userLogged);
        }


        ArrayList<FacturaConDetallesDTO> facturas = new ArrayList<>();
        List<FacturaEntity> facturasDelProveedor = facturaEntityRepository.getFacturasByIdProveedorAndIdCliente(userLogged.getIdProveedor(), searchClientID);

        for (FacturaEntity factura : facturasDelProveedor) {
            List<DetalleFacturaEntity> detalles = detalleFacturaEntityRepository.getDetallesByIdFactura(factura.getIdFactura());
            FacturaConDetallesDTO facturaConDetallesDTO = new FacturaConDetallesDTO();
            facturaConDetallesDTO.setFactura(factura);
            facturaConDetallesDTO.setDetalles(detalles);
            facturas.add(facturaConDetallesDTO);
        }

        return facturas;
    }
}
