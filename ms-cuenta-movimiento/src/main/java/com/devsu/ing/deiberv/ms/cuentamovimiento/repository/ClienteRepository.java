package com.devsu.ing.deiberv.ms.cuentamovimiento.repository;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClienteRepository
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
