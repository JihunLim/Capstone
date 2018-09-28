package com.capstone.precare.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
    ApiResponseMessage : API 응답 메시지

    * status : 상태값
    * message : 메시지 내용
    * errorMessage : 오류 메시지 내용
    * errorCode : 오류 코드
 */

/**
 * Api 메세지 DTO
 *
 * created 18.08.28  by 임지훈
 */

@Getter
@Setter
@ToString
public class ApiResponseMessage {
    private String status;
    private String message;
    private String errorMessage;
    private String errorCode;

    public ApiResponseMessage() {}

    public ApiResponseMessage(String status, String message, String errorMessage, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
