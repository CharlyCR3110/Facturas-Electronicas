package com.facturas.Facturas_Electronicas.Clientes.controller;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @SuppressWarnings("unchecked")
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
    public String addClient(@Valid @ModelAttribute("currentClient") ClienteEntity cliente, BindingResult bindingResult, Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, guardar los errores en la sesión
            httpSession.setAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/clients";  // redirigir a la pagina de clientes
        }

        ClienteEntity newClient = new ClienteEntity(); // Crear nueva instancia de ClienteEntity para evitar errores
        newClient.copy(cliente); // Copiar los atributos del cliente recibido al nuevo cliente

        // agregar el id del proveedor al cliente
        newClient.setIdProveedor(userLogged.getIdProveedor());
        // guardar el cliente en la base de datos
        try {
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

    // Metodo para eliminar un cliente
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


    // metodo para editar un cliente
    @PostMapping("/clients/edit")
    public String editClient(@Valid @ModelAttribute("currentClient") ClienteEntity cliente, BindingResult bindingResult, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, guardar los errores en la sesión
            httpSession.setAttribute("errorMessage", bindingResult.getAllErrors().getFirst().getDefaultMessage());
            return "redirect:/clients";  // redirigir a la pagina de clientes
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

        return "redirect:/clients";  // redirigir a la pagina de clientes
    }

    // metodo para buscar clientes
    @PostMapping("/clients/search")
    public String searchClients(@RequestParam("searchName") String searchName, Model model) {
        // obtener el usuario loggeado (se obtiene de la sesion)
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        if (userLogged == null) {
            return "redirect:/login";
        }
        // buscar clientes por el término de búsqueda
        ArrayList<ClienteEntity> clientes = clienteService.searchClientsByName(userLogged, searchName);

        if (clientes != null) {
            model.addAttribute("currentClientList", clientes);  // agregar al model la lista de clientes
        }

        // agregar el error al modelo (viene desde httpSession)
        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");

        return "redirect:/clients";  // redirigir a la pagina de clientes
    }

}
