package com.ezequiel.productos_clientes_ventas.dto;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String marca;
    private Double precio;
    private Integer stock;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String mensaje;
}
