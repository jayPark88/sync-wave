package com.parker.service.api.v1.login.service;

import com.parker.service.api.v1.login.dto.LoginDto;
import com.parker.common.dto.TokenDto;
import com.parker.common.jwt.JwtFilter;
import com.parker.common.jwt.TokenProvider;
import com.parker.common.resonse.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * com.jaypark8282.base.api.v1.login.service
 * ㄴ AuthService
 *
 * <pre>
 * description :
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
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    // Spring Security는 사용자의 인증과 권한 부여를 담당하는 프레임워크로, AuthenticationManagerBuilder는 이를 설정하는데 도움을 줍니다.
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public CommonResponse<TokenDto> authorize(LoginDto loginDto) {
        // UsernamePasswordAuthenticationToken는 주로 사용자가 제공한 사용자명(username)과 비밀번호(password)를 저장하며, 이 정보를 기반으로 사용자를 인증하는 데 활용됩니다.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        // authenticationToken을 이용해서 Authentication 객체를 생성하려고 authenticate 메서드가 실행이 될때 loadUserByUserName 메서드가 실행됩니다.(customUserDetail에서 오버라이드해서 그 비즈니스가 실행 됨)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication객체를 생성하고 이를 SecurityContext에 저장하고 Authentication 객체를 createToken 메서드를 통해서 JWT Token을 생성합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new CommonResponse<>(new TokenDto(jwt));
    }
}