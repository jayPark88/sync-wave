package com.parker.common.intf;


import com.parker.common.resonse.CommonResponse;

public interface AlarmInterface {
    public CommonResponse<String> sendMsg(String email, String msg);
}
