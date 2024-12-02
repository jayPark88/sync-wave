package com.parker.common.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodosDto {
    @NotNull(message = "{todo.task.not.null}")
    @Size(min = 1, max = 255, message = "{todo.task.size}")
    private String task;
    private LocalDate startDate;
    private String status;
    @NotNull(message = "{todo.dueDate.not.null}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}
