package com.tcs.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.tcs.dto.DepositRequestDTO;
import com.tcs.dto.TransactionRequestDTO;
import com.tcs.dto.WithdrawRequestDTO;
import com.tcs.entity.Account;
import com.tcs.entity.Transaction;
import com.tcs.entity.User;
import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;
import com.tcs.entity.values.TransactionType;
import com.tcs.exceptions.AccountBlockedException;
import com.tcs.exceptions.AccountNotFoundException;
import com.tcs.exceptions.UnauthorizedAccessException;
import com.tcs.exceptions.UserNotFoundException;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.TransactionRepository;
import com.tcs.repository.UserRepository;
import com.tcs.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(UserRepository userRepository,AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository=userRepository;
    }

    @Override
    public void transfer(String role,int userId,TransactionRequestDTO dto) {
    	
    	if ("ADMIN".equals(role) || "SUPERADMIN".equals(role)) {
    		Account from = accountRepository.findByAccountNumber(dto.getFromAccountNumber())
                    .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
    		
    		
            Account to = accountRepository.findByAccountNumber(dto.getToAccountNumber())
                    .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));
        
            from.setBalance(from.getBalance() - dto.getAmount());
            to.setBalance(to.getBalance() + dto.getAmount());

            accountRepository.save(from);
            accountRepository.save(to);

            saveTransaction(dto.getFromAccountNumber(), dto.getToAccountNumber(), dto.getAmount(), TransactionType.TRANSFER);
            return;
    	}
    	
    	
    	User user=userRepository.findById(userId).orElseThrow(
    			()->new UnauthorizedAccessException("You are not authorized"));
    	
        Account from = accountRepository.findByAccountNumber(dto.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        
        if (from.getUserId() != userId) {
            throw new UnauthorizedAccessException("You are not authorized to access this account");
        }
        
        Account to = accountRepository.findByAccountNumber(dto.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        validate(from, dto.getAmount());

        from.setBalance(from.getBalance() - dto.getAmount());
        to.setBalance(to.getBalance() + dto.getAmount());

        accountRepository.save(from);
        accountRepository.save(to);

        saveTransaction(dto.getFromAccountNumber(), dto.getToAccountNumber(), dto.getAmount(), TransactionType.TRANSFER);
    }

    @Override
    public void deposit(String role,DepositRequestDTO dto) {
    	
    	
    	
        Account acc = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        
        acc.setBalance(acc.getBalance() + dto.getAmount());
        
        accountRepository.save(acc);

        saveTransaction(null, dto.getAccountNumber(), dto.getAmount(), TransactionType.DEPOSIT);
        return;
    }

    @Override
    public void withdraw(String role,int userId,WithdrawRequestDTO dto) {
    	
    	if ("ADMIN".equals(role) || "SUPERADMIN".equals(role)) {
    		Account acc = accountRepository.findByAccountNumber(dto.getAccountNumber())
                    .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    		
    		validate(acc, dto.getAmount());
    		
    		acc.setBalance(acc.getBalance() - dto.getAmount());
            accountRepository.save(acc);

            saveTransaction(dto.getAccountNumber(), null, dto.getAmount(), TransactionType.WITHDRAW);
            return;
    	}
    	
    	User user=userRepository.findById(userId).orElseThrow(
    			()->new UnauthorizedAccessException("You are not authorized"));
    	
    	
        Account acc = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (acc.getUserId() != userId) {
            throw new UnauthorizedAccessException("You are not authorized to access this account");
        }
        
        validate(acc, dto.getAmount());

        acc.setBalance(acc.getBalance() - dto.getAmount());
        accountRepository.save(acc);

        saveTransaction(dto.getAccountNumber(), null, dto.getAmount(), TransactionType.WITHDRAW);
    }

    @Override
    public List<Transaction> history(String role,int userId,String accountNumber) {
    	
    	
    	if ("ADMIN".equals(role) || "SUPERADMIN".equals(role)) {
            return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
        }
    	
    	User user=userRepository.findById(userId).orElseThrow(
    			()->new UnauthorizedAccessException("You are not authorized"));
    	
    	Account acc = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    	
    	if(acc.getUserId()!=userId) {
    		throw new UnauthorizedAccessException("You are not authorized to access this account");
    	}
    	
        return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
    }

    private void validate(Account acc, Double amount) {
    	
    	
        if (acc.getStatus() != Status.ACTIVE)
            throw new AccountBlockedException("Account is blocked");

        if (amount <= 0)
            throw new RuntimeException("Invalid amount");

        if (acc.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");
    }

    private void saveTransaction(String from, String to, Double amount, TransactionType type) {
        Transaction txn = new Transaction();
        txn.setFromAccount(from);
        txn.setToAccount(to);
        txn.setAmount(amount);
        txn.setTransactionType(type);
        txn.setTimestamp(LocalDateTime.now());
        
        
//        if (type == TransactionType.DEPOSIT) {
//            txn.setToAccount(to);  // For deposits, set toAccount as account_number
//        } else if (type == TransactionType.WITHDRAW) {
//            txn.setFromAccount(from);  // For withdrawals, set fromAccount as account_number
//        } else if (type == TransactionType.TRANSFER) {
//            txn.setFromAccount(from);  // For transfers, set fromAccount as account_number
//        }
        transactionRepository.save(txn);
    }
}

