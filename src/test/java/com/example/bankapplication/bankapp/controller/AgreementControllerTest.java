package com.example.bankapplication.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.config.TestConfig;
import de.telran.bankapp.dto.AgreementDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

@Testcontainers
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class AgreementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetAllAgreements() throws Exception {
        // when
        MvcResult agreementsGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/agreements"))
                .andReturn();

        // then
        Assertions.assertEquals(200, agreementsGetResult.getResponse().getStatus());
        Set<AgreementDto> agrrementDtoSet = objectMapper.readValue(agreementsGetResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(3, agrrementDtoSet.size());
    }
}