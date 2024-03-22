package com.facturas.Facturas_Electronicas.Proveedores.controller;

import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import com.facturas.Facturas_Electronicas.Proveedores.service.ProveedorService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProveedorController {
    private final ProveedorService proveedorService;

    @Autowired
    private HttpSession httpSession;

    // Constructor para "inyectar" el servicio (se usa en lugar de @Autowired)
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    /* Metodos para manejar las peticiones GET y POST */
    @GetMapping("/register")
    public String getRegisterProveedorPage(Model model) {
        model.addAttribute("registerRequest", new ProveedorEntity());
        return "proveedor_auth/register";   // devuelve el view de register (templates/proveedor_auth/register.html)
    }

    @GetMapping("/login")
    public String getLoginProveedorPage(Model model) {
        model.addAttribute("loginRequest", new ProveedorEntity());
        return "proveedor_auth/login";  // devuelve el view de login (templates/proveedor_auth/login.html)
    }

    // registrar a un proveeedor
    @PostMapping("/register")
    public String registerProveedor(@ModelAttribute("registerRequest") ProveedorEntity proveedorEntity, Model model) {
        try {
            proveedorService.registerProveedor(proveedorEntity);

            // Agrega un mensaje de confirmación al modelo
            model.addAttribute("confirmation", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        } catch (IllegalArgumentException e) {
            // si falla el registro, se agregará un mensaje de error al modelo y luego devuelve al view de register con el mensaje (muestra el popup de error)
            model.addAttribute("error", e.getMessage());
            return "proveedor_auth/register";
        }

        // Retorna al mismo formulario de registro con un mensaje de confirmación o error
        return "proveedor_auth/register";
    }


    // loggear a un proveedor (inicio de sesion)
    @PostMapping("/login")
    public String loginProveedor(@ModelAttribute("loginRequest") ProveedorEntity proveedorEntity, Model model, HttpSession httpSession) {
        try {
            // se intenta loggear al proveedor
            ProveedorEntity logged = proveedorService.loginProveedor(proveedorEntity.getCorreo(), proveedorEntity.getContrasena());
            httpSession.setAttribute("userLogged", logged);

            return "redirect:/account_info";   // si el login es exitoso, redirige al view de account_info
        } catch (IllegalArgumentException e) {
            // si falla el login, se agregara un mensaje de error al modelo y luego devuelve al view de login con el mensaje (muestra el popup de error)
            model.addAttribute("error", e.getMessage());
            return "proveedor_auth/login";
        }
    }

    // relacionado con el view de account_info (lugar donde se muestra la informacion del proveedor y se puede editar)
    @GetMapping("/account_info")
    public String getAccountInfoPage(Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");

        // si no hay un proveedor loggeado, redirige al view de login
        if (userLogged == null) {
            return "redirect:/login";
        }

        System.out.println(userLogged.getCorreo() + userLogged.getContrasena() + userLogged.getNombre());

        model.addAttribute("userLogged", userLogged);
        return "proveedor_account_info/account_info";   // devuelve el view de account_info (templates/proveedor_auth/account_info.html)
    }
}