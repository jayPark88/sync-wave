package com.parker.service.api.v1.todos.service;

import com.parker.common.dto.request.TodosDto;
import com.parker.common.dto.request.TodosDtoSearchDto;
import com.parker.common.jpa.entity.TodosEntity;
import com.parker.common.jpa.repository.TodosRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Commit
@Transactional
@SpringBootTest
class TodosServiceTest {

    @Autowired
    private TodosRepository todosRepository;

    private TodosDto todosDto;

    @BeforeEach
    void beforeEach() {
        // given
        todosDto = TodosDto.builder()
                .task("매일 저녁 toyProject!!")
                .dueDate(LocalDate.now().plusDays(100))
                .build();
    }

    @Test
    void createTodos() {
        // when
        Optional<TodosEntity> optionalTodosEntity = saveTodos();

        // then
        Assertions.assertAll(
                () -> assertTrue(optionalTodosEntity.isPresent()),
                () -> assertEquals(todosDto.getTask(), optionalTodosEntity.get().getTask()),
                () -> assertEquals(todosDto.getDueDate(), optionalTodosEntity.get().getDueDate())
        );
    }

    @Test
    void getDetailTodoDetailInfo() {
        // given
        Optional<TodosEntity> saveResultEntity = saveTodos();
        Long todosId = saveResultEntity.get().getId();

        // when
        Optional<TodosEntity> searchResultEntity = todosRepository.findById(todosId);

        // then
        Assertions.assertAll(
                () -> assertTrue(searchResultEntity.isPresent()),
                () -> assertEquals(searchResultEntity.get().getTask(), saveResultEntity.get().getTask())
        );
    }

    @Test
    void getTodosList() {
        // given
        saveTodos();
        TodosDtoSearchDto todosDtoSearchDto =
                TodosDtoSearchDto.builder()
                        .task("매일 저녁 toyProject!!")
                        .dueDate(LocalDate.now().plusDays(100))
                        .isCompleted('N')
                        .build();

        // when
        List<TodosEntity> todosEntityList = todosRepository.findByDueDateGreaterThanEqual(todosDtoSearchDto.getDueDate());

        if (!ObjectUtils.isEmpty(todosDtoSearchDto.getTask())) {
            todosEntityList = todosEntityList.stream().filter(item -> item.getTask().contains(todosDtoSearchDto.getTask())).toList();
        }

        if (!ObjectUtils.isEmpty(todosDtoSearchDto.getIsCompleted())) {
            todosEntityList = todosEntityList.stream().filter(item -> item.getIsCompleted() == todosDtoSearchDto.getIsCompleted()).toList();
        }

        // then
        List<TodosEntity> finalTodosEntityList = todosEntityList;
        Assertions.assertAll(
                () -> Assertions.assertFalse(finalTodosEntityList.isEmpty()),
                () -> Assertions.assertTrue(finalTodosEntityList.stream().anyMatch(item -> item.getIsCompleted() == 'N')),
                () -> Assertions.assertTrue(finalTodosEntityList.stream().anyMatch(item -> item.getTask().equals(todosDtoSearchDto.getTask())))
        );
    }

    @Test
    void modifyTodoInfo() {
        // given
        Optional<TodosEntity> saveResultEntity = saveTodos();
        TodosDto requestTodosDto = TodosDto.builder()
                .task("매일 저녁 푸쉬업!!")
                .dueDate(LocalDate.now().plusDays(10))
                .isCompleted('Y')
                .build();

        Optional<TodosEntity> targetEntity = todosRepository.findById(saveResultEntity.get().getId());

        // when
        if (targetEntity.isPresent()) {
            if (!ObjectUtils.isEmpty(requestTodosDto.getTask())) {
                log.info("task update {}", requestTodosDto.getTask());
                targetEntity.get().setTask(requestTodosDto.getTask());
            }

            if (!ObjectUtils.isEmpty(requestTodosDto.getIsCompleted())) {
                log.info("isCompleted update {}", requestTodosDto.getIsCompleted());
                targetEntity.get().setIsCompleted(requestTodosDto.getIsCompleted());
            }

            if (!ObjectUtils.isEmpty(requestTodosDto.getDueDate())) {
                log.info("dueDate update {}", requestTodosDto.getDueDate());
                targetEntity.get().setDueDate(requestTodosDto.getDueDate());
            }
            todosRepository.save(targetEntity.get());
        } else {
            Assertions.fail("데이터가 없습니다!");
        }

        // then
        targetEntity = todosRepository.findById(saveResultEntity.get().getId());

        Optional<TodosEntity> finalTargetEntity = targetEntity;

        Assertions.assertAll(
                () -> Assertions.assertTrue(finalTargetEntity.isPresent()),
                () -> Assertions.assertEquals(requestTodosDto.getTask(), finalTargetEntity.get().getTask()),
                () -> Assertions.assertEquals(requestTodosDto.getDueDate(), finalTargetEntity.get().getDueDate()),
                () -> Assertions.assertEquals(requestTodosDto.getIsCompleted(), finalTargetEntity.get().getIsCompleted())
        );
    }

    @Test
    void deleteScheduleData() {
        // given
        Optional<TodosEntity> saveResultEntity = saveTodos();
        Long todosId = saveResultEntity.get().getId();

        // when
        Optional<TodosEntity> targetEntity = todosRepository.findById(todosId);

        if (targetEntity.isPresent()) {
            todosRepository.delete(targetEntity.get());
        } else {
            Assertions.fail("데이터가 없습니다!");
        }

        //then
        targetEntity = todosRepository.findById(todosId);
        Assertions.assertTrue(targetEntity.isEmpty());
    }

    private Optional<TodosEntity> saveTodos() {
        return Optional.of(todosRepository.save(
                        TodosEntity.builder()
                                .task(todosDto.getTask())
                                .isCompleted('N')
                                .dueDate(todosDto.getDueDate())
                                .userId(1L)
                                .build()
                )
        );
    }
}
