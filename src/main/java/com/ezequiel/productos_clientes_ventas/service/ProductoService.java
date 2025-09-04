package com.ezequiel.productos_clientes_ventas.service;


import com.ezequiel.productos_clientes_ventas.advice.ProductoDuplicadoException;
import com.ezequiel.productos_clientes_ventas.advice.ProductoNoEncontradoException;
import com.ezequiel.productos_clientes_ventas.dto.ProductoRequest;
import com.ezequiel.productos_clientes_ventas.dto.ProductoResponse;
import com.ezequiel.productos_clientes_ventas.entities.Producto;
import com.ezequiel.productos_clientes_ventas.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;


    public ProductoResponse crearProducto(ProductoRequest request){

        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (request.getMarca() == null || request.getMarca().isBlank()) {
            throw new IllegalArgumentException("La marca del producto es obligatoria");
        }
        if (request.getCosto() <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor a 0");
        }
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }

        if (productoRepository.existsByNombreAndMarca(request.getNombre(), request.getMarca())) {
            throw new ProductoDuplicadoException("Ya existe un producto con ese nombre y marca");
        }

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setMarca(request.getMarca());
        producto.setPrecio(request.getCosto());
        producto.setStock(request.getStock());

        Producto productoGuardado = productoRepository.save(producto);


        return ProductoResponse.builder()
                .id(productoGuardado.getId())
                .nombre(productoGuardado.getNombre())
                .marca(productoGuardado.getMarca())
                .precio(productoGuardado.getPrecio())
                .stock(producto.getStock())
                .fechaCreacion(productoGuardado.getFechaCreacion())
                .fechaActualizacion(productoGuardado.getFechaActualizacion())
                .mensaje("Producto Creado Correctamente")
                .build();
    }

    public List<Producto> obtenerTodosProductos(){
        return productoRepository.findAll();
    }

    public ProductoResponse obtenerProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new ProductoNoEncontradoException("Producto no encontrado"));

        ProductoResponse response = ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .marca(producto.getMarca())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .fechaCreacion(producto.getFechaCreacion())
                .fechaActualizacion(producto.getFechaActualizacion())
                .mensaje("Producto Encotrado")
                .build();

        return response;
    }

    @Transactional
    public ProductoResponse eliminarProducto(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new ProductoNoEncontradoException("Producto no encontrado"));

        System.out.println("Producto Encontrado" + producto.getNombre());

        //Mapeo
        ProductoResponse response = ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .marca(producto.getMarca())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .fechaCreacion(producto.getFechaCreacion())
                .fechaActualizacion(producto.getFechaActualizacion())
                .mensaje("Producto eliminado correctamente el: "+ LocalDate.now())
                .build();

        productoRepository.delete(producto);

        System.out.println("Producto eliminado: " + producto.getNombre());

        return response;

    }

    public ProductoResponse editarProducto(Long id, ProductoRequest request){
        Producto existente = productoRepository.findById(id).orElseThrow(
                ()-> new ProductoNoEncontradoException("Producto No encontrado"));

        if (productoRepository.existsByNombreAndMarca(request.getNombre(), request.getMarca()) &&
                (!request.getNombre().equalsIgnoreCase(existente.getNombre()) ||
                        !request.getMarca().equalsIgnoreCase(existente.getMarca()))) {
            throw new ProductoDuplicadoException("Ya existe un producto con ese nombre y marca");
        }

        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (request.getCosto() <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor a 0");
        }
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }

        existente.setNombre(request.getNombre());
        existente.setMarca(request.getMarca());
        existente.setPrecio(request.getCosto());
        existente.setStock(request.getStock());

        Producto productoGuardado = productoRepository.save(existente);


        return new ProductoResponse(
                existente.getId(),
                existente.getNombre(),
                existente.getMarca(),
                existente.getPrecio(),
                existente.getStock(),
                existente.getFechaCreacion(),
                existente.getFechaActualizacion(),
                "Producto Actualizado Correctamente"
        );
    }
    // ---------- Paginación y orden ----------

    public Page<ProductoResponse> findAllOrderByNombreAsc(Pageable pageable) {
        return productoRepository.findAllOrderByNombreAsc(pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductoResponse> findAllOrderByNombreDesc(Pageable pageable) {
        return productoRepository.findAllOrderByNombreDesc(pageable)
                .map(this::mapToResponse);
    }


    public Page<ProductoResponse> findAllOrdered(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productoRepository.findAll(pageable).map(this::mapToResponse);
    }

    // ---------- Búsquedas y filtros ----------
    public List<ProductoResponse> buscarProductos(String nombre, String marca) {
        return productoRepository.buscarPorNombreYMarca(nombre, marca)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductoResponse> filtrarPorPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductoResponse> getProductosConBajoStock(int limite) {
        return productoRepository.findByStockLessThan(limite)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public long contarProductos() {
        return productoRepository.count();
    }

    public Double obtenerPrecioPromedio() {
        return productoRepository.calcularPrecioPromedio();
    }


    private ProductoResponse mapToResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion(),
                producto.getMensaje()
        );
    }

}
