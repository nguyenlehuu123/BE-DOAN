package com.nguyen.master.NguyenMaster.core.exceptions.rest;

public class SqlException extends RuntimeException{
    private static final long serialVersionUID = 6620520008237236930L;
    public SqlException(String message) {
        super(message);
    }
}
