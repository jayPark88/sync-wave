package com.parker.common.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodosDtoSearchDto {
    private String task;
    private char isCompleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}