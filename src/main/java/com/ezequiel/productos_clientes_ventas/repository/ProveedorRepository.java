package com.ezequiel.productos_clientes_ventas.repository;

import com.ezequiel.productos_clientes_ventas.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {

    List<Proveedor> findAllByOrderByRazonSocialAsc();

    List<Proveedor> findAllByOrderByRazonSocialDesc();

}
