package com.zing.cloud.house.api.utils;

public class RestException extends RuntimeException {

    private static final long serialVersionUID = -3834533661930691909L;

    public RestException(String errorCode) {
        super(errorCode);
    }

    public RestException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public RestException(String errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
    }

    public RestException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode + ":" + errorMsg, cause);
    }

    public RestException(Throwable cause) {
        super(cause);
    }

}
