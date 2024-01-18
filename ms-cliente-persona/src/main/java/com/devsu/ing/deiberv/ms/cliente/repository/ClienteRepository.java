package com.devsu.ing.deiberv.ms.cliente.repository;

import com.devsu.ing.deiberv.ms.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClienteRepository
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
