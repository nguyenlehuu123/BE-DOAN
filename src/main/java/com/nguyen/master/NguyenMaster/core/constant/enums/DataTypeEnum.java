package com.nguyen.master.NguyenMaster.core.constant.enums;

public enum DataTypeEnum {
    STRING(1, "STRING"),
    INTEGER(2, "INTEGER"),
    FLOAT(3, "FLOAT"),
    DATE(4, "DATE"),
    TIMESTAMP(5, "TIMESTAMP");

    public static DataTypeEnum valueOfCode(int code) {
        for (DataTypeEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    private final Integer code;
    private final String content;

    public Integer getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    DataTypeEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }
}
