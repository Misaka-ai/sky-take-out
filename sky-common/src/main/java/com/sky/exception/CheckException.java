package com.sky.exception;

/**
 * 账号被锁定异常
 */
public class CheckException extends BaseException {

    public CheckException() {
    }

    public CheckException(String msg) {
        super(msg);
    }

}
