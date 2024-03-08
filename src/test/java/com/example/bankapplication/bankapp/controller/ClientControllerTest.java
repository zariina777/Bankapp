package com.example.bankapplication.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.config.TestConfig;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.dto.ClientWithAccountDto;
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
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetAllClients() throws Exception {
        // when
        MvcResult clientsGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andReturn();

        // then
        Assertions.assertEquals(200, clientsGetResult.getResponse().getStatus());
        Set<ClientDto> clientDtoSet = objectMapper.readValue(clientsGetResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(5, clientDtoSet.size());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetClientsWithBalanceMoreThan() throws Exception {
        //given
        Set<ClientDto> expectedClientList = Set.of(
                new ClientDto(
                        "523e4567-e89b-12d3-a456-030000000002",
                        "Markus",
                        "Schmidt",
                        "markus.schmidt@gmx.de",
                        "Musterstrasse 17, D-80331 Munchen, Germany",
                        "+49 89 1234567"
                ),
                new ClientDto(
                        "523e4567-e89b-12d3-a456-030000000003",
                        "Lena",
                        "Weber",
                        "lena.weber@yahoo.de",
                        "Hauptstrasse 25, D-50667 Koln, Germany",
                        "+49 221 9876543"
                )
        );

        //when
        MvcResult clientListGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/clients/with-balance-more-than/100000"))
                .andReturn();

        //then
        Assertions.assertEquals(200, clientListGetResult.getResponse().getStatus());
        Set<ClientDto> clientList = objectMapper.readValue(clientListGetResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(expectedClientList, clientList);
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetClientsAndAccountsWithBalanceMoreThan() throws Exception {
        //given
        Set<ClientWithAccountDto> expectedClientList = Set.of(
                new ClientWithAccountDto(
                        "523e4567-e89b-12d3-a456-030000000002",
                        "Markus",
                        "Schmidt",
                        "markus.schmidt@gmx.de",
                        "Musterstrasse 17, D-80331 Munchen, Germany",
                        "+49 89 1234567",
                        "523e4567-e89b-12d3-a456-040000000004",
                        "DE33 2004 1144 0199 9999 00",
                        "250000.00"
                ),
                new ClientWithAccountDto(
                        "523e4567-e89b-12d3-a456-030000000003",
                        "Lena",
                        "Weber",
                        "lena.weber@yahoo.de",
                        "Hauptstrasse 25, D-50667 Koln, Germany",
                        "+49 221 9876543",
                        "523e4567-e89b-12d3-a456-040000000005",
                        "DE45 7002 0270 0015 7695 53",
                        "153256.00"
                )
        );

        //when
        MvcResult clientListGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/clients/clients-accounts-with-balance-more-than/100000"))
                .andReturn();

        //then
        Assertions.assertEquals(200, clientListGetResult.getResponse().getStatus());
        Set<ClientWithAccountDto> clientList = objectMapper.readValue(clientListGetResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(expectedClientList, clientList);
    }
}