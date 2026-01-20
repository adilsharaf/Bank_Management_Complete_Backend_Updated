package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


public class LoanApplyRequestDTO {

    private String loanType;
    private Double principalAmount;
    private Integer tenureMonths;
    private int userId;
    private String accountNumber;
	public LoanApplyRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoanApplyRequestDTO(String loanType, Double principalAmount, Integer tenureMonths, int userId,
			String accountNumber) {
		super();
		this.loanType = loanType;
		this.principalAmount = principalAmount;
		this.tenureMonths = tenureMonths;
		this.userId = userId;
		this.accountNumber = accountNumber;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public Double getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(Double principalAmount) {
		this.principalAmount = principalAmount;
	}
	public Integer getTenureMonths() {
		return tenureMonths;
	}
	public void setTenureMonths(Integer tenureMonths) {
		this.tenureMonths = tenureMonths;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
    
    
    
}
