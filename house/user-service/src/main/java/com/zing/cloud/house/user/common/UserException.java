package com.zing.cloud.house.user.common;

import com.zing.cloud.house.user.exception.WithTypeException;

public class UserException extends RuntimeException implements WithTypeException {

    private static final long serialVersionUID = -6825242819567953352L;

    private Type type;

    public UserException(String message) {
        super(message);
        this.type = Type.LACK_PARAMETER;
    }

    public UserException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public Type type() {
        return type;
    }


    public enum Type {
        WRONG_PAGE_NUM, LACK_PARAMETER, USER_NOT_LOGIN, USER_NOT_FOUND, USER_AUTH_FAIL;
    }
}
