package com.facturas.Facturas_Electronicas.Clientes.controller;

import com.facturas.Facturas_Electronicas.Clientes.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    private HttpSession httpSession;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
}
