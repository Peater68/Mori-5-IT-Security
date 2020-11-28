package com.mori5.itsecurity.service;



import com.mori5.itsecurity.api.model.LoginRequestDTO;
import com.mori5.itsecurity.api.model.LoginResponseDTO;
import com.mori5.itsecurity.api.model.PasswordChangeDTO;
import com.mori5.itsecurity.api.model.TokensDTO;

import java.io.IOException;
import java.util.List;

public interface AuthenticationService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTODTO);

    void changePasswordUser(PasswordChangeDTO passwordChangeDTO);

    TokensDTO refreshToken(String refreshToken);

    List<String> getRoles();

    void setPasswordForUser(String userId, PasswordChangeDTO passwordChangeDTO);
}
