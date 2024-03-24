package com.facturas.Facturas_Electronicas.Facturacion.Facturas.controller;

import com.facturas.Facturas_Electronicas.Facturacion.DTO.FacturaConDetallesDTO;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.model.FacturaEntity;
import com.facturas.Facturas_Electronicas.Facturacion.Facturas.service.FacturaEntityService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;

@Controller
@SessionAttributes({"userLogged", "currentPage", "currentInvoice", "currentInvoicesList"})
public class FacturaEntityController {
    @ModelAttribute("currentFactura") public FacturaEntity currentFactura() { return new FacturaEntity(); }

    @Autowired
    HttpSession httpSession;

    private final FacturaEntityService facturaEntityService;

    public FacturaEntityController(FacturaEntityService facturaEntityService) {
        this.facturaEntityService = facturaEntityService;
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

        model.addAttribute("currentPage", "invoices");
        // devuelve el view de invoices (templates/facturas/facturas.html)
        return "invoices/invoices";
    }
}
