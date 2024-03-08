package com.example.bankapplication.service.implementations;

import com.example.bankapplication.dto.ClientDto;
import com.example.bankapplication.dto.ClientWithAccountDto;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AccountRepository accountRepository;

    @Override
    public List<ClientDto> getClientsWithBalanceMoreThan(BigDecimal amount) {
        return clientMapper.toDtoList(clientRepository.getClientsWithBalanceMoreOrEqualThan(amount));
    }

    @Override
    public List<ClientWithAccountDto> getClientsAndAccountsWithBalanceMoreThan(BigDecimal amount) {
        List<Account> accountsWithBalanceMoreThen = accountRepository.getAccountByBalanceIsGreaterThanEqual(amount);
        return accountsWithBalanceMoreThen.stream()
                .map(clientMapper::clientWithAccountToDto)
                .toList();
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientMapper.toDtoList(clientRepository.findAll());
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

}
