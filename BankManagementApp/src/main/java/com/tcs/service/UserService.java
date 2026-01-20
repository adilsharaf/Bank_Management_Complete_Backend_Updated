package com.tcs.service;

import java.util.List;

import com.tcs.dto.RegisterRequestDTO;
import com.tcs.dto.UserResponseDTO;
import com.tcs.exceptions.UnauthorizedAccessException;

public interface UserService {

    void createAdmin(RegisterRequestDTO registerRequestDTO);

    void blockUser(int userId);

    void unblockUser(int userId);

    List<UserResponseDTO> getAllUsers() ;
}
