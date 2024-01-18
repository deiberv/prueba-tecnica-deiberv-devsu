package com.devsu.ing.deiberv.ms.cliente.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Persona
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Persona {

    private String nombre;
    @Enumerated(EnumType.STRING)
    private EnumGenero genero;
    private Long edad;
    private String identificacion;
    private String direccion;
    private String telefono;

}
