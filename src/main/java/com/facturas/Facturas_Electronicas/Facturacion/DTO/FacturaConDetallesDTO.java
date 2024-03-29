package com.facturas.Facturas_Electronicas.Facturacion.DTO;

import com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;

import java.util.List;
import java.util.Objects;

public class FacturaConDetallesDTO {
    private FacturaEntity factura;
    private List<DetalleFacturaEntity> detalles;

    // no args constructor
    public FacturaConDetallesDTO() {
    }

    // all args constructor
    public FacturaConDetallesDTO(FacturaEntity factura, List<DetalleFacturaEntity> detalles) {
        this.factura = factura;
        this.detalles = detalles;
    }

    // getters and setters

    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

    public List<DetalleFacturaEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaEntity> detalles) {
        this.detalles = detalles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturaConDetallesDTO that = (FacturaConDetallesDTO) o;
        return Objects.equals(factura, that.factura) && Objects.equals(detalles, that.detalles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factura, detalles);
    }

    @Override
    public String toString() {
        return "FacturaConDetallesDTO{" +
                "factura=" + factura +
                ", detalles=" + detalles +
                '}';
    }
}
