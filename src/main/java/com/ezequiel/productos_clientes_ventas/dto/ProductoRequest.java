package com.ezequiel.productos_clientes_ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ProductoRequest {


    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La marca es Obligatoria")
    private String marca;
    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private Double costo;
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer stock;

}
