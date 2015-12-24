package com.goeuro.dot.base.exception;

public class DotException extends RuntimeException {


    public DotException(String msg) {
        super(msg);
    }

    public DotException(String msg, Exception parent) {
        super(msg, parent);
    }
}
