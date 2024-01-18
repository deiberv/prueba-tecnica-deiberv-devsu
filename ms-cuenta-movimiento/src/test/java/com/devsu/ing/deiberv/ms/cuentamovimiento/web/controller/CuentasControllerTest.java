package com.devsu.ing.deiberv.ms.cuentamovimiento.web.controller;

import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoCuenta;
import com.devsu.ing.deiberv.ms.cuentamovimiento.enums.TipoEstado;
import com.devsu.ing.deiberv.ms.cuentamovimiento.fixture.CuentasFixture;
import com.devsu.ing.deiberv.ms.cuentamovimiento.service.CuentasService;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.ClienteVm;
import com.devsu.ing.deiberv.ms.cuentamovimiento.web.vm.CuentaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CuentasController.class})
@ContextConfiguration(classes = CuentasController.class)
class CuentasControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CuentasService cuentaService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar() throws Exception {
        Mockito.when(cuentaService.listarCuentas(any(Pageable.class)))
            .thenReturn(CuentasFixture.obtenerListadoCuentas());
        mockMvc.perform(get("/cuentas")
                .param("page", "1")
                .param("size", "15"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarPorId() throws Exception {
        Mockito.when(cuentaService.buscarCuenta(anyString()))
            .thenReturn(CuentasFixture.obtenerCuentaVm());
        mockMvc.perform(get("/cuentas/detalle/{numeroCuenta}", 12345678))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void crear() throws Exception {
        var request = CuentaRequest.builder()
            .numeroCuenta("12345678")
            .tipoCuenta(TipoCuenta.AHORRO)
            .cliente(ClienteVm.builder()
                .clienteId(1L)
                .nombre("Cliente")
                .build())
            .estado(TipoEstado.True)
            .saldoInicial(new BigDecimal("50000"))
            .build();
        Mockito.when(cuentaService.crearCuenta(any(CuentaRequest.class)))
            .thenReturn(CuentasFixture.obtenerCuentaVm());
        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void eliminar() throws Exception {
        Mockito.doNothing().when(cuentaService).eliminarCuenta(anyString());
        mockMvc.perform(delete("/cuentas/{numeroCuenta}", 987456))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
