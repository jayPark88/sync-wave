package com.parker.common.jpa.repository;

import com.parker.common.jpa.entity.SchedulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulesRepository extends JpaRepository<SchedulesEntity, String> {
}
