package com.facturas.Facturas_Electronicas.Facturacion.DTO;

import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;

import java.util.Objects;

public class ProductOnCart {
    // Atributos
    private ProductoEntity product;
    private int quantity;

    // Constructores
    public ProductOnCart() {
    }

    public ProductOnCart(ProductoEntity product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductoEntity getProduct() {
        return product;
    }

    public void setProduct(ProductoEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOnCart that = (ProductOnCart) o;
        return quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "ProductOnCart{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
