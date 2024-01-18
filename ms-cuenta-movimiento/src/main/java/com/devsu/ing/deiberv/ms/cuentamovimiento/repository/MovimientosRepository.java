package com.devsu.ing.deiberv.ms.cuentamovimiento.repository;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Movimiento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MovimientosRepository
 */
@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findAllByFechaBetween(LocalDateTime fechaStart, LocalDateTime fechaEnd);

    @Query("SELECT m, c FROM Movimiento m LEFT JOIN m.cuenta c  " +
        " LEFT JOIN c.cliente c1 WHERE c1.clienteId = ?1 AND m.fecha between ?2 AND ?3")
    List<Movimiento> findAllByClienteAndFecha(Long clienteId, LocalDateTime fechaStart, LocalDateTime fechaEnd, Sort sort);
}
