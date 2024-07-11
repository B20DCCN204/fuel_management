package com.giangtrung.fuel_management.exception;

import com.giangtrung.fuel_management.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, InsufficientQuantityException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        if(ex instanceof MethodArgumentNotValidException){
            String message = ex.getMessage();
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            error.setMessage(message.substring(start+1, end-1));
        }else if(ex instanceof InsufficientQuantityException){
            error.setMessage(ex.getMessage());
        }
        return error;
    }

}
