package com.ezequiel.productos_clientes_ventas.repository;

import com.ezequiel.productos_clientes_ventas.entities.Cliente;
import com.ezequiel.productos_clientes_ventas.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
