package com.hert.legacyofatbackend.exception;

public class ErrorInfo {

    private String msg;

    public ErrorInfo() {
    }

    public ErrorInfo(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
