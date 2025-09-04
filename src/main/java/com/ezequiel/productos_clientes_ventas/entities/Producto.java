package com.ezequiel.productos_clientes_ventas.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String marca;
    private Double precio;
    private Integer stock;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String mensaje;

    @PrePersist  // Se ejecuta al insertar (CREATE)
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate   // Se ejecuta al actualizar (UPDATE)
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}

