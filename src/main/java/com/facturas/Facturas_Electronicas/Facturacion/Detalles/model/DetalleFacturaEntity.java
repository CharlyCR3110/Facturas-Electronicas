package com.facturas.Facturas_Electronicas.Facturacion.Detalles.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "tbl_detalle_facturas", schema = "facturas_electronicas", catalog = "")
@IdClass(com.facturas.Facturas_Electronicas.Facturacion.Detalles.model.DetalleFacturaEntityPK.class)
public class DetalleFacturaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_factura")
    private int idFactura;

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_producto")
    private int idProducto;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Basic
    @Column(name = "cantidad")
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Basic
    @Column(name = "total")
    private BigDecimal total;

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
        DetalleFacturaEntity that = (DetalleFacturaEntity) o;
        return idFactura == that.idFactura && idProducto == that.idProducto && cantidad == that.cantidad && Objects.equals(precioUnitario, that.precioUnitario) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, idProducto, cantidad, precioUnitario, total);
    }
}
