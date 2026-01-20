package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.tcs.entity.values.LoanType;

import lombok.AllArgsConstructor;

public class LoanResponseDTO {

	private int loanId;
	private LoanType loanType;
	private String loanNumber;
	private Double principalAmount;
	private Double interestRate;
	private Integer tenureMonths;
	private Double remainingAmount;
	private String status;
	private String accountNumber;

	public LoanResponseDTO(int loanId, LoanType loanType,String loanNumber, Double principalAmount, Double interestRate,
			Integer tenureMonths, Double remainingAmount, String status, String accountNumber) {
		super();
		this.loanId = loanId;
		this.loanType = loanType;
		this.loanNumber=loanNumber;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.tenureMonths = tenureMonths;
		this.remainingAmount = remainingAmount;
		this.status = status;
		this.accountNumber = accountNumber;
	}

	public int getLoanId() {
		return loanId;
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(Double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureMonths(Integer tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
