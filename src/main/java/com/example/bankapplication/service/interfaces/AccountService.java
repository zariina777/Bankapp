package com.example.bankapplication.service.interfaces;

import com.example.bankapplication.dto.AccountDto;
import com.example.bankapplication.entity.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts();

    List<AccountDto> getAccountsByStatus(AccountStatus status);

    List<AccountDto> markForDeletionAccountsWithoutTransactionsAndCreatedEarlierThan(LocalDateTime date);
}
