package com.ezequiel.productos_clientes_ventas.dto;


import com.ezequiel.productos_clientes_ventas.entities.MetodoPago;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private MetodoPago metodoPago;
    private String mensaje;
}
