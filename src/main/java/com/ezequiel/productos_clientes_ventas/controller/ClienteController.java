package com.ezequiel.productos_clientes_ventas.controller;


import com.ezequiel.productos_clientes_ventas.dto.ClienteRequest;
import com.ezequiel.productos_clientes_ventas.dto.ClienteResponse;
import com.ezequiel.productos_clientes_ventas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<ClienteResponse> crearCliente(@RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(request));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ClienteResponse>> obtenerTodosClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodosClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerCliente(id));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ClienteResponse> eliminarCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.eliminarCliente(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ClienteResponse> editarCliente(
            @PathVariable Long id,
            @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.editarCliente(id, request));
    }

}
