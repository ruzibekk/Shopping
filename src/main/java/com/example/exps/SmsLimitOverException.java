package com.example.exps;

public class SmsLimitOverException extends RuntimeException {
    public SmsLimitOverException(String message) {
        super(message);
    }
}
