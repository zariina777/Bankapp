package com.example.bankapplication.bankapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.config.TestConfig;
import de.telran.bankapp.dto.ManagerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

@Testcontainers
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetAllManagers() throws Exception {
        // when
        MvcResult managerGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/managers"))
                .andReturn();

        // then
        Assertions.assertEquals(200, managerGetResult.getResponse().getStatus());
        Set<ManagerDto> managerDtoSet = objectMapper.readValue(managerGetResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(4, managerDtoSet.size());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldCreateManager() throws Exception {
        // given
        ManagerDto managerDto = new ManagerDto("523e4567-e89b-12d3-a456-010000000011",
                "John",
                "Smith",
                "WORKING");

        String managerStr = objectMapper.writeValueAsString(managerDto);

        // when
        MvcResult managerCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr))
                .andReturn();

        MvcResult managerGetResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/managers/" + managerDto.getId()))
                .andReturn();

        MvcResult managerDeleteResult = mockMvc
                .perform(MockMvcRequestBuilders.delete("/managers/" + managerDto.getId()))
                .andReturn();

        // then
        Assertions.assertEquals(201, managerCreationResult.getResponse().getStatus());
        Assertions.assertEquals(200, managerGetResult.getResponse().getStatus());
        Assertions.assertEquals(204, managerDeleteResult.getResponse().getStatus());

        String managerGetStringDto = managerGetResult.getResponse().getContentAsString();
        ManagerDto receivedManagerDto = objectMapper.readValue(managerGetStringDto, ManagerDto.class);

        Assertions.assertEquals(managerDto, receivedManagerDto);
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldReceiveCreationExceptionAlreadyExist() throws Exception {
        // given
        ManagerDto managerDto = new ManagerDto("523e4567-e89b-12d3-a456-010000000001",
                "John",
                "Smith",
                "WORKING");

        String managerStr = objectMapper.writeValueAsString(managerDto);

        // when
        MvcResult managerCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr))
                .andReturn();

        MvcResult managerGetResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/managers/" + managerDto.getId()))
                .andReturn();

        // then
        Assertions.assertEquals(400, managerCreationResult.getResponse().getStatus());
        Assertions.assertEquals(200, managerGetResult.getResponse().getStatus());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldReceiveCreationExceptionIDIsEmpty() throws Exception {
        // given
        ManagerDto managerDto = new ManagerDto("",
                "John",
                "Smith",
                "WORKING");

        String managerStr = objectMapper.writeValueAsString(managerDto);

        // when
        MvcResult managerCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr))
                .andReturn();

        // then
        Assertions.assertEquals(400, managerCreationResult.getResponse().getStatus());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldUpdateManager() throws Exception {
        // given
        ManagerDto managerDto = new ManagerDto("523e4567-e89b-12d3-a456-010000000001",
                "NewFirstName",
                "NewLastName",
                "FIRED");
        String managerStr = objectMapper.writeValueAsString(managerDto);
        ManagerDto managerDto1 = new ManagerDto("523e4567-e89b-12d3-a456-010000000001",
                "James",
                "Smith",
                "WORKING");
        String managerStr1 = objectMapper.writeValueAsString(managerDto1);

        // when
        MvcResult managerUpdateResult = mockMvc.perform(MockMvcRequestBuilders.put("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr))
                .andReturn();
        MvcResult managerGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/managers/" + managerDto.getId()))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.put("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr1))
                .andReturn();

        // then
        Assertions.assertEquals(200, managerUpdateResult.getResponse().getStatus());
        Assertions.assertEquals(200, managerGetResult.getResponse().getStatus());
        String receivedManagerJson = managerGetResult.getResponse().getContentAsString();
        ManagerDto receivedManagerDto = objectMapper.readValue(receivedManagerJson, ManagerDto.class);
        Assertions.assertEquals(managerDto.getLastName(), receivedManagerDto.getLastName());
        Assertions.assertEquals(managerDto.getFirstName(), receivedManagerDto.getFirstName());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldReceiveExceptionUpdateManager() throws Exception {
        // given
        ManagerDto managerDto1 = new ManagerDto("523e4567-e89b-12d3-a456-010000000018",
                "NewFirstName",
                "NewLastName",
                "WORKING");
        String managerStr1 = objectMapper.writeValueAsString(managerDto1);
        ManagerDto managerDto2 = new ManagerDto("",
                "NewFirstName",
                "NewLastName",
                "WORKING");
        String managerStr2 = objectMapper.writeValueAsString(managerDto2);

        // when
        MvcResult managerUpdateResult1 = mockMvc.perform(MockMvcRequestBuilders.put("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr1))
                .andReturn();
        MvcResult managerUpdateResult2 = mockMvc.perform(MockMvcRequestBuilders.put("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr2))
                .andReturn();

        // then
        Assertions.assertEquals(400, managerUpdateResult1.getResponse().getStatus());
        Assertions.assertEquals(400, managerUpdateResult2.getResponse().getStatus());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldDeleteManagerByID() throws Exception {
        // given
        ManagerDto managerDto = new ManagerDto("523e4567-e89b-12d3-a456-010000000011",
                "John",
                "Smith",
                "WORKING");
        String managerStr = objectMapper.writeValueAsString(managerDto);

        // when
        MvcResult managerCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(managerStr))
                .andReturn();
        MvcResult managerDeletionResult = mockMvc.perform(MockMvcRequestBuilders.delete("/managers/" + managerDto.getId()))
                .andReturn();
        MvcResult managerGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/managers/" + managerDto.getId()))
                .andReturn();

        // then
        Assertions.assertEquals(201, managerCreationResult.getResponse().getStatus());
        Assertions.assertEquals(204, managerDeletionResult.getResponse().getStatus());
        Assertions.assertEquals(400, managerGetResult.getResponse().getStatus());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldReceiveExceptionDeleteManagerByID() throws Exception {
        // given
        String managerId = "523e4567-e89b-12d3-a456-010000000018";

        // when
        MvcResult managerGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/managers/" + managerId))
                .andReturn();
        MvcResult managerDeletionResult = mockMvc.perform(MockMvcRequestBuilders.delete("/managers/" + managerId))
                .andReturn();

        // then
        Assertions.assertEquals(400, managerDeletionResult.getResponse().getStatus());
        Assertions.assertEquals(400, managerGetResult.getResponse().getStatus());
    }

    @Test
    @WithUserDetails("vip@gmail.com")
    void shouldGetAllManagersByClientStatus() throws Exception {
        // given
        String status = "VIP";
        Set<ManagerDto> expectedManagers;
        ManagerDto managerDto1 = new ManagerDto(
                "523e4567-e89b-12d3-a456-010000000001",
                "James",
                "Smith",
                "WORKING"
        );
        ManagerDto managerDto2 = new ManagerDto(
                "523e4567-e89b-12d3-a456-010000000003",
                "Anna",
                "Lee",
                "FIRED"
        );
        expectedManagers = Set.of(managerDto1, managerDto2);

        // when
        MvcResult managerGetResult = mockMvc.perform(MockMvcRequestBuilders.get("/managers/get-all-by-clients-status/" + status))
                .andReturn();

        // then
        Assertions.assertEquals(200, managerGetResult.getResponse().getStatus());
        Set<ManagerDto> managerDtoSet = objectMapper.readValue(managerGetResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertEquals(expectedManagers, managerDtoSet);
    }
}