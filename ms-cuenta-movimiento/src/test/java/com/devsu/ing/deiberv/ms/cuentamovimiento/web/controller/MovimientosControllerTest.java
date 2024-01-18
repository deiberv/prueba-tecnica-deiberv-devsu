package com.devsu.ing.deiberv.ms.cuentamovimiento.web.controller;


import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.MovimientosFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.MovimientosService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoRequest;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.MovimientoSaldo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MovimientosController.class})
@ContextConfiguration(classes = MovimientosController.class)
class MovimientosControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovimientosService movimientosService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMovimiento() throws Exception {
        var movimientoRQ = MovimientoRequest.builder()
            .numeroCuenta("789654")
            .valor(BigDecimal.TEN)
            .build();

        var movimientoSaldo = MovimientoSaldo.builder()
            .idMovimiento("1")
            .cuenta(movimientoRQ.getNumeroCuenta())
            .soldoDisponible(BigDecimal.TEN)
            .build();
        Mockito.when(movimientosService.registrar(ArgumentMatchers.any(MovimientoRequest.class)))
            .thenReturn(movimientoSaldo);

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoRQ)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void reporte() throws Exception {
        Mockito.when(movimientosService.reporte(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(MovimientosFixture.obtenerMovimientosVm());
        mockMvc.perform(get("/movimientos/cliente/{clienteId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("fechaInicial", "05/01/2024")
                .queryParam("fechaFin", "15/01/2024"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
