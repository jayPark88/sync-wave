package com.parker.service.api.v1.login.controller;

import com.parker.service.api.v1.login.service.AuthService;
import com.parker.common.dto.LoginDto;
import com.parker.common.exception.CustomException;
import com.parker.common.resonse.CommonResponse;
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
import java.util.Locale;

import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_401;


/**
 * com.jaypark8282.base.api.v1.login.controller;
 * ㄴ AuthController
 *
 * <pre>
 * description : 최초 로그인 호출 시 jwtFilter를 타서 interceptor까지 다 끝난 후 401이 뜨지 않고 온다 permitall이기 때문
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  parker, 1.0, 12/25/23  초기작성
 * </pre>
 *
 * @author parker
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    @PostMapping("/login")
    public CommonResponse<?> authorize(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomException(FAIL_401.code(), messageSource.getMessage("http.status.unauthorized", null, Locale.getDefault()), HttpStatus.UNAUTHORIZED);
        }
        return authService.authorize(loginDto);
    }
}