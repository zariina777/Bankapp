package com.example.bankapplication.service.implementations;

import com.example.bankapplication.dto.AgreementDto;
import com.example.bankapplication.mapper.AgreementMapper;
import com.example.bankapplication.repository.AgreementRepository;
import com.example.bankapplication.service.interfaces.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;

    @Override
    public List<AgreementDto> getAllAgreements() {
        return agreementMapper.toDtoList(agreementRepository.findAll());
    }
}
