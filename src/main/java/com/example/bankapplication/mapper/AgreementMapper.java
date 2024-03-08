package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AgreementDto;
import com.example.bankapplication.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgreementMapper {
    @Mapping(source = "agreement.createdAt", target = "creationDate")
    @Mapping(expression = "java(agreement.getClient().getFullName())", target = "clientName")
    @Mapping(source = "agreement.account.name", target = "account")
    @Mapping(source = "agreement.product.name", target = "product")
    @Mapping(source = "agreement.amount", target = "amountFromAgreement")
    @Mapping(source = "agreement.account.balance", target = "amountFromAccount")
    AgreementDto toDto(Agreement agreement);

    List<AgreementDto> toDtoList(List<Agreement> agreements);
}
