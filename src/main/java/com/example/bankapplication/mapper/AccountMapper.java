package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDto;
import com.example.bankapplication.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);
    List<AccountDto> toDtoList(List<Account> accountList);

}
