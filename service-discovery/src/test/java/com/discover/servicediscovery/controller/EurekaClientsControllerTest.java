package com.discover.servicediscovery.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EurekaClientsControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void getEurekaApplications() throws Exception {
        this.mockMvc.perform(get("/applications/")).andDo(print()).andExpect(status().isOk());
    }

}