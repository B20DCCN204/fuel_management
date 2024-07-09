package com.giangtrung.fuel_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_ENOUGH_FUEL(9999, "Not enough fuel available ", HttpStatus.BAD_REQUEST),
    DATE_NOT_NULL(1001, "Date is required", HttpStatus.BAD_REQUEST),
    START_BEFORE_END(1002, "Start date must be before end date ", HttpStatus.BAD_REQUEST),
   ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
