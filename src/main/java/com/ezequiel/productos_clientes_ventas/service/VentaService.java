package com.ezequiel.productos_clientes_ventas.service;


import com.ezequiel.productos_clientes_ventas.dto.ClienteResponse;
import com.ezequiel.productos_clientes_ventas.dto.ProductoResponse;
import com.ezequiel.productos_clientes_ventas.dto.VentaRequest;
import com.ezequiel.productos_clientes_ventas.dto.VentaResponse;
import com.ezequiel.productos_clientes_ventas.entities.Cliente;
import com.ezequiel.productos_clientes_ventas.entities.Producto;
import com.ezequiel.productos_clientes_ventas.entities.Venta;
import com.ezequiel.productos_clientes_ventas.repository.ClienteRepository;
import com.ezequiel.productos_clientes_ventas.repository.ProductoRepository;
import com.ezequiel.productos_clientes_ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VentaService {


    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public VentaResponse crearVenta(VentaRequest request){
        if (request.getClienteId() == null) {
            throw new IllegalArgumentException("El clienteId no puede ser null");
        }

        if (request.getProductoIds() == null || request.getProductoIds().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar al menos un producto");
        }

        // 2️⃣ Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + request.getClienteId()));

        // 3️⃣ Buscar productos
        List<Producto> productos = productoRepository.findAllById(request.getProductoIds());

        if (productos.isEmpty()) {
            throw new RuntimeException("No se encontraron productos válidos para los ids: " + request.getProductoIds());
        }

        // 4️⃣ Calcular total
        double total = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        // 5️⃣ Crear venta
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setTotal(total);
        venta.setUnCliente(cliente);
        venta.setListaProductos(productos);

        Venta ventaGuardada = ventaRepository.save(venta);

        // 6️⃣ Mapear a VentaResponse
        return mapToResponse(ventaGuardada);
    }



    @Transactional(readOnly = true)
    public List<VentaResponse> listarVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private VentaResponse mapToResponse(Venta venta) {
        return VentaResponse.builder()
                .id(venta.getId())
                .fechaVenta(venta.getFechaVenta())
                .total(venta.getTotal())
                .cliente(
                        ClienteResponse.builder()
                                .id(venta.getUnCliente().getId())
                                .nombre(venta.getUnCliente().getNombre())
                                .apellido(venta.getUnCliente().getApellido())
                                .dni(venta.getUnCliente().getDni())
                                .metodoPago(venta.getUnCliente().getMetodoPago())
                                .build()
                )
                .productos(
                        venta.getListaProductos().stream()
                                .map(p -> ProductoResponse.builder()
                                        .id(p.getId())
                                        .nombre(p.getNombre())
                                        .marca(p.getMarca())
                                        .precio(p.getPrecio())
                                        .stock(p.getStock())
                                        .fechaCreacion(p.getFechaCreacion())
                                        .fechaActualizacion(p.getFechaActualizacion())
                                        .build()
                                ).toList()
                )
                .build();
    }

    public VentaResponse obtenerVenta(Long id){
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return mapToResponse(venta);
    }

    @Transactional
    public VentaResponse eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        ventaRepository.delete(venta);
        return mapToResponse(venta);
    }


    public Venta editarVenta(Long id, Venta venta){
        if(ventaRepository.existsById(id)){
            venta.setId(id);
            return ventaRepository.save(venta);
        }
        return null;
    }


    public List<VentaResponse> ventasPorCliente(Long clienteId) {
        return ventaRepository.findByUnClienteId(clienteId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


       public List<VentaResponse> ventasPorRango(LocalDate desde, LocalDate hasta) {
        return ventaRepository.findByFechaVentaBetween(desde, hasta)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<VentaResponse> ventasPaginadas(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ventaRepository.findAll(pageable).map(this::mapToResponse);
    }

    public long contarVentas() {
        return ventaRepository.count();
    }

    public Double promedioVentas() {
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getTotal)
                .average()
                .orElse(0.0);
    }


    public Map<LocalDate, Double> promedioVentasPorDia() {
        List<Object[]> resultados = ventaRepository.promedioVentasPorDia();
        Map<LocalDate, Double> respuesta = new HashMap<>();
        for (Object[] fila : resultados) {
            respuesta.put((LocalDate) fila[0], (Double) fila[1]);
        }
        return respuesta;
    }

    public Map<LocalDate, Long> cantidadVentasPorDia() {
        List<Object[]> resultados = ventaRepository.cantidadVentasPorDia();
        Map<LocalDate, Long> respuesta = new HashMap<>();
        for (Object[] fila : resultados) {
            respuesta.put((LocalDate) fila[0], (Long) fila[1]);
        }
        return respuesta;
    }

    public Map<Integer, Double> promedioVentasPorMes() {
        List<Object[]> resultados = ventaRepository.promedioVentasPorMes();
        Map<Integer, Double> respuesta = new HashMap<>();
        for (Object[] fila : resultados) {
            respuesta.put((Integer) fila[0], (Double) fila[1]);
        }
        return respuesta;
    }

    public Object[] clienteConMasComprasEnMes(int mes) {
        List<Object[]> resultados = ventaRepository.clienteConMasComprasEnMes(mes);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public Object[] clienteConMasGastoEnMes(int mes) {
        List<Object[]> resultados = ventaRepository.clienteConMasGastoEnMes(mes);
        return resultados.isEmpty() ? null : resultados.get(0);
    }
}
