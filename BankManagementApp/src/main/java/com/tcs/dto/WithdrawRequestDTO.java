package com.tcs.dto;

public class WithdrawRequestDTO {
    public String accountNumber;
    public Double amount;
    
    public WithdrawRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WithdrawRequestDTO(String accountNumber, Double amount) {
		super();
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}