package com.facturas.Facturas_Electronicas.Proveedores.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_proveedores", schema = "facturas_electronicas", catalog = "")
public class ProveedorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_proveedor")
    private int idProveedor;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorEntity that = (ProveedorEntity) o;
        return idProveedor == that.idProveedor && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(telefono, that.telefono) && Objects.equals(correo, that.correo) && Objects.equals(contrasena, that.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProveedor, nombre, direccion, telefono, correo, contrasena);
    }
}
