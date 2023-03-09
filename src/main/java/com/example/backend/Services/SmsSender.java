package com.example.backend.Services;

import com.example.backend.Entity.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}
