package com.egs.atmemulator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BadRequestException extends RuntimeException {
    private int code;
    private Object result;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
        this.code = 103;
    }

    public BadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BadRequestException(int code, String message, Object result) {
        super(message);
        this.code = code;
        this.result = result;
    }


    public int getCode() {
        return code;
    }

    public Object getResult() {
        return result;
    }
}
