package com.nguyen.master.NguyenMaster.core.exceptions.rest;

import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;

import java.util.List;

public class Error500Exception extends BaseException{
    private static final long serialVersionUID = -3175371690919652017L;

    public Error500Exception(String errorCode, List<ErrorMessage> errorMessages) {
        super(errorCode, errorMessages);
    }
}
