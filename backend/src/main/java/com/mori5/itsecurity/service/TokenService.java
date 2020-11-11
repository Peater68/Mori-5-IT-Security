package com.mori5.itsecurity.service;


import com.mori5.itsecurity.domain.Role;

public interface TokenService {

    String generateAccessToken(String email, String id, Role role);

    String generateRefreshToken(String email, String id, Role role);

    String validateRefreshToken(String refreshToken);
}
