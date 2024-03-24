package com.facturas.Facturas_Electronicas.Productos.controller;

import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;
import com.facturas.Facturas_Electronicas.Productos.service.ProductoService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
@SessionAttributes({"userLogged", "currentPage", "currentProduct", "currentProductList"})
public class ProductoController {
    @ModelAttribute("currentProduct") public ProductoEntity currentProduct() { return new ProductoEntity(); }

    private final ProductoService productoService;

    @Autowired
    private HttpSession httpSession;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/products")
    public String getProductsPage(Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        if (model.getAttribute("currentProductList") == null) {
            // obtener la lista de productos del proveedor loggeado
            ArrayList<ProductoEntity> productos = productoService.getProductosByProveedor(userLogged);
            if (productos != null) {
                model.addAttribute("currentProductList", productos);  // agregar al model la lista de productos
            }
        }

        ArrayList<ProductoEntity> productos = ArrayList.class.cast(model.getAttribute("currentProductList"));
        model.addAttribute("currentProductList", productos);  // agregar al model la lista de productos

        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        model.addAttribute("currentPage", "products");
        return "products/products";   // devuelve el view de products (templates/productos/products.html)
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("currentProduct") ProductoEntity producto, Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        ProductoEntity newProduct = new ProductoEntity(); // Crear nueva instancia de ProductoEntity para evitar errores
        newProduct.copy(producto); // Copiar los atributos del producto recibido al nuevo producto

        // agregar el id del proveedor al producto
        newProduct.setIdProveedor(userLogged.getIdProveedor());
        // guardar el producto en la base de datos
        try {
            System.out.println("GUARDANDO PRODUCTO");
            System.out.println(newProduct);
            productoService.saveProduct(newProduct);
            // Obtener la lista actualizada de productos
            ArrayList<ProductoEntity> updatedProductos = productoService.getProductosByProveedor(userLogged);
            model.addAttribute("currentProductList", updatedProductos);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products";  // redirigir a la pagina de productos
    }

    // Metodo para eliminar un producto
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer productoId, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // eliminar el producto de la base de datos
        try {
            productoService.deleteProductById(productoId);
            // Obtener la lista actualizada de productos
            ArrayList<ProductoEntity> updatedProductos = productoService.getProductosByProveedor(userLogged);
            model.addAttribute("currentProductList", updatedProductos);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products";  // redirigir a la pagina de productos
    }

    // metodo para editar un producto
    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("currentProduct") ProductoEntity producto, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        try {
            // agregar el id del proveedor al producto
            producto.setIdProveedor(userLogged.getIdProveedor());
            // editar el producto en la base de datos
            productoService.editProduct(producto);
            // Obtener la lista actualizada de productos
            ArrayList<ProductoEntity> updatedProductos = productoService.getProductosByProveedor(userLogged);
            model.addAttribute("currentProductList", updatedProductos);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }

        System.out.println("EDITANDO PRODUCTO");
        System.out.println(producto);

        return "redirect:/products";  // redirigir a la pagina de productos
    }

    @PostMapping("/products/search")
    public String searchProducts(@RequestParam("searchName") String searchName, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // buscar productos por el término de búsqueda
        ArrayList<ProductoEntity> productos = productoService.searchProductsByName(userLogged, searchName);

        if (productos != null) {
            model.addAttribute("currentProductList", productos);  // agregar al model la lista de productos
        }

        // agregar el error al modelo (viene desde httpSession)
        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        // agregar al model un identificador de la pagina actual (para el navbar)
        model.addAttribute("currentPage", "products");
        return "redirect:/products";  // redirigir a la pagina de productos
    }


}
