package com.mori5.itsecurity.domain;

import com.mori5.itsecurity.security.AuthoritiesConstants;

public enum Role {
    ADMIN(AuthoritiesConstants.ROLE_ADMIN),
    CUSTOMER(AuthoritiesConstants.ROLE_CUSTOMER);

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
