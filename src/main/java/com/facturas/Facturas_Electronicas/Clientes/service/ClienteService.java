package com.facturas.Facturas_Electronicas.Clientes.service;

import com.facturas.Facturas_Electronicas.Clientes.model.ClienteEntity;
import com.facturas.Facturas_Electronicas.Clientes.repository.ClienteRepository;
import com.facturas.Facturas_Electronicas.Proveedores.model.ProveedorEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ArrayList<ClienteEntity> getClientesByProveedor(ProveedorEntity userLogged) {
        return clienteRepository.findAllByIdProveedor(userLogged.getIdProveedor());
    }

    public void saveClient(ClienteEntity newClient) {
        // validar que el proveedor no tenga otro cliente con la misma identificación
        ArrayList<ClienteEntity> clientes = clienteRepository.findAllByIdProveedor(newClient.getIdProveedor());
        for (ClienteEntity c : clientes) {
            if (c.getIdentificacion().equals(newClient.getIdentificacion())) {
                throw new RuntimeException("Ya existe un cliente con la misma identificación");
            }
        }
        // Ademas la base de datos no permite que se repita el correo (entonces no es necesario validar) (tira excepción)

        clienteRepository.save(newClient);
    }

    public void deleteClientById(Integer clienteId) {
        try {
            clienteRepository.deleteById(clienteId);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo eliminar el cliente");
        }
    }

    public void editCliente(ClienteEntity cliente) {
        // validar que el proveedor no tenga otro cliente con la misma identificación
        ArrayList<ClienteEntity> clientes = clienteRepository.findAllByIdProveedor(cliente.getIdProveedor());
        for (ClienteEntity c : clientes) {
            if (c.getIdentificacion().equals(cliente.getIdentificacion()) && c.getIdCliente() != cliente.getIdCliente()) {
                throw new RuntimeException("Ya existe un cliente con la misma identificación");
            }
        }
        // Ademas la base de datos no permite que se repita el correo (entonces no es necesario validar) (tira excepción)

        clienteRepository.save(cliente);
    }

    public ArrayList<ClienteEntity> searchClientsByName(ProveedorEntity userLogged, String searchName) {
        if (searchName == null || searchName.isEmpty()) {
            return clienteRepository.findAllByIdProveedor(userLogged.getIdProveedor());
        }

        return clienteRepository.findAllByIdProveedorAndNombreContaining(userLogged.getIdProveedor(), searchName);
    }
}
