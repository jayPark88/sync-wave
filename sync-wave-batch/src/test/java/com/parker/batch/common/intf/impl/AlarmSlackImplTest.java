package com.parker.batch.common.intf.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class AlarmSlackImplTest {

    @Value("${notification.slack.webhook.url}")
    private String slackWebhookUrl;

    @Test
    void sendMsg() {
        // given
        String message = "hello Slack Success Parker.pen!!!!";
        RestTemplate restTemplate = new RestTemplate();

        // Slack 메시지 포맷 설정
        String payload = String.format("{\"text\": \"%s\"}", message);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        // when
        // Slack으로 POST 요청 전송
        ResponseEntity<String> response = restTemplate.exchange(
                slackWebhookUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        //then
        Assertions.assertAll(
                () -> Assertions.assertTrue(response.getStatusCode().is2xxSuccessful())
        );
    }
}