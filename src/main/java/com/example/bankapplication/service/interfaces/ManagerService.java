package com.example.bankapplication.service.interfaces;

import com.example.bankapplication.dto.ManagerDto;
import com.example.bankapplication.entity.enums.ClientStatus;

import java.util.List;
import java.util.UUID;

public interface ManagerService {
    List<ManagerDto> getAllManagers();

    ManagerDto getManagerByID(UUID id);

    void deleteManagerByID(UUID id);

    void createManager(ManagerDto managerDto);

    void updateManager(ManagerDto managerDto);

    List<ManagerDto> getAllManagersByClientStatus(ClientStatus status);

}
