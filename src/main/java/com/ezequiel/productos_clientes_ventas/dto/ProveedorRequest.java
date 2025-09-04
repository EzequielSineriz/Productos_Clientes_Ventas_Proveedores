package com.ezequiel.productos_clientes_ventas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProveedorRequest {

    @NotBlank(message = "La razón social no puede estar vacía")
    @Size(min = 3, max = 100, message = "La razón social debe tener entre 3 y 100 caracteres")
    private String razonSocial;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos")
    private String telefono;

    private String cuitRuc;

    @Email(message = "Debe ser un email válido")
    private String email;
    private String tipoDeProductos;
    private String metodoPago;
    private String diasDeEntrega;
    private String plazosDePago;
}
