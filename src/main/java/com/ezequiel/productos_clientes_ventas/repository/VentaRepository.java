package com.ezequiel.productos_clientes_ventas.repository;

import com.ezequiel.productos_clientes_ventas.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByUnClienteId(Long clienteId);
    List<Venta> findByFechaVentaBetween(LocalDate desde, LocalDate hasta);
    List<Venta> findByTotalGreaterThanEqual(Double min);


    @Query("SELECT v.fechaVenta, AVG(v.total) FROM Venta v GROUP BY v.fechaVenta")
    List<Object[]> promedioVentasPorDia();

    @Query("SELECT v.fechaVenta, COUNT(v) FROM Venta v GROUP BY v.fechaVenta")
    List<Object[]> cantidadVentasPorDia();

    @Query("SELECT FUNCTION('MONTH', v.fechaVenta), AVG(v.total) FROM Venta v GROUP BY FUNCTION('MONTH', v.fechaVenta)")
    List<Object[]> promedioVentasPorMes();

    @Query("SELECT v.unCliente, COUNT(v) as totalCompras FROM Venta v " +
            "WHERE FUNCTION('MONTH', v.fechaVenta) = :mes " +
            "GROUP BY v.unCliente ORDER BY totalCompras DESC")
    List<Object[]> clienteConMasComprasEnMes(@Param("mes") int mes);

    @Query("SELECT v.unCliente, SUM(v.total) as totalGastado FROM Venta v " +
            "WHERE FUNCTION('MONTH', v.fechaVenta) = :mes " +
            "GROUP BY v.unCliente ORDER BY totalGastado DESC")
    List<Object[]> clienteConMasGastoEnMes(@Param("mes") int mes);
}
