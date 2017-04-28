package com.lib.common.net;

/**
 *
 */
public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}