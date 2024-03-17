package com.nguyen.master.NguyenMaster.core.exceptions.rest;

public class UnknownException extends RuntimeException{
    private static final long serialVersionUID = 6620520008237236930L;
    public UnknownException(String message) {
        super(message);
    }
}
