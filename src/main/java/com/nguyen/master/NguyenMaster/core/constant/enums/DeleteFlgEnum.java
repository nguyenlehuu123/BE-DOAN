package com.nguyen.master.NguyenMaster.core.constant.enums;

public enum DeleteFlgEnum {
    ACTIVE(0, "Active"),
    DELETE(1, "Delete");

    public static DeleteFlgEnum valueOfCode(int code) {
        for (DeleteFlgEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }


    private final Integer code;
    private final String content;

    DeleteFlgEnum(Integer code, String content) {
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
