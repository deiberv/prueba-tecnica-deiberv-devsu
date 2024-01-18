package com.devsu.ing.deiberv.ms.cuentamovimiento.repository;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CuentasRepository
 */
@Repository
public interface CuentasRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByCliente(Cliente cliente);

}
