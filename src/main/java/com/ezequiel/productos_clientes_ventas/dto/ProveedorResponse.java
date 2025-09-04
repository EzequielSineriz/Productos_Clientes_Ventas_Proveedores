package com.ezequiel.productos_clientes_ventas.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorResponse {
    private Long id;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String cuitRuc;
    private String email;
    private String tipoDeProductos;
    private String metodoPago;
    private String diasDeEntrega;
    private String plazosDePago;
}
