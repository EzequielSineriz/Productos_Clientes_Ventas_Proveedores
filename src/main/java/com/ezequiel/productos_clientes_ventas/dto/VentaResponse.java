package com.ezequiel.productos_clientes_ventas.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {
    private Long id;
    private LocalDate fechaVenta;
    private Double total;

    private ClienteResponse cliente;
    private List<ProductoResponse> productos;

    private String mensaje;
}
