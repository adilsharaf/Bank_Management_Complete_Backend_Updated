package com.tcs.service;

import java.util.List;

import com.tcs.dto.AccountCreateRequestDTO;
import com.tcs.dto.AccountResponseDTO;

public interface AccountService {

    void createAccount(int userId,AccountCreateRequestDTO accountCreateRequestDTO);

    AccountResponseDTO getAccountDetails(String accountNumber);

    void blockAccount(String accountNumber);
    void unBlockAccount(String accountNumber);

    List<AccountResponseDTO> getAccountsByUser(int currentUser,String role,int userId);
}