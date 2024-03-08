package com.example.bankapplication.service.implementations;

import com.example.bankapplication.dto.AccountDto;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountMapper.toDtoList(accountRepository.findAll());
    }

    @Override
    public List<AccountDto> getAccountsByStatus(AccountStatus status) {
        List<Account> accountList = accountRepository.getAccountsByStatusIs(status);
        return accountMapper.toDtoList(accountList);
    }

    @Override
    @Transactional
    public List<AccountDto> markForDeletionAccountsWithoutTransactionsAndCreatedEarlierThan(LocalDateTime date) {
        List<Account> accountList = accountRepository.getAccountsWithoutTransactionsAndCreatedEarlierThan(date.toLocalDate().toString(), AccountStatus.FOR_DELETION.name());
        return accountMapper.toDtoList(accountList);
    }
}
