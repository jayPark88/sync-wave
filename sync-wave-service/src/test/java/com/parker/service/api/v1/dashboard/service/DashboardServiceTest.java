package com.parker.service.api.v1.dashboard.service;

import com.parker.common.enums.TodoStatus;
import com.parker.common.exception.CustomException;
import com.parker.common.jpa.entity.SchedulesEntity;
import com.parker.common.jpa.entity.TodosEntity;
import com.parker.common.jpa.repository.SchedulesRepository;
import com.parker.common.jpa.repository.TodosRepository;
import com.parker.common.model.SchedulesModel;
import com.parker.common.model.TodosModel;
import com.parker.service.api.v1.dashboard.dto.DashBoardDto;
import com.parker.service.api.v1.dashboard.dto.SearchRequestDto;
import com.parker.service.api.v1.schedules.dto.SearchSchedulesDto;
import com.parker.service.api.v1.todos.dto.TodosDtoSearchDto;
import com.parker.service.api.v1.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;

import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_400;

@Slf4j
@Transactional
@SpringBootTest
class DashboardServiceTest {
    @Autowired
    SchedulesRepository schedulesRepository;
    @Autowired
    TodosRepository todosRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    UserService userService;

    @Test
    void getDashBoardInfo() {
        // given
        DashBoardDto dashBoardDto = new DashBoardDto();
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setTargetDate(LocalDate.now());
        Long userId = 1L;

        SearchSchedulesDto searchSchedulesDto = new SearchSchedulesDto();
        TodosDtoSearchDto todosDtoSearchDto = new TodosDtoSearchDto();

        // 특정 달 설정
        YearMonth yearMonth = YearMonth.of(2024, 12); // 2024년 12월

        searchSchedulesDto.setStartDate(yearMonth.atDay(1));// 1일
        searchSchedulesDto.setEndDate(yearMonth.atEndOfMonth());// 말일

        todosDtoSearchDto.setStartDate(yearMonth.atDay(1));
        todosDtoSearchDto.setDueDate(yearMonth.atEndOfMonth());

        // when
        List<SchedulesEntity> schedulesEntityList = getDetailScheduleList(searchSchedulesDto, userId);
        dashBoardDto.setSchedulesDtoList(schedulesEntityList.stream().map(SchedulesModel::new).toList());

        List<TodosEntity>todosEntityList = getDetailTodosList(todosDtoSearchDto, userId).stream().filter(item -> item.getStatus().equals(TodoStatus.PENDING.code()) || item.getStatus().equals(TodoStatus.IN_PROGRESS.code())).toList();
        dashBoardDto.setTodayList(todosEntityList.stream().map(TodosModel::new).toList());

        // then
        Assertions.assertAll(
                () -> Assertions.assertFalse(dashBoardDto.getSchedulesDtoList().isEmpty()),
                () -> Assertions.assertFalse(dashBoardDto.getTodayList().isEmpty())
        );
    }


    public List<SchedulesEntity> getDetailScheduleList(SearchSchedulesDto searchSchedulesDto, Long userId) {
        List<SchedulesEntity> schedulesEntityList = schedulesRepository.findByUserIdAndStartDateTimeBetween(userId, searchSchedulesDto.getStartDate().atStartOfDay(), searchSchedulesDto.getEndDate().atTime(23, 59, 59));

        if (!schedulesEntityList.isEmpty()) {
            if (!ObjectUtils.isEmpty(searchSchedulesDto.getTitle())) {
                schedulesEntityList = schedulesEntityList.stream()
                        .filter(item -> item.getTitle().contains(searchSchedulesDto.getTitle()))
                        .toList();
            }
            if (!ObjectUtils.isEmpty(searchSchedulesDto.getDescription())) {
                schedulesEntityList = schedulesEntityList.stream()
                        .filter(item -> item.getDescription().contains(searchSchedulesDto.getDescription()))
                        .toList();
            }
            return schedulesEntityList;
        } else {
            throw new CustomException(FAIL_400.code(), messageSource.getMessage("schedules.data.not.found", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }
    }

    public List<TodosEntity> getDetailTodosList(TodosDtoSearchDto todosDtoSearchDto, Long userId) {
        return todosRepository.findByUserIdAndDueDateGreaterThanEqual(userId, todosDtoSearchDto.getStartDate());
    }
}