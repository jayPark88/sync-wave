package com.parker.common.util.retrofit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.parker.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Locale;

import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_500;

@Service
@RequiredArgsConstructor
public class RetrofitService {
    private final MessageSource messageSource;

    public <T> T convertInterfaceData(Call<Object> sampleInfoCall, Class<T> targetType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Gson gson = new Gson();
        Response<Object> resultCallInfo = sampleInfoCall.execute();

        if (!resultCallInfo.isSuccessful()) {
            // 예외 처리 또는 로깅 추가
            throw new CustomException(FAIL_500.code(), messageSource.getMessage("interfacing.trouble", null, Locale.getDefault()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LinkedTreeMap<String, Object> resultMap = (LinkedTreeMap<String, Object>) resultCallInfo.body();

        String json = gson.toJson(resultMap);
        return objectMapper.readValue(json, targetType);
    }
}
