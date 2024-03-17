package com.nguyen.master.NguyenMaster.core.exceptions.rest;

import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;

import java.util.List;

public class Error400Exception extends BaseException {
    private static final long serialVersionUID = -6069656061564298561L;

    public Error400Exception(String errorCode, List<ErrorMessage> errorMessages) {
        super(errorCode, errorMessages);
    }
}
