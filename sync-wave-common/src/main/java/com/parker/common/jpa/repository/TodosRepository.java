package com.parker.common.jpa.repository;

import com.parker.common.jpa.entity.TodosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodosRepository extends JpaRepository<TodosEntity, String> {
}
