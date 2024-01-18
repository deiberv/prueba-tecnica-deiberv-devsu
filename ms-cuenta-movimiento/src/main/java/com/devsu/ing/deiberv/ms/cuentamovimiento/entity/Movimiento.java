package com.devsu.ing.deiberv.ms.cuentamovimiento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Movimiento
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;
    private BigDecimal saldoInicial;
    private BigDecimal valor;
    private BigDecimal saldo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_id")
    private Cuenta cuenta;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now(ZoneId.systemDefault());
    }

}
