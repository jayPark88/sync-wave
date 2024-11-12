package com.parker.service.api.v1.schedules.service;

import com.parker.common.dto.request.SchedulesDto;
import com.parker.common.jpa.entity.SchedulesEntity;
import com.parker.common.jpa.repository.SchedulesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class SchedulesServiceTest {
    private SchedulesDto schedulesDto;

    @Autowired
    private SchedulesRepository schedulesRepository;

    @BeforeEach
    void init() {
        // given
        schedulesDto = SchedulesDto.builder()
                .title("토이프로젝트 개발")
                .description("얼른 완성 시켜서 포트폴리오에 사용하자!")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(10))
                .build();
    }

    @Test
    void createSchedules() {
        // when
        Optional<SchedulesEntity> schedulesEntity = saveSchedule();

        // then
        Assertions.assertAll(
                () -> assertTrue(schedulesEntity.isPresent()),
                () -> assertEquals(schedulesDto.getTitle(), schedulesEntity.get().getTitle()),
                () -> assertEquals(schedulesDto.getDescription(), schedulesEntity.get().getDescription())
        );

    }

    @Test
    void readSchedules() {
        // given
        Optional<SchedulesEntity> schedulesEntity = saveSchedule();

        SchedulesDto schedulesDto = SchedulesDto.builder().title("토이프로젝트 개발").build();

        // when
        Optional<SchedulesEntity> optionalSchedulesEntity = schedulesRepository.findByTitle(schedulesDto.getTitle());

        // then
        Assertions.assertAll(
                () -> assertTrue(schedulesEntity.isPresent()),
                () -> assertEquals(schedulesDto.getTitle(), schedulesEntity.get().getTitle())
        );


    }

    @Test
    void updateSchedules() {
        //todo: 11/13
        System.out.println("updateSchedules not yet");
    }

    @Test
    void deleteSchedules() {
        //todo: 11/13
        System.out.println("deleteSchedules not yet");
    }

    private Optional<SchedulesEntity> saveSchedule() {
        return Optional.of(schedulesRepository.save(SchedulesEntity.builder()
                .title(schedulesDto.getTitle())
                .description(schedulesDto.getDescription())
                .startDateTime(schedulesDto.getStartDateTime())
                .endDateTime(schedulesDto.getEndDateTime())
                .userId(1L)
                .build()));
    }
}