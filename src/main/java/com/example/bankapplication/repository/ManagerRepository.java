package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    @Query("SELECT m FROM Manager m JOIN Client c ON m = c.manager AND c.status = :status")
    List<Manager> getAllManagersByClientStatus(ClientStatus status);
}
