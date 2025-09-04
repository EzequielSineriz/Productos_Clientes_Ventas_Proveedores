package com.ezequiel.productos_clientes_ventas.controller;

import com.ezequiel.productos_clientes_ventas.dto.ProveedorRequest;
import com.ezequiel.productos_clientes_ventas.dto.ProveedorResponse;
import com.ezequiel.productos_clientes_ventas.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {


    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(@Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crearProveedor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerProveedor(id));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> editar(@PathVariable Long id, @RequestBody ProveedorRequest request) {
        return ResponseEntity.ok(proveedorService.editarProveedor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/pageable")
    public ResponseEntity<Page<ProveedorResponse>> getProveedoresPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "razonSocial,asc") String[] sort
    ) {
        // parsear ordenamiento
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        String sortBy = sort[0];

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ProveedorResponse> response = proveedorService.getProveedores(pageable);

        return ResponseEntity.ok(response);
    }

}

