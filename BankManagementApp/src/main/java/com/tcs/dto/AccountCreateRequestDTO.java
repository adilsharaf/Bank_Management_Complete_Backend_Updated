package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


public class AccountCreateRequestDTO {

    private String accountType; // SAVINGS / CURRENT
    private Long userId;
    private double balance;
    
	public AccountCreateRequestDTO() {
		super();
		
	}
	public AccountCreateRequestDTO(String accountType, Long userId,double balance) {
		super();
		this.accountType = accountType;
		this.balance=balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
    
    
    
}
