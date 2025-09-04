package com.ezequiel.productos_clientes_ventas.dto;

import com.ezequiel.productos_clientes_ventas.entities.MetodoPago;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String apellido;
    private String dni;
    private MetodoPago metodoPago;
}
