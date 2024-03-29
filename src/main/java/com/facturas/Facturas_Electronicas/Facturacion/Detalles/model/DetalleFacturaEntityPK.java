package com.facturas.Facturas_Electronicas.Facturacion.Detalles.model;

import java.io.Serializable;
import java.util.Objects;

// Como la llave primaria de la tabla tbl_detalle_facturas está compuesta por dos columnas, se debe crear una clase que represente la llave primaria de la tabla

public class DetalleFacturaEntityPK implements Serializable {
    private int idFactura;
    private int idProducto;

    // no args constructor
    DetalleFacturaEntityPK() {
    }

    // all args constructor
    public DetalleFacturaEntityPK(int idFactura, int idProducto) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleFacturaEntityPK that)) return false;
        return getIdFactura() == that.getIdFactura() && getIdProducto() == that.getIdProducto();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdFactura(), getIdProducto());
    }
}
