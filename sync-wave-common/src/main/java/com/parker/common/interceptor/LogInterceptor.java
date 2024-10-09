package com.parker.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";
    public static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String uuid = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        request.setAttribute(LOG_ID, uuid);
        request.setAttribute(START_TIME, startTime);

        log.info("REQUEST  [{}][{}][{}][{}]", uuid, requestURI, queryString, handler);

        // 요청 헤더 로깅
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> log.info("Header [{}]: {}", headerName, request.getHeader(headerName)));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        long startTime = (long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("RESPONSE [{}][{}][{}ms][{}]", logId, requestURI, duration, response.getStatus());

        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}

