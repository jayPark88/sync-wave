package com.parker.service.api.v1.todos.service;

import com.parker.common.dto.request.TodosDto;
import com.parker.common.dto.request.TodosDtoSearchDto;
import com.parker.common.jpa.entity.TodosEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodosService {
    //todo: 11/18일 개발 예정, junit 부터 😎
    //todo: createTodo
    public TodosEntity createSchedules(TodosDto todosDto) {
        return null;
    }

    //todo: readTodo
    public TodosEntity getDetailTodoDetailInfo(Long todosId) {
        return null;
    }

    //todo: readTodo
    public List<TodosEntity> getDetailTodosList(TodosDtoSearchDto todosDtoSearchDto) {
        return null;
    }

    //todo: updateTodo
    public TodosEntity modifyTodoInfo(Long todosId, TodosDtoSearchDto todosDtoSearchDto) {
        return null;
    }

    //todo: deleteTodo
    public String deleteScheduleData(Long todosId){
        return null;
    }
}
