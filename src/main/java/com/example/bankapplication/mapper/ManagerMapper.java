package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ManagerDto;
import com.example.bankapplication.entity.Manager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerDto toDto(Manager manager);
    List<ManagerDto> toDtoList(List<Manager> managers);
}

