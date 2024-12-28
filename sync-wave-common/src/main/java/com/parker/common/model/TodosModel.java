package com.parker.common.model;

import com.parker.common.intf.ChangableToFromEntity;
import com.parker.common.jpa.entity.TodosEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TodosModel implements ChangableToFromEntity<TodosEntity> {

    private Long id;
    private String task;
    private LocalDate startDate;
    private String status;
    private LocalDate dueDate;
    private Long userId;

    public TodosModel(TodosEntity todosEntity){
        from(todosEntity);
    }

    @Override
    public TodosEntity to() {
        return TodosEntity.builder()
                .id(id)
                .task(task)
                .startDate(startDate)
                .status(status)
                .dueDate(dueDate)
                .userId(userId)
                .build();
    }

    @Override
    public void from(TodosEntity entity) {
        this.id = entity.getId();
        this.task = entity.getTask();
        this.startDate = entity.getStartDate();
        this.status = entity.getStatus();
        this.dueDate = entity.getDueDate();
        this.userId = entity.getUserId();
    }
}
