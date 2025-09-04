package com.ezequiel.productos_clientes_ventas.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRequest {

    private LocalDate fechaVenta;
    @NonNull
    private Long clienteId;
    @NonNull
    private List<Long> productoIds; // se mandan los ids de productos a comprar
}