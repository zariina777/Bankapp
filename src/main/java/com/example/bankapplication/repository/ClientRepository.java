package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("SELECT c FROM Client c JOIN Account a ON c.id = a.client.id AND a.balance >= :amount")
    List<Client> getClientsWithBalanceMoreOrEqualThan(BigDecimal amount);

    Client findClientByEmail(String email);
}
