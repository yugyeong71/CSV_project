package com.example.csv.common;

public class ResponseMessage { // 요청 응답 메시지를 위한 클래스

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
