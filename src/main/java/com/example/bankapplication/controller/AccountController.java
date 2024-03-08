package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AccountDto;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.service.interfaces.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Account controller API", description = "With this controller you can get a list of all accounts or delete " +
        "accounts for which there were no transactions and those created before a specific date. As a response," +
        " you will receive a list of clients (owners of these accounts) with their contact information")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {
    private final AccountService accountService;

    @GetMapping()
    @Operation(summary = "Get list of all accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get list of accounts by Status")
    public ResponseEntity<List<AccountDto>> getAccountsByStatus(@PathVariable("status") AccountStatus status) {
        return ResponseEntity.ok(accountService.getAccountsByStatus(status));
    }

    @DeleteMapping("/delete-accounts-without-transactions-and-created-earlier-than")
    @Operation(summary = "Delete (change status) accounts for which there were no transactions and those created before a specific date",
            description = "As a response, you will receive a list of accounts with their owners." +
                    " The 'date' parameter is passed as a query params")
    public ResponseEntity<List<AccountDto>> markForDeletionAccountsWithoutTransactionsAndCreatedEarlierThan(@RequestParam("date")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(accountService.markForDeletionAccountsWithoutTransactionsAndCreatedEarlierThan(date));
    }
}
