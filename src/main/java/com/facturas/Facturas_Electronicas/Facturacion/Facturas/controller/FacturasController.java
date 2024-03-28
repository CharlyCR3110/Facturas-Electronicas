package com.facturas.Facturas_Electronicas.Facturacion.Facturas.controller;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.DTO.ProductOnCart;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.service.FacturaEntityService;
import com.facturas.Facturas_Electronicas.Productos.service.ProductoService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
@SessionAttributes({"userLogged", "currentPage", "currentInvoice", "currentInvoicesList", "currentClientsList", "currentProductsList", "cart", "total", "currentClientSelected"})
public class FacturasController {
    @ModelAttribute("currentFactura") public FacturaEntity currentFactura() { return new FacturaEntity(); }
    @ModelAttribute("cart") public ArrayList<ProductOnCart> cart() { return new ArrayList<>(); }
    @ModelAttribute("total") public BigDecimal total() { return BigDecimal.ZERO; }
    @ModelAttribute("currentClientSelected") public ClienteEntity currentClientSelected() { return new ClienteEntity(); }

    @Autowired
    HttpSession httpSession;

    private final FacturaEntityService facturaEntityService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    public FacturasController(FacturaEntityService facturaEntityService, ClienteService clienteService, ProductoService productoService) {
        this.facturaEntityService = facturaEntityService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping("/invoices/history")
    public String getInvoices(Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        if (model.getAttribute("currentInvoicesList") == null) {
            // obtener la lista de facturas del proveedor loggeado
            ArrayList<FacturaConDetallesDTO> invoices = facturaEntityService.getFacturasByProveedor(userLogged);
            if (invoices != null) {
                model.addAttribute("currentInvoicesList", invoices);  // agregar al model la lista de facturas
            }
        }

        ArrayList<FacturaEntity> invoices = ArrayList.class.cast(model.getAttribute("currentInvoicesList"));
        model.addAttribute("currentInvoicesList", invoices);  // agregar al model la lista de facturas

        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        model.addAttribute("currentPage", "invoicesHistory");
        // devuelve el view de invoices (templates/facturas/facturas.html)
        return "invoices/invoices";
    }

    // para elimitar una factura
    @GetMapping("/invoices/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Integer id, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // eliminar la factura
        try {
            facturaEntityService.deleteFactura(id);
            // obtener la lista actualizada de facturas
            ArrayList<FacturaConDetallesDTO> invoices = facturaEntityService.getFacturasByProveedor(userLogged);
            if (invoices != null) {
                model.addAttribute("currentInvoicesList", invoices);  // agregar al model la lista de facturas
            }
        } catch (Exception e) {
            httpSession.setAttribute("errorMessage", "No se pudo eliminar la factura");
        }

        return "redirect:/invoices/history";
    }

    // para buscar una factura con base searchClientID
    @PostMapping("/invoices/search")
    public String searchInvoice(@RequestParam(name = "searchClientID", required = false) Integer searchClientID, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // buscar la factura

        ArrayList<FacturaConDetallesDTO> invoices = facturaEntityService.getFacturasByProveedorAndClientID(userLogged, searchClientID);
        if (invoices != null) {
            model.addAttribute("currentInvoicesList", invoices);  // agregar al model la lista de facturas
        }

        model.addAttribute("currentPage", "invoicesHistory");
        return "redirect:/invoices/history";
    }
    
    // relacionados con la creacion de una factura
    @GetMapping("/invoice_creator")
    public String getInvoiceCreator(Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // Para agregar una factura se necesita la lista de clientes del proveedor
        model.addAttribute("currentClientsList", clienteService.getClientesByProveedor(userLogged));
        // Para agregar una factura se necesita la lista de productos del proveedor
        model.addAttribute("currentProductsList", productoService.getProductosByProveedor(userLogged));

        model.addAttribute("currentPage", "invoiceCreator");
        return "invoices/invoice_creator";
    }

    @PostMapping("/invoice_creator/addToCart")
    public String addToCart(@RequestParam(name = "product") Integer productID, @RequestParam(name = "quantity") Integer quantity, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }


        // obtener el producto
        ProductOnCart productOnCart = new ProductOnCart();
        productOnCart.setProduct(productoService.getProductoByID(productID));
        productOnCart.setQuantity(quantity);

        // agregar el producto al carrito
        ArrayList<ProductOnCart> cart = (ArrayList<ProductOnCart>) model.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // verificar que en el carrito no haya un producto con el mismo id
        boolean found = false;
        for (ProductOnCart p : cart) {
            if (p.getProduct().getIdProducto() == productID) {
                p.setQuantity(p.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(productOnCart);
        }

        model.addAttribute("cart", cart);
        //total
        BigDecimal total = BigDecimal.ZERO;
        for (ProductOnCart p : cart) {
            BigDecimal price = p.getProduct().getPrecioUnitario();
            BigDecimal quantityP = BigDecimal.valueOf(p.getQuantity());
            total = total.add(price.multiply(quantityP));
        }

        model.addAttribute("total", total);

        System.out.println("cart: " + cart);

        return "redirect:/invoice_creator";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(@PathVariable("id") Integer productID, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // obtener el producto
        ProductOnCart productOnCart = new ProductOnCart();
        productOnCart.setProduct(productoService.getProductoByID(productID));

        // eliminar el producto del carrito
        ArrayList<ProductOnCart> cart = (ArrayList<ProductOnCart>) model.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        for (ProductOnCart p : cart) {
            if (p.getProduct().getIdProducto() == productID) {
                cart.remove(p);
                break;
            }
        }

        model.addAttribute("cart", cart);
        //total
        BigDecimal total = BigDecimal.ZERO;
        for (ProductOnCart p : cart) {
            BigDecimal price = p.getProduct().getPrecioUnitario();
            BigDecimal quantityP = BigDecimal.valueOf(p.getQuantity());
            total = total.add(price.multiply(quantityP));
        }

        model.addAttribute("total", total);

        return "redirect:/invoice_creator";
    }

    @PostMapping("/invoice_creator/selectClient")
    public String selectClient(@RequestParam(name = "client") Integer clientID, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // obtener el cliente
        ClienteEntity client = clienteService.getClientByID(clientID);
        model.addAttribute("currentClientSelected", client);

        return "redirect:/invoice_creator";
    }

    @GetMapping("/invoice_creator/createInvoice")
    public String createInvoice(Model model) {
        // OBTENER DATOS
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        // obtener el cliente
        ClienteEntity client = (ClienteEntity) model.getAttribute("currentClientSelected");
        if (client == null) {
            httpSession.setAttribute("errorMessage", "Debe seleccionar un cliente");
            return "redirect:/invoice_creator";
        }

        // obtener el carrito
        ArrayList<ProductOnCart> cart = (ArrayList<ProductOnCart>) model.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            httpSession.setAttribute("errorMessage", "Debe agregar productos al carrito");
            return "redirect:/invoice_creator";
        }

//        2024-03-19 10:00:00
        // Obteniendo la fecha actual
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp todaysDate = new java.sql.Timestamp(date.getTime());

        // crear la factura
        FacturaEntity factura = new FacturaEntity();
        factura.setFechaEmision(todaysDate);
        factura.setIdProveedor(userLogged.getIdProveedor());
        factura.setIdCliente(client.getIdCliente());
        factura.setImpuesto(BigDecimal.valueOf(13)); // 13% -> en el trigger -> SET total = subtotal + (subtotal * impuesto / 100)
        // se calculan al agregar los detalles, pero se ponen aqui para que no sean null
        factura.setSubtotal(BigDecimal.ZERO);
        factura.setTotal(BigDecimal.ZERO);

        // Guardar la factura y obtener la factura con el id
        factura = facturaEntityService.saveFactura(factura);

        // Guardar los detalles de la factura
        for (ProductOnCart p : cart) {
            facturaEntityService.saveDetalleFactura(factura.getIdFactura(), p.getProduct().getIdProducto(), p.getQuantity());
        }

        // LIMPIAR DATOS
        // limpiar el carrito
        model.addAttribute("cart", new ArrayList<>());
        model.addAttribute("total", BigDecimal.ZERO);

        // limpiar el cliente seleccionado
        model.addAttribute("currentClientSelected", new ClienteEntity());

        // redirigir a la lista de facturas
        return "redirect:/invoices/history";
    }
}
