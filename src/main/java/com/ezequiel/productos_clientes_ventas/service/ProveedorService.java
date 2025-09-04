package com.ezequiel.productos_clientes_ventas.service;

import com.ezequiel.productos_clientes_ventas.dto.ProveedorRequest;
import com.ezequiel.productos_clientes_ventas.dto.ProveedorResponse;
import com.ezequiel.productos_clientes_ventas.entities.Proveedor;
import com.ezequiel.productos_clientes_ventas.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;


    public ProveedorResponse crearProveedor(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor();
        proveedor.setRazonSocial(request.getRazonSocial());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setCuitRuc(request.getCuitRuc());
        proveedor.setEmail(request.getEmail());
        proveedor.setTipoDeProductos(request.getTipoDeProductos());
        proveedor.setMetodoPago(request.getMetodoPago());
        proveedor.setDiasDeEntrega(request.getDiasDeEntrega());
        proveedor.setPlazosDePago(request.getPlazosDePago());

        Proveedor guardado = proveedorRepository.save(proveedor);

        return mapToResponse(guardado);
    }


    public ProveedorResponse obtenerProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
        return mapToResponse(proveedor);
    }


    public List<ProveedorResponse> obtenerTodos() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public Page<ProveedorResponse> getProveedores(Pageable pageable) {
        return proveedorRepository.findAll(pageable)
                .map(this::mapToResponse);
    }


    public ProveedorResponse editarProveedor(Long id, ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));

        proveedor.setRazonSocial(request.getRazonSocial());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setCuitRuc(request.getCuitRuc());
        proveedor.setEmail(request.getEmail());
        proveedor.setTipoDeProductos(request.getTipoDeProductos());
        proveedor.setMetodoPago(request.getMetodoPago());
        proveedor.setDiasDeEntrega(request.getDiasDeEntrega());
        proveedor.setPlazosDePago(request.getPlazosDePago());

        Proveedor actualizado = proveedorRepository.save(proveedor);

        return mapToResponse(actualizado);
    }


    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con id: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    private ProveedorResponse mapToResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getRazonSocial(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getCuitRuc(),
                proveedor.getEmail(),
                proveedor.getTipoDeProductos(),
                proveedor.getMetodoPago(),
                proveedor.getDiasDeEntrega(),
                proveedor.getPlazosDePago()
        );
    }

}
