package com.facturas.Facturas_Electronicas.Productos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tbl_productos", schema = "facturas_electronicas", catalog = "")
public class ProductoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_producto")
    private int idProducto;

    @Basic
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @NotBlank(message = "La descripción es requerida")
    @Size(min = 3, max = 255, message = "La descripción debe tener entre 3 y 255 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @NotNull
    @Min(value = 0, message = "El precio unitario debe ser mayor a 0")
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Basic
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEntity that = (ProductoEntity) o;
        return idProducto == that.idProducto && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(precioUnitario, that.precioUnitario) && Objects.equals(idProveedor, that.idProveedor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, nombre, descripcion, precioUnitario, idProveedor);
    }

    @Override
    public String toString() {
        return "ProductoEntity{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", idProveedor=" + idProveedor +
                '}';
    }

    public void copy(ProductoEntity producto) {
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precioUnitario = producto.getPrecioUnitario();
    }
}