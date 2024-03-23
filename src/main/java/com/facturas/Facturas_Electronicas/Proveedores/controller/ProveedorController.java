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
import org.springframework.web.bind.annotation.RequestParam;

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

        // agregar el mensaje de confirmación al modelo (viene desde httpSession)
        model.addAttribute("confirmation", httpSession.getAttribute("confirmation"));
        // eliminar el mensaje de confirmación de la sesión
        httpSession.removeAttribute("confirmation");
        // agregar el error al modelo (viene desde httpSession)
        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        System.out.println("Error message: " + httpSession.getAttribute("errorMessage"));
        // eliminar el error de la sesión
        httpSession.removeAttribute("errorMessage");


        // agregar el proveedor loggeado al modelo
        model.addAttribute("userLogged", userLogged);
        return "proveedor_account_info/account_info";   // devuelve el view de account_info (templates/proveedor_auth/account_info.html)
    }

    // cerrar sesion
    @GetMapping("/logout")
    public String logout() {
        httpSession.removeAttribute("userLogged");
        return "redirect:/login";   // redirige al view de login
    }

    @PostMapping("/account_info/change-email")
    public String changeEmail(@ModelAttribute("userLogged") ProveedorEntity proveedorEntity, Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        System.out.println("CHANGE_EMAIL:" + proveedorEntity.getCorreo());

        try {
            ProveedorEntity provedor = proveedorService.changeEmail(userLogged, proveedorEntity.getCorreo());
            httpSession.setAttribute("userLogged", provedor);
            // enviar un mensaje de confirmación
            httpSession.setAttribute("confirmation", "¡Correo actualizado correctamente!");
        } catch (IllegalArgumentException e) {
            httpSession.setAttribute("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }

        return "redirect:/account_info";
    }

    @PostMapping("/account_info/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @ModelAttribute("userLogged") ProveedorEntity proveedorEntity,
                                 Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        try {
            if (!newPassword.equals(confirmPassword)) {
                httpSession.setAttribute("errorMessage", "Las contraseñas no coinciden.");
                return "redirect:/account_info";
            }
            if (!userLogged.getContrasena().equals(currentPassword)) {
                httpSession.setAttribute("errorMessage", "La contraseña actual no es válida.");
                return "redirect:/account_info";
            }
            ProveedorEntity provedor = proveedorService.changePassword(userLogged, newPassword);
            httpSession.setAttribute("userLogged", provedor);
            // enviar un mensaje de confirmación
            httpSession.setAttribute("confirmation", "¡Contraseña actualizada correctamente!");
            return "redirect:/account_info";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PostMapping("/account_info/change-provider-info")
    public String changeProviderInfo(@ModelAttribute("userLogged") ProveedorEntity proveedorEntity, Model model) {
        ProveedorEntity userLogged = (ProveedorEntity) httpSession.getAttribute("userLogged");
        try {
            ProveedorEntity provedor = proveedorService.changeProviderInfo(userLogged, proveedorEntity);
            httpSession.setAttribute("userLogged", provedor);
            // enviar un mensaje de confirmación
            httpSession.setAttribute("confirmation", "¡Información actualizada correctamente!");
        } catch (IllegalArgumentException e) {
            httpSession.setAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/account_info";
    }
}