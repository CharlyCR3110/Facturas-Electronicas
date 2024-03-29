package com.facturas.Facturas_Electronicas.Facturacion.Facturas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tbl_facturas", schema = "facturas_electronicas", catalog = "")
public class FacturaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_factura")
    private int idFactura;
    @Basic
    @Column(name = "fecha_emision")
    private Timestamp fechaEmision;
    @Basic
    @Column(name = "id_proveedor")
    private int idProveedor;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Basic
    @Column(name = "impuesto")
    private BigDecimal impuesto;
    @Basic
    @Column(name = "total")
    private BigDecimal total;

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturaEntity that = (FacturaEntity) o;
        return idFactura == that.idFactura && idProveedor == that.idProveedor && idCliente == that.idCliente && Objects.equals(fechaEmision, that.fechaEmision) && Objects.equals(subtotal, that.subtotal) && Objects.equals(impuesto, that.impuesto) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, fechaEmision, idProveedor, idCliente, subtotal, impuesto, total);
    }

    @Override
    public String toString() {
        return "FacturaEntity{" +
                "idFactura=" + idFactura +
                ", fechaEmision=" + fechaEmision +
                ", idProveedor=" + idProveedor +
                ", idCliente=" + idCliente +
                ", subtotal=" + subtotal +
                ", impuesto=" + impuesto +
                ", total=" + total +
                '}';
    }
}
