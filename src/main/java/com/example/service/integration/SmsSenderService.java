package com.example.service.integration;


import com.example.entity.sms.SmsHistoryEntity;

public interface SmsSenderService {

    void sendSmsHTTP(SmsHistoryEntity smsHistory);

}
