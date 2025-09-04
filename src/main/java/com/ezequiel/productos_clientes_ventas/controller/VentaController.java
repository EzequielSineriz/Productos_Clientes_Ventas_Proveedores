package com.ezequiel.productos_clientes_ventas.controller;

import com.ezequiel.productos_clientes_ventas.dto.VentaRequest;
import com.ezequiel.productos_clientes_ventas.dto.VentaResponse;
import com.ezequiel.productos_clientes_ventas.entities.Venta;
import com.ezequiel.productos_clientes_ventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    @Autowired
    private final VentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<VentaResponse> crearVenta(@RequestBody VentaRequest request){
        VentaResponse response = ventaService.crearVenta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build(); // 204 sin body
    }


    @PutMapping("/editar/{codigoVenta}")
    public Venta editarVenta(@PathVariable Long codigoVenta,@RequestBody Venta venta){
        return ventaService.editarVenta(codigoVenta, venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerVenta(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVenta(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaResponse>> ventasPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.ventasPorCliente(clienteId));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<VentaResponse>> ventasPorRango(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta
    ) {
        return ResponseEntity.ok(ventaService.ventasPorRango(desde, hasta));
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<VentaResponse>> ventasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fechaVenta") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(ventaService.ventasPaginadas(page, size, sortBy, direction));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> contarVentas() {
        return ResponseEntity.ok(ventaService.contarVentas());
    }

    @GetMapping("/promedio")
    public ResponseEntity<Double> promedioVentas() {
        return ResponseEntity.ok(ventaService.promedioVentas());
    }


    @GetMapping("/reportes/promedio-dia")
    public ResponseEntity<Map<LocalDate, Double>> promedioPorDia() {
        return ResponseEntity.ok(ventaService.promedioVentasPorDia());
    }

    @GetMapping("/reportes/cantidad-dia")
    public ResponseEntity<Map<LocalDate, Long>> cantidadPorDia() {
        return ResponseEntity.ok(ventaService.cantidadVentasPorDia());
    }

    @GetMapping("/reportes/promedio-mes")
    public ResponseEntity<Map<Integer, Double>> promedioPorMes() {
        return ResponseEntity.ok(ventaService.promedioVentasPorMes());
    }

    @GetMapping("/reportes/cliente-mas-compras")
    public ResponseEntity<Object> clienteMasCompras(@RequestParam int mes) {
        Object[] resultado = ventaService.clienteConMasComprasEnMes(mes);
        if (resultado == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(Map.of(
                "cliente", resultado[0],
                "compras", resultado[1]
        ));
    }

    @GetMapping("/reportes/cliente-mas-gasto")
    public ResponseEntity<Object> clienteMasGasto(@RequestParam int mes) {
        Object[] resultado = ventaService.clienteConMasGastoEnMes(mes);
        if (resultado == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(Map.of(
                "cliente", resultado[0],
                "totalGastado", resultado[1]
        ));
    }
}