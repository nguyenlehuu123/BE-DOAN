package com.nguyen.master.NguyenMaster.core.exceptions.rest;

import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class BaseException extends RuntimeException{
    private static final long serialVersionUID = -6069656061564298561L;

    private String errorCode;
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    public BaseException(String errorCode, List<ErrorMessage> errorMessages) {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }
}
