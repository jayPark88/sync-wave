package com.parker.common.jpa.repository;

import com.parker.common.jpa.entity.SchedulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchedulesRepository extends JpaRepository<SchedulesEntity, String> {
    Optional<SchedulesEntity>findByTitle(String title);
    Optional<SchedulesEntity>findById(Long id);
}
