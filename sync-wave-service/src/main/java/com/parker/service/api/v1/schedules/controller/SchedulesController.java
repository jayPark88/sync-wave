package com.parker.service.api.v1.schedules.controller;

import com.parker.common.dto.request.SchedulesDto;
import com.parker.common.exception.CustomException;
import com.parker.common.resonse.CommonResponse;
import com.parker.service.api.v1.schedules.service.SchedulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_400;

@Slf4j
@RestController
@RequestMapping("/v1/schedules")
@RequiredArgsConstructor
public class SchedulesController {
    private final SchedulesService schedulesService;
    private final MessageSource messageSource;

    @PostMapping
    public CommonResponse<?> createSchedules(@Valid @RequestBody SchedulesDto schedulesDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(FAIL_400.code(), bindingResult.getAllErrors().stream().findFirst().get().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return schedulesService.createSchedules(schedulesDto);
    }
}
