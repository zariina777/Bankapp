package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientDto;
import com.example.bankapplication.dto.ClientWithAccountDto;
import com.example.bankapplication.service.interfaces.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Client controller API", description = "With this controller you can get a list of all Clients, " +
        "a list of clients (or clients + accounts) who have accounts with a balance higher than certain amount")
@SecurityRequirement(name = "Bearer Authentication")
public class ClientController {
    private final ClientService clientService;

    @GetMapping()
    @Operation(summary = "Get list of all clients")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/with-balance-more-than/{amount}")
    @Operation(summary = "Get a list of clients who have accounts with a balance higher than {amount}")
    public ResponseEntity<List<ClientDto>> getClientsWithBalanceMoreThan(@PathVariable("amount") BigDecimal amount) {
        return ResponseEntity.ok(clientService.getClientsWithBalanceMoreThan(amount));
    }

    @GetMapping("/clients-accounts-with-balance-more-than/{amount}")
    @Operation(summary = "Get a list of clients and their accounts with a balance higher than {amount}")
    public ResponseEntity<List<ClientWithAccountDto>> getClientsAndAccountsWithBalanceMoreThan(@PathVariable("amount") BigDecimal amount) {
        return ResponseEntity.ok(clientService.getClientsAndAccountsWithBalanceMoreThan(amount));
    }
}
