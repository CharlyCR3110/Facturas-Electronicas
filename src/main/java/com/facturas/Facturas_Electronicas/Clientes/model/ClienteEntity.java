package com.facturas.Facturas_Electronicas.Clientes.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_clientes", schema = "facturas_electronicas", catalog = "")
public class ClienteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "identificacion")
    private String identificacion;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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
        ClienteEntity that = (ClienteEntity) o;
        return idCliente == that.idCliente && Objects.equals(nombre, that.nombre) && Objects.equals(identificacion, that.identificacion) && Objects.equals(telefono, that.telefono) && Objects.equals(correo, that.correo) && Objects.equals(idProveedor, that.idProveedor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombre, identificacion, telefono, correo, idProveedor);
    }

    @Override
    public String toString () {
        return "ClienteEntity{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", idProveedor=" + idProveedor +
                '}';
    }

    public void copy(ClienteEntity cliente) {
        this.nombre = cliente.nombre;
        this.identificacion = cliente.identificacion;
        this.telefono = cliente.telefono;
        this.correo = cliente.correo;
    }
}
