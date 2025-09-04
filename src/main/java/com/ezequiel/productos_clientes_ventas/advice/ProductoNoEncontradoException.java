package com.ezequiel.productos_clientes_ventas.advice;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(String mensaje){
        super(mensaje);
    }

}
