package com.parker.common.intf.impl;

import com.parker.common.intf.AlarmInterface;
import com.parker.common.resonse.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmSlackImpl implements AlarmInterface {

    /**
     * Slack Msg 발송 기능
     * @param email
     * @param msg
     * @return
     */
    @Override
    public CommonResponse<String> sendMsg(String email, String msg) {
        return null;
    }
}
