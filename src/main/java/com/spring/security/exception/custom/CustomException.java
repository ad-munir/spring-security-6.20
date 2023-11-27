package com.spring.security.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CustomException extends RuntimeException{

    int code;

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }
}