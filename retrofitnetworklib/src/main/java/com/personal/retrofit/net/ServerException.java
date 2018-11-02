package com.personal.retrofit.net;

public class ServerException extends Exception {

    private int errorCode;

    public ServerException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
