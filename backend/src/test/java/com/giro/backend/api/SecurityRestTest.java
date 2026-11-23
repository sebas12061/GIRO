package com.giro.backend.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SecurityRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saludEsPublica() throws Exception {
        mockMvc.perform(get("/api/v1/public/health"))
            .andExpect(status().isOk());
    }

    @Test
    void pedidosExigeAutenticacion() throws Exception {
        mockMvc.perform(get("/api/v1/pedidos").param("restauranteId", "00000000-0000-0000-0000-000000000001"))
            .andExpect(status().isForbidden());
    }
}
