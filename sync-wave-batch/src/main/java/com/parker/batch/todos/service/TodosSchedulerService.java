package com.parker.batch.todos.service;

import com.parker.batch.common.intf.AlarmInterface;
import com.parker.common.enums.TodoStatus;
import com.parker.common.jpa.entity.TodosEntity;
import com.parker.common.jpa.entity.UserEntity;
import com.parker.common.jpa.repository.TodosRepository;
import com.parker.common.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodosSchedulerService {
    private final TodosRepository todosRepository;
    private final UserRepository userRepository;

    private final AlarmInterface alarmSlackImpl;

    public void alertUsersAboutTodosReminderTask() {
        log.info("금일 기준 Todos DueDate 종료되지 않은 리스트 조회");
        List<TodosEntity> todosEntityList = todosRepository.findByDueDateGreaterThanEqual(LocalDate.now());

        log.info("todosEntityList 중 중복 Id 제거");
        List<TodosEntity> removeDuplicateList =
                todosEntityList.stream().filter(item -> item.getStatus().equals(TodoStatus.PENDING.code())).collect(Collectors.toMap( // toMap을 사용해 스트림 데이터를 Map으로 변환
                        TodosEntity::getUserId, // 중복 제거 기준 필드, Map의 키로 사용
                        todosEntity -> todosEntity, // Map의 값은 TodosEntity 객체 자체로 설정
                        (existing, replacement) -> replacement // 중복 키 발생 시 기존 값 대신 새 값으로 교체
                )).values().stream().toList(); // Map의 값(value) 컬렉션을 스트림으로 변환 후 List로 재생성

        log.info("알림 발송 요청! 🚀");
        removeDuplicateList.stream().parallel().forEach(item -> {
            log.info("Thread: {} 처리 중: {}", Thread.currentThread().getName(), item);
            Optional<UserEntity> optionalUserEntity = userRepository.findById(item.getUserId());
            log.info("알림 대상자 조회 {} ", optionalUserEntity);
            if (optionalUserEntity.isPresent()) {
                log.info("알림 발송 요청! 🚀");
                alarmSlackImpl.sendMsg(optionalUserEntity.get().getEmail(), generateAlaramMsg(item, optionalUserEntity));
            }
        });

    }

    /**
     *
     * @param todosEntity
     * @param optionalUserEntity
     * @return
     */
    private String generateAlaramMsg(TodosEntity todosEntity, Optional<UserEntity> optionalUserEntity) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(optionalUserEntity.isPresent() ? optionalUserEntity.get().getUserName() : "");
        stringBuilder.append("님 한시간 뒤 아래와 같은 일정이 예정되어 있습니다 🚀");
        stringBuilder.append("\n\n");
        stringBuilder.append("︎︎◼︎ todo 내용: ");
        stringBuilder.append(todosEntity.getTask());
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

}
