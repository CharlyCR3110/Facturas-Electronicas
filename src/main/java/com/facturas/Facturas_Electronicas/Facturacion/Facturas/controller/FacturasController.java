package com.facturas.Facturas_Electronicas.Facturacion.Facturas.controller;

import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.service.FacturaEntityService;
import com.facturas.Facturas_Electronicas.Productos.service.ProductoService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@SessionAttributes({"userLogged", "currentPage", "currentInvoice", "currentInvoicesList", "currentClientsList", "currentProductsList"})
public class FacturasController {
    @ModelAttribute("currentFactura") public FacturaEntity currentFactura() { return new FacturaEntity(); }

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
}
