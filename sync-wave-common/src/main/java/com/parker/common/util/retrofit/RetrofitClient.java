package com.parker.common.util.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parker.common.util.retrofit.intf.sample.SampleOpenApiService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitClient {
    @Value("${service.interface.sample.url}")
    private String sampleOpenApiUrl;

    // KaKaoService의 인스터를 생성 호출을 위한 메서드
    public SampleOpenApiService getSampleOpenApiService() {
        return getKaKaoOpenApiInstance().create(SampleOpenApiService.class);
    }

    // sampleOpenApi 설정
    private Retrofit getKaKaoOpenApiInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder()
//                    .header("Authorization", "apiKey") // api키가 있다면 헤더를 추가합니다.
                    .method(originalRequest.method(), originalRequest.body());
            Request modifiedRequest = requestBuilder.build();
            return chain.proceed(modifiedRequest);
        });

        return new Retrofit.Builder()
                .baseUrl(sampleOpenApiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build()) // OkHttpClient를 Retrofit에 설정합니다.
                .build();
    }
}
