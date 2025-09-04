package com.ezequiel.productos_clientes_ventas.repository;

import com.ezequiel.productos_clientes_ventas.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //List<Producto> findByCantidadDisponibleLessThan(int i);

    boolean existsByNombreAndMarca(String nombre, String marca);

    @Query("SELECT p FROM Producto p ORDER BY LOWER(p.nombre) ASC")
    Page<Producto> findAllOrderByNombreAsc(Pageable pageable);

    @Query("SELECT p FROM Producto p ORDER BY LOWER(p.nombre) DESC")
    Page<Producto> findAllOrderByNombreDesc(Pageable pageable);




    // Búsqueda por nombre o marca (case insensitive)
    @Query("SELECT p FROM Producto p " +
            "WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:marca IS NULL OR LOWER(p.marca) LIKE LOWER(CONCAT('%', :marca, '%')))")
    List<Producto> buscarPorNombreYMarca(@Param("nombre") String nombre,
                                         @Param("marca") String marca);

    // Filtro por rango de precios
    List<Producto> findByPrecioBetween(Double min, Double max);

    // Productos con stock menor a un límite
    List<Producto> findByStockLessThan(int limite);

    // Métricas
    long count(); // ya lo hereda JpaRepository, lo dejamos explícito
    @Query("SELECT AVG(p.precio) FROM Producto p")
    Double calcularPrecioPromedio();
}
