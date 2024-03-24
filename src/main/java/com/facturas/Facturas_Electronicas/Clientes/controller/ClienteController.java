package com.facturas.Facturas_Electronicas.Clientes.controller;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import com.facturas.Facturas_Electronicas.Productos.model.ProductoEntity;
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
@SessionAttributes({"userLogged", "currentPage", "currentClient", "currentClientList"})
public class ClienteController {
    @ModelAttribute("currentClient") public ProductoEntity currentProduct() { return new ProductoEntity(); }
    private final ClienteService clienteService;

    @Autowired
    private HttpSession httpSession;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clients")
    public String getClientesPage(Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        if (model.getAttribute("currentClientList") == null) {
            // obtener la lista de clientes del proveedor loggeado
            ArrayList<ClienteEntity> clientes = clienteService.getClientesByProveedor(userLogged);
            if (clientes != null) {
                model.addAttribute("currentClientList", clientes);  // agregar al model la lista de clientes
            }
        }

        ArrayList<ClienteEntity> clientes = ArrayList.class.cast(model.getAttribute("currentClientList"));
        model.addAttribute("currentClientList", clientes);  // agregar al model la lista de clientes

        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        model.addAttribute("currentPage", "clients");
        return "clients/clients";   // devuelve el view de clientes (templates/clientes/clientes.html)
    }

}
