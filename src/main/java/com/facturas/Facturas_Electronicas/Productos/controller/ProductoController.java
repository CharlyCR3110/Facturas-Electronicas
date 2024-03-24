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
    // definir los atributos que se van a compartir entre los metodos del controlador

    private final ProductoService productoService;

    @Autowired
    private HttpSession httpSession;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Método para manejar las peticiones GET
    @GetMapping("/products")
    public String getProductsPage(Model model) {
        // Obtener el usuario loggeado (se obtiene de la sesión)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // Verificar si la lista de productos ya está en la sesión
        ArrayList<ProductoEntity> currentProductList = (ArrayList<ProductoEntity>) httpSession.getAttribute("currentProductList");
        if (currentProductList == null) {
            // Si no está en la sesión, obtener la lista de productos del proveedor y guardarla en la sesión
            currentProductList = productoService.getProductosByProveedor(userLogged);
            httpSession.setAttribute("currentProductList", currentProductList);
        }

        // Agregar la lista de productos al modelo
        model.addAttribute("currentProductList", currentProductList);

        // Agregar el error al modelo (viene desde la sesión)
        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // Eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        // Agregar al modelo un identificador de la página actual (para el navbar)
        model.addAttribute("currentPage", "products");

        return "products/products";   // Devuelve el view de products (templates/productos/products.html)
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("currentProduct") ProductoEntity producto) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // agregar el id del proveedor al producto
        producto.setIdProveedor(userLogged.getIdProveedor());
        // guardar el producto en la base de datos
        try {
            System.out.println("GUARDANDO PRODUCTO");
            System.out.println(producto);
            productoService.saveProduct(producto);
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products";  // redirigir a la pagina de productos
    }

    // Metodo para eliminar un producto
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer productoId) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // eliminar el producto de la base de datos
        try {
            productoService.deleteProductById(productoId);
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products";  // redirigir a la pagina de productos
    }

    // metodo para editar un producto
    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("currentProduct") ProductoEntity producto) {
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
