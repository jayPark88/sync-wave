package com.parker.common.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * com.jaypark8282.core.jpa.entity
 * ㄴ User
 *
 * <pre>
 * description : todo 리스트 테이블
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  parker, 1.0, 12/23/23  초기작성
 * </pre>
 *
 * @author parker
 * @version 1.0
 */
@Entity
@Table(name = "todos")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TodosEntity extends BaseInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 일정 고유 ID

    @Column(nullable = false, length = 255, columnDefinition = "VARCHAR(255) COMMENT '작업명'")
    private String task;

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) COMMENT '완료 여부'")
    private char isCompleted;

    @Column(nullable = false, columnDefinition = "DATETIME COMMENT '마감일'")
    private LocalDate dueDate; // 시작 날짜 및 시간

    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '사용자 ID'")
    private Long userId; // 사용자 ID
}