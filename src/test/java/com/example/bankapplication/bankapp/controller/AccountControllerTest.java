package com.example.bankapplication.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.config.TestConfig;
import de.telran.bankapp.dto.AccountDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.repository.AccountRepository;
import org.jetbrains.annotations.NotNull;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Testcontainers
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetAllAccounts() throws Exception {

        // when
        MvcResult accountsGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andReturn();

        // then
        Assertions.assertEquals(200, accountsGetResult.getResponse().getStatus());
        Set<AccountDto> accountDtoSet = objectMapper.readValue(accountsGetResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(7, accountDtoSet.size());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void markForDeletionAccountsWithoutTransactionsAndCreatedEarlierThan() throws Exception {
        // given
        MvcResult accountsBeforeDeleteResult = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/FOR_DELETION"))
                .andReturn();
        List<AccountDto> exceptedAccountDtoList = getAccountDtoList();

        // when
        MvcResult accountsDeleteResult = mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/delete-accounts-without-transactions-and-created-earlier-than")
                        .param("date", "2023-01-01T00:00:00")
                )
                .andReturn();
        List<AccountDto> accountDtoList = objectMapper.readValue(accountsDeleteResult.getResponse().getContentAsString(), new TypeReference<>() {});

        MvcResult accountsAfterDeleteResult = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/FOR_DELETION"))
                .andReturn();

        // then
        Assertions.assertEquals(200, accountsBeforeDeleteResult.getResponse().getStatus());
        Set<AccountDto> accountDtoSet = objectMapper.readValue(accountsBeforeDeleteResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(0, accountDtoSet.size());

        Assertions.assertEquals(200, accountsDeleteResult.getResponse().getStatus());
        Assertions.assertEquals(exceptedAccountDtoList, accountDtoList);
        Assertions.assertEquals(200, accountsAfterDeleteResult.getResponse().getStatus());
        accountDtoSet = objectMapper.readValue(accountsAfterDeleteResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(2, accountDtoSet.size());
        List<String> uuidList = accountDtoSet.stream()
                        .map(AccountDto::getId)
                                .toList();
        Assertions.assertTrue(uuidList.contains("523e4567-e89b-12d3-a456-040000000003") && uuidList.contains("523e4567-e89b-12d3-a456-040000000006"));


    }

    @NotNull
    private static List<AccountDto> getAccountDtoList() {
        ClientDto clientDto1 = new ClientDto(
                "523e4567-e89b-12d3-a456-030000000001",
                "Lukas",
                "Muller",
                "regular@gmail.com",
                "Lansstrasse 81, D-11179 Berlin, Germany",
                "+49 30 5684962"
        );
        ClientDto clientDto2 = new ClientDto(
                "523e4567-e89b-12d3-a456-030000000003",
                "Lena",
                "Weber",
                "lena.weber@yahoo.de",
                "Hauptstrasse 25, D-50667 Koln, Germany",
                "+49 221 9876543"
        );
        AccountDto accountDto1 = new AccountDto(
                "523e4567-e89b-12d3-a456-040000000003",
                clientDto1,
                "DE71 5123 0800 0000 6830 99",
                "CURRENT",
                "FOR_DELETION",
                BigDecimal.valueOf(0.00).movePointLeft(1),
                "EUR",
                "2022-08-06T10:35:00",
                "2022-08-06T10:35:00"
        );
        AccountDto accountDto2 = new AccountDto(
                "523e4567-e89b-12d3-a456-040000000006",
                clientDto2,
                "DE98 5001 0517 5407 3249 31",
                "CURRENT",
                "FOR_DELETION",
                BigDecimal.valueOf(0.00).movePointLeft(1),
                "EUR",
                "2022-12-06T10:35:00",
                "2022-12-06T10:35:00"
        );

        return List.of(accountDto1, accountDto2);
    }
}