package com.example.bankapplication.service.interfaces;

import com.example.bankapplication.dto.ClientDto;
import com.example.bankapplication.dto.ClientWithAccountDto;
import com.example.bankapplication.entity.Client;

import java.math.BigDecimal;
import java.util.List;

public interface ClientService {
    List<ClientDto> getClientsWithBalanceMoreThan(BigDecimal amount);

    List<ClientWithAccountDto> getClientsAndAccountsWithBalanceMoreThan(BigDecimal amount);

    List<ClientDto> getAllClients();

    Client findClientByEmail(String email);
}
