package com.facturas.Facturas_Electronicas.Proveedores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "tbl_proveedores", schema = "facturas_electronicas", catalog = "")
public class ProveedorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_proveedor")
    private int idProveedor;

    @Basic
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @NotBlank(message = "La dirección es requerida")
    @Size(min = 3, max = 255, message = "La dirección debe tener entre 3 y 255 caracteres")
    @Column(name = "direccion")
    private String direccion;

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
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 3, max = 255, message = "La contraseña debe tener entre 3 y 255 caracteres")
    @Column(name = "contrasena")
    private String contrasena;

    @Basic
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "^(en espera|activo|inactivo)$", message = "El estado debe ser 'en espera', 'activo' o 'inactivo'")
    @Column(name = "estado")
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
