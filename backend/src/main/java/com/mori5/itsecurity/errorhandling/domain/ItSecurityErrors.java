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
    public static final ItSecurityErrors UNAVAILABLE = new ItSecurityErrors("UNAVAILABLE", 4800);
    public static final ItSecurityErrors INVALID_PASSWORD = new ItSecurityErrors("INVALID_PASSWORD", 1005);
    public static final ItSecurityErrors UNKNOWN = new ItSecurityErrors("UNKNOWN", 1007);
    public static final ItSecurityErrors ENTITY_NOT_FOUND = new ItSecurityErrors("ENTITY_NOT_FOUND", 1008);
    public static final ItSecurityErrors UNAUTHORIZED = new ItSecurityErrors("UNAUTHORIZED", 1009);
    public static final ItSecurityErrors USER_INFORMATION_NOT_EXISTS = new ItSecurityErrors("USER_INFORMATION_NOT_EXISTS", 1022);
}
