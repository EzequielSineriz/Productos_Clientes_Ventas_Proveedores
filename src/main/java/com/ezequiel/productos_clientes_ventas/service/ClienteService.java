package com.ezequiel.productos_clientes_ventas.service;

import com.ezequiel.productos_clientes_ventas.dto.ClienteRequest;
import com.ezequiel.productos_clientes_ventas.dto.ClienteResponse;
import com.ezequiel.productos_clientes_ventas.entities.Cliente;
import com.ezequiel.productos_clientes_ventas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    // Crear cliente
    public ClienteResponse crearCliente(ClienteRequest request) {
        // Validaciones
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del Cliente es obligatorio");
        }

        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .metodoPago(request.getMetodoPago())
                .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return mapToResponse(clienteGuardado, "Cliente creado correctamente");
    }

    // Obtener todos
    public List<ClienteResponse> obtenerTodosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> mapToResponse(cliente, null))
                .toList();
    }

    // Obtener por ID
    public ClienteResponse obtenerCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return mapToResponse(cliente, null);
    }

    // Eliminar cliente
    public ClienteResponse eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        clienteRepository.delete(cliente);
        return mapToResponse(cliente, "Cliente eliminado correctamente");
    }

    // Editar cliente
    public ClienteResponse editarCliente(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDni(request.getDni());
        cliente.setMetodoPago(request.getMetodoPago());

        Cliente clienteEditado = clienteRepository.save(cliente);
        return mapToResponse(clienteEditado, "Cliente editado correctamente");
    }

    // ====================
    // 🔹 Método auxiliar
    // ====================
    private ClienteResponse mapToResponse(Cliente cliente, String mensaje) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .dni(cliente.getDni())
                .metodoPago(cliente.getMetodoPago())
                .mensaje(mensaje)
                .build();
    }
}
