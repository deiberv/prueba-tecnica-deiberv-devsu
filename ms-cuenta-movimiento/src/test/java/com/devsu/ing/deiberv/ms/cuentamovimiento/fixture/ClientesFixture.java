package com.devsu.ing.deiberv.ms.cuentamovimiento.fixture;

import com.devsu.ing.deiberv.ms.cuentamovimiento.entity.Cliente;
import com.devsu.ing.deiberv.ms.cuentamovimiento.restclients.dtos.ClienteRs;

/**
 * ClientesFixture
 */
public class ClientesFixture {

    public static Cliente getClienteEntity(){
        return Cliente.builder()
            .clienteId(15963L)
            .nombre("Nombre cliente")
            .build();
    }

    public static ClienteRs getClienteRs(){
        return new ClienteRs(123L, "Cliente");
    }

}
