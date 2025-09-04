package com.ezequiel.productos_clientes_ventas.controller;

import com.ezequiel.productos_clientes_ventas.dto.ProductoRequest;
import com.ezequiel.productos_clientes_ventas.dto.ProductoResponse;
import com.ezequiel.productos_clientes_ventas.entities.Producto;
import com.ezequiel.productos_clientes_ventas.repository.ProductoRepository;
import com.ezequiel.productos_clientes_ventas.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/crear")
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest request){
        ProductoResponse response = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<Producto> obtenerProductos(){
        return productoService.obtenerTodosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable Long id) {
        ProductoResponse response = productoService.obtenerProducto(id);
        return ResponseEntity.ok(response);
    }


    /*@GetMapping("/falta_stock")
    public List<Producto> obtenerProductosFaltaStock() {
        return productoRepository.findByCantidadDisponibleLessThan(5);
    }*/

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ProductoResponse> eliminarProducto(@PathVariable Long id){
        ProductoResponse response = productoService.eliminarProducto(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductoResponse> editarProducto(@Valid @PathVariable Long id, @RequestBody ProductoRequest request){
        ProductoResponse response = productoService.editarProducto(id, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/pageable")
    public Page<ProductoResponse> getProductosPageable5(){
        final Pageable pageable = PageRequest.of(0, 5);

        Page<Producto> productos = productoRepository.findAll(pageable);

        return productos.map(producto -> new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion(),
                producto.getMensaje()
        ));
    }
    // Paginado genérico con sortBy y direction
    @GetMapping("/pageable/order")
    public ResponseEntity<Page<ProductoResponse>> getProductosPageableOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<ProductoResponse> result = productoService.findAllOrdered(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pageable/ordername/asc")
    public Page<ProductoResponse> getProductosAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productoService.findAllOrderByNombreAsc(PageRequest.of(page, size));
    }

    @GetMapping("/pageable/ordername/desc")
    public Page<ProductoResponse> getProductosDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productoService.findAllOrderByNombreDesc(PageRequest.of(page, size));
    }

    // ----- BUSQUEDAS / FILTROS -----
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponse>> buscarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String marca
    ) {
        List<ProductoResponse> lista = productoService.buscarProductos(nombre, marca);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/filtrar/precio")
    public ResponseEntity<List<ProductoResponse>> filtrarPorPrecio(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        List<ProductoResponse> lista = productoService.filtrarPorPrecio(min, max);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/stock/bajo")
    public ResponseEntity<List<ProductoResponse>> getProductosConBajoStock(
            @RequestParam(defaultValue = "5") int limite
    ) {
        List<ProductoResponse> lista = productoService.getProductosConBajoStock(limite);
        return ResponseEntity.ok(lista);
    }

    // ----- METRICAS -----
    @GetMapping("/count")
    public ResponseEntity<Long> contarProductos() {
        return ResponseEntity.ok(productoService.contarProductos());
    }

    @GetMapping("/precio/promedio")
    public ResponseEntity<Double> obtenerPrecioPromedio() {
        return ResponseEntity.ok(productoService.obtenerPrecioPromedio());
    }



}