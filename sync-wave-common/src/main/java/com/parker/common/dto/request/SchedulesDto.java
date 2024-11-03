package com.parker.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchedulesDto {

    @NotNull(message = "{schedules.title.not.null}")
    @Size(min = 1, max =100, message = "{user.name.size}")
    private String title;

    @NotNull(message = "{schedules.description.not.null}")
    @Size(min = 1, max =500, message = "{user.name.size}")
    private String description;

    @NotNull(message = "{schedules.date.time.not.null}")
    private LocalDateTime startDateTime;

    @NotNull(message = "{schedules.date.time.not.null}")
    private LocalDateTime endDateTime;
}
