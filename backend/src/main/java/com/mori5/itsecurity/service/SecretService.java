package com.mori5.itsecurity.service;

import io.jsonwebtoken.SigningKeyResolver;

public interface SecretService {

    SigningKeyResolver getSigningKeyResolver();

    byte[] getHS512SecretBytes();

}
