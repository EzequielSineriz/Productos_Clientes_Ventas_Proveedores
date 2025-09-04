package com.ezequiel.productos_clientes_ventas.dto;

public class ClienteReporteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String metodoPago;
    private Long cantidadCompras;
    private Double totalGastado;

    public ClienteReporteResponse(Long id, String nombre, String apellido, String dni, String metodoPago,
                                  Long cantidadCompras, Double totalGastado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.metodoPago = metodoPago;
        this.cantidadCompras = cantidadCompras;
        this.totalGastado = totalGastado;
    }
}
