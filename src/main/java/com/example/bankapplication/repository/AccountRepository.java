package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> getAccountByBalanceIsGreaterThanEqual(BigDecimal amount);

    List<Account> getAccountsByStatusIs(AccountStatus status);

    @Query(value = "SELECT * from mark_accounts_for_deletion(:date, :statusForDeletion);", nativeQuery = true)
    List<Account> getAccountsWithoutTransactionsAndCreatedEarlierThan(String date, String statusForDeletion);
}
