package com.tcs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcs.entity.Account;
import com.tcs.entity.values.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByUserId(int userId);
	
	@Query("Select a from Account a where a.accountNumber=?1")
	Optional<Account> findByAccountNumber(String accountNumber);
	
	 @Query("SELECT a FROM Account a WHERE a.userId = ?1")
	 Optional<Account> findAccountsByUserIdCustom(int userId);
	
	 boolean existsByUserIdAndAccountType(int userId, AccountType accountType);
	 
	 Optional<Account> findFirstByUserIdOrderByIdAsc(int userId);

}