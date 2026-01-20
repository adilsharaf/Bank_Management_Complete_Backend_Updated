package com.tcs.service;

import java.util.List;

import com.tcs.dto.DepositRequestDTO;
import com.tcs.dto.TransactionRequestDTO;
import com.tcs.dto.TransactionResponseDTO;
import com.tcs.dto.WithdrawRequestDTO;
import com.tcs.entity.Transaction;

public interface TransactionService {

    void transfer(String role,int userId,TransactionRequestDTO dto);

    void deposit(String role,DepositRequestDTO dto);

    void withdraw(String role,int userId,WithdrawRequestDTO dto);

    List<Transaction> history(String role,int userId,String accountNumber);
}