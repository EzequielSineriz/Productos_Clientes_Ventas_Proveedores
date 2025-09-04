package com.ezequiel.productos_clientes_ventas.advice;

public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String mensaje) {
        super(mensaje);
    }


}
