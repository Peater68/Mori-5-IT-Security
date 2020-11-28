package com.mori5.itsecurity.errorhandling.domain;

import java.io.Serializable;

public class ItSecurityErrors implements Serializable {


    protected final String key;
    protected final int code;

    private ItSecurityErrors(String key, int code) {
        this.key = key;
        this.code = code;
    }

    public String getKey() {
        return this.key;
    }

    public int getCode() {
        return this.code;
    }

    public static final ItSecurityErrors CONFLICT = new ItSecurityErrors("CONFLICT", 1000);
    public static final ItSecurityErrors ACCESS_DENIED = new ItSecurityErrors("ACCESS_DENIED", 1002);
    public static final ItSecurityErrors INVALID_OPERATION = new ItSecurityErrors("INVALID_OPERATION", 1003);
    public static final ItSecurityErrors FAILED_OPERATION = new ItSecurityErrors("FAILED_OPERATION", 1004);
    public static final ItSecurityErrors INVALID_PASSWORD = new ItSecurityErrors("INVALID_PASSWORD", 1005);
    public static final ItSecurityErrors UNKNOWN = new ItSecurityErrors("UNKNOWN", 1007);
    public static final ItSecurityErrors ENTITY_NOT_FOUND = new ItSecurityErrors("ENTITY_NOT_FOUND", 1008);
    public static final ItSecurityErrors UNAUTHORIZED = new ItSecurityErrors("UNAUTHORIZED", 1009);
    public static final ItSecurityErrors UNPROCESSABLE_ENTITY = new ItSecurityErrors("UNAUTHORIZED", 1010);
    public static final ItSecurityErrors USER_BANNED = new ItSecurityErrors("USER_BANNED", 1023);
    public static final ItSecurityErrors PARSING_ERROR = new ItSecurityErrors("PARSING_ERROR", 1024);
    public static final ItSecurityErrors FILE_UPLOAD_ERROR = new ItSecurityErrors("FILE_UPLOAD_ERROR", 1025);
    public static final ItSecurityErrors PREVIEW_CREATING_ERROR = new ItSecurityErrors("PREVIEW_CREATING_ERROR", 1025);

}
