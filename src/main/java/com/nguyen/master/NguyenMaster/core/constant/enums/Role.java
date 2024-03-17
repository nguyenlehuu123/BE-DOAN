package com.nguyen.master.NguyenMaster.core.constant.enums;

public enum Role {
    USER(1, "User"),
    AUTHOR(2, "Author"),
    ADMIN(3, "Admin");

    public static Role valueOfCode(int code) {
        for (Role e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    private final Integer code;
    private final String content;

    Role(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public Integer getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }
}
