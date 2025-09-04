package com.ezequiel.productos_clientes_ventas.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    // Relación con ventas
    @OneToMany(mappedBy = "unCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venta> ventas;
}
