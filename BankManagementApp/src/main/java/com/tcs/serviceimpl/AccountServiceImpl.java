package com.tcs.serviceimpl;


import com.tcs.dto.AccountCreateRequestDTO;
import com.tcs.dto.AccountResponseDTO;
import com.tcs.entity.Account;
import com.tcs.entity.values.AccountType;
import com.tcs.entity.values.Status;
import com.tcs.exceptions.UnauthorizedAccessException;
import com.tcs.repository.AccountRepository;
import com.tcs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void createAccount(int userId, AccountCreateRequestDTO accountCreateRequestDTO) {
    	System.out.println(accountCreateRequestDTO);
        AccountType accountType = AccountType.valueOf(
                accountCreateRequestDTO.getAccountType().toUpperCase()
        );

        // Check if user already has this type of account
        boolean alreadyExists = accountRepository.existsByUserIdAndAccountType(userId, accountType);

        if (alreadyExists) {
            throw new IllegalStateException(
                "User already has an account of type: " + accountType
            );
        }

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(accountType);
        account.setUserId(userId);
        account.setBalance(accountCreateRequestDTO.getBalance());
        account.setStatus(Status.ACTIVE);

        accountRepository.save(account);
    }



    // Get account details by account number
    @Override
    public AccountResponseDTO getAccountDetails(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        
        if (!account.isPresent()) {
            throw new RuntimeException("Account not found with account number: " + accountNumber);
        }

        // Return account details as DTO
        Account acc = account.get();
        return new AccountResponseDTO(
                acc.getAccountNumber(),
                acc.getAccountType().toString(),
                acc.getBalance(),
                acc.getStatus().toString()
        );
    }

    // Block an account by account number
    @Override
    public void blockAccount(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        
        if (!account.isPresent()) {
            throw new RuntimeException("Account not found with account number: " + accountNumber);
        }

        
        Account acc = account.get();
        acc.setStatus(Status.BLOCKED);
        accountRepository.save(acc);
    }
    @Override
    public void unBlockAccount(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        
        if (!account.isPresent()) {
            throw new RuntimeException("Account not found with account number: " + accountNumber);
        }

        
        Account acc = account.get();
        acc.setStatus(Status.ACTIVE);
        accountRepository.save(acc);
    }

    // Get all accounts by user ID
    @Override
    public List<AccountResponseDTO> getAccountsByUser(int currentUser,String role,int userId) {
    	System.out.println(currentUser);
    	System.out.println(userId);
    	if(role.equals("CUSTOMER") && !(currentUser==userId)) {
    		throw new UnauthorizedAccessException("You are unauthorized for this account details");
    	}
        List<Account> accounts = accountRepository.findByUserId(userId);

        return accounts.stream()
                .map(acc -> new AccountResponseDTO(
                        acc.getAccountNumber(),
                        acc.getAccountType().toString(),
                        acc.getBalance(),
                        acc.getStatus().toString()
                ))
                .collect(Collectors.toList());
    }

    // Helper method to generate a unique account number (for simplicity, using current time in ms)
    private String generateAccountNumber() {
        return "AC" + System.currentTimeMillis();  // Account number based on current time in ms
    }

}
