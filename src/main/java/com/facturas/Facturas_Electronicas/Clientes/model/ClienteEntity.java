package com.facturas.Facturas_Electronicas.Clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "tbl_clientes", schema = "facturas_electronicas", catalog = "")
public class ClienteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente")
    private int idCliente;

    @Basic
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @NotBlank(message = "La identificación es requerida")
    @Size(min = 3, max = 50, message = "La identificación debe tener entre 3 y 50 caracteres")
    @Column(name = "identificacion")
    private String identificacion;

    @Basic
    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 3, max = 20, message = "El teléfono debe tener entre 3 y 20 caracteres")
    @Column(name = "telefono")
    private String telefono;

    @Basic
    @NotBlank(message = "El correo es requerido")
    @Size(min = 3, max = 100, message = "El correo debe tener entre 3 y 100 caracteres")
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
