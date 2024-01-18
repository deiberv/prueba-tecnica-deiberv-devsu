package com.devsu.ing.deiberv.ms.cuentamovimiento.entity;

import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Cuenta
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numeroCuenta"})
})
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;
    @Column(unique = true, length = 10, nullable = false)
    private String numeroCuenta;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private TipoEstado estado;
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    private Set<Movimiento> movimientos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

}
