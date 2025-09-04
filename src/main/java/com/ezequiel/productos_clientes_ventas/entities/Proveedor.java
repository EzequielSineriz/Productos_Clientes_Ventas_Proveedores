package com.ezequiel.productos_clientes_ventas.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String cuitRuc;
    private String email;
    private String TipoDeProductos;
    private String metodoPago;
    private String diasDeEntrega;
    private String plazosDePago;


}
