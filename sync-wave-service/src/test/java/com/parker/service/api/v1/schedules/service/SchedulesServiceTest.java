package com.parker.service.api.v1.schedules.service;

import com.parker.common.dto.request.SchedulesDto;
import com.parker.common.jpa.entity.SchedulesEntity;
import com.parker.common.jpa.repository.SchedulesRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
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

        SchedulesDto requestSchedulesDto = SchedulesDto.builder().title("토이프로젝트 개발").build();

        // when
        Optional<SchedulesEntity> optionalSchedulesEntity = schedulesRepository.findByTitle(requestSchedulesDto.getTitle());

        // then
        Assertions.assertAll(
                () -> assertTrue(schedulesEntity.isPresent()),
                () -> assertEquals(requestSchedulesDto.getTitle(), schedulesEntity.get().getTitle())
        );
    }

    @Test
    void updateSchedules() {
        // given
        Optional<SchedulesEntity> schedulesEntity = saveSchedule();
        SchedulesDto schedulesDto = SchedulesDto.builder()
                .title("토이프로젝트 개발2")
                .description("얼른 완성 시켜서 포트폴리오에 사용하자2!")
                .startDateTime(LocalDateTime.now().minusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1))
                .build();

        Optional<SchedulesEntity>optionalSchedulesEntity = schedulesRepository.findById(schedulesEntity.get().getId());

        // when
        if(optionalSchedulesEntity.isPresent()){
            if(!ObjectUtils.isEmpty(schedulesDto.getTitle())){
                log.debug("title update {}", schedulesDto.getTitle());
                optionalSchedulesEntity.get().setTitle(schedulesDto.getTitle());
            }

            if(!ObjectUtils.isEmpty(schedulesDto.getDescription())){
                log.debug("title update {}", schedulesDto.getDescription());
                optionalSchedulesEntity.get().setDescription(schedulesDto.getDescription());
            }

            if(!ObjectUtils.isEmpty(schedulesDto.getStartDateTime())){
                log.debug("title startDateTime {}", schedulesDto.getStartDateTime());
                optionalSchedulesEntity.get().setStartDateTime(schedulesDto.getStartDateTime());
            }

            if(!ObjectUtils.isEmpty(schedulesDto.getEndDateTime())){
                log.debug("title endDateTime {}", schedulesDto.getEndDateTime());
                optionalSchedulesEntity.get().setEndDateTime(schedulesDto.getEndDateTime());
            }

            schedulesRepository.save(optionalSchedulesEntity.get());

        }else{
            Assertions.fail("데이터가 없습니다!");
        }

        // then
        optionalSchedulesEntity = schedulesRepository.findById(schedulesEntity.get().getId());
        Optional<SchedulesEntity> finalOptionalSchedulesEntity = optionalSchedulesEntity;

        Assertions.assertAll(
                () ->Assertions.assertTrue(finalOptionalSchedulesEntity.isPresent()),
                () ->Assertions.assertEquals("토이프로젝트 개발2", finalOptionalSchedulesEntity.get().getTitle()),
                () ->Assertions.assertEquals("얼른 완성 시켜서 포트폴리오에 사용하자2!", finalOptionalSchedulesEntity.get().getDescription()),
                () ->Assertions.assertEquals(LocalDateTime.now().minusDays(1).getDayOfYear(), finalOptionalSchedulesEntity.get().getStartDateTime().getDayOfYear()),
                () ->Assertions.assertEquals(LocalDateTime.now().plusDays(1).getDayOfYear(), finalOptionalSchedulesEntity.get().getEndDateTime().getDayOfYear())

        );
    }

    @Test
    void deleteSchedules() {
        // given
        Optional<SchedulesEntity> schedulesEntity = saveSchedule();
        Long targetScheduleId = schedulesEntity.get().getId();

        // when
        Optional<SchedulesEntity>optionalSchedulesEntity = schedulesRepository.findById(schedulesEntity.get().getId());

        if(optionalSchedulesEntity.isPresent()){
            schedulesRepository.delete(optionalSchedulesEntity.get());
        }else{
            Assertions.fail("데이터가 없습니다!");
        }

        //then
        optionalSchedulesEntity = schedulesRepository.findById(schedulesEntity.get().getId());
        Assertions.assertTrue(optionalSchedulesEntity.isEmpty());
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