package com.tcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Loan;
import com.tcs.entity.values.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

	List<Loan> findByLoanStatus(LoanStatus status);
	
	Loan findByLoanNumber(String number);
	List<Loan> findByUserId(int userId);
}
