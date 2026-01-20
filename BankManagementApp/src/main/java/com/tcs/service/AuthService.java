package com.tcs.service;

import com.tcs.dto.AuthResponseDTO;
import com.tcs.dto.LoginRequestDTO;
import com.tcs.dto.RegisterRequestDTO;
import com.tcs.dto.UserResponseDTO;
import com.tcs.entity.User;

public interface AuthService {

	UserResponseDTO  registerUser(RegisterRequestDTO registerRequestDTO);

    AuthResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    String generateJWTToken(User user);
}
