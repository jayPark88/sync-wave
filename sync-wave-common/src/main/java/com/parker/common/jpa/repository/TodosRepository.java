package com.parker.common.jpa.repository;

import com.parker.common.jpa.entity.TodosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodosRepository extends JpaRepository<TodosEntity, Long> {
    Optional<TodosEntity>findByTask(String task);
    List<TodosEntity> findByDueDate(LocalDate dueDate);
}
