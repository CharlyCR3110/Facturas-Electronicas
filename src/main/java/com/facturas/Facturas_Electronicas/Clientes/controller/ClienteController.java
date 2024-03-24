package com.facturas.Facturas_Electronicas.Clientes.controller;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@SessionAttributes({"userLogged", "currentPage", "currentClient", "currentClientList"})
public class ClienteController {
    @ModelAttribute("currentClient") public ClienteEntity currentClient() { return new ClienteEntity(); }
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

    @PostMapping("/clients/add")
    public String addClient(@ModelAttribute("currentClient") ClienteEntity cliente, Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        ClienteEntity newClient = new ClienteEntity(); // Crear nueva instancia de ClienteEntity para evitar errores
        newClient.copy(cliente); // Copiar los atributos del cliente recibido al nuevo cliente

        // agregar el id del proveedor al cliente
        newClient.setIdProveedor(userLogged.getIdProveedor());
        // guardar el cliente en la base de datos
        try {
            System.out.println("GUARDANDO CLIENTE...");
            System.out.println(newClient);
            clienteService.saveClient(newClient);
            // Obtener la lista actualizada de clientes
            ArrayList<ClienteEntity> updatedClients = clienteService.getClientesByProveedor(userLogged);
            model.addAttribute("currentClientList", updatedClients);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                // correo duplicado
                httpSession.setAttribute("errorMessage", "Ya existe un cliente con el mismo correo");
            } else {
                // Cuando el proveedor ya tiene un cliente con la misma identificación
                httpSession.setAttribute("errorMessage", e.getMessage());
            }
        }
        return "redirect:/clients";  // redirigir a la pagina de clientes
    }

    // Metodo para eliminar un producto
    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") Integer clienteId, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // eliminar el cliente de la base de datos
        try {
            clienteService.deleteClientById(clienteId);
            // Obtener la lista actualizada de clientes
            ArrayList<ClienteEntity> updatedClients = clienteService.getClientesByProveedor(userLogged);
            model.addAttribute("currentClientList", updatedClients);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            // si hay un error, guardar el mensaje en la sesion
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/clients";  // redirigir a la pagina de clientes
    }


    // metodo para editar un producto
    @PostMapping("/clients/edit")
    public String editClient(@ModelAttribute("currentClient") ClienteEntity cliente, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        try {
            // agregar el id del proveedor al cliente
            cliente.setIdProveedor(userLogged.getIdProveedor());
            // editar el client en la base de datos
            clienteService.editCliente(cliente);
            // Obtener la lista actualizada de client
            ArrayList<ClienteEntity> updatedClients = clienteService.getClientesByProveedor(userLogged);
            model.addAttribute("currentClientList", updatedClients);  // Actualizar la lista en el modelo
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                // correo duplicado
                httpSession.setAttribute("errorMessage", "Ya existe un cliente con el mismo correo");
            } else {
                // Cuando el proveedor ya tiene un cliente con la misma identificación
                httpSession.setAttribute("errorMessage", e.getMessage());
            }
        }

        System.out.println("EDITANDO CLIENTE...");
        System.out.println(cliente);

        return "redirect:/clients";  // redirigir a la pagina de clientes
    }

}
