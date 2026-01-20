package com.tcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.dto.TransactionResponseDTO;
import com.tcs.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	 List<Transaction> findByFromAccount(String fromAccount);
	   List<Transaction> findByToAccount(String toAccount);

	 List<Transaction> findByFromAccountOrToAccount(String from, String to);
}