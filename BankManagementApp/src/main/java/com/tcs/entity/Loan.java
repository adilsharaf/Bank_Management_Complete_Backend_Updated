package com.tcs.entity;

import com.tcs.entity.values.AdminStatus;
import com.tcs.entity.values.LoanStatus;
import com.tcs.entity.values.LoanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@Column(unique=true,nullable=false)
	private String loanNumber;
	
	private LoanType loanType;
	private double principalAmount;
	private double interestRate;;
	private int tenureMonths; 
	private double remainingAmount;
	private LoanStatus loanStatus;
	private int userId;
	private String accountNumber;
	private AdminStatus adminStatus;
	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Loan( String loanNumber, LoanType loanType, double principalAmount, double interestRate,
			int tenureMonths, double remainingAmount, LoanStatus loanStatus, int userId, String accountNumber,
			AdminStatus adminStatus) {
		super();
		this.loanNumber = loanNumber;
		this.loanType = loanType;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.tenureMonths = tenureMonths;
		this.remainingAmount = remainingAmount;
		this.loanStatus = loanStatus;
		this.userId = userId;
		this.accountNumber = accountNumber;
		this.adminStatus = adminStatus;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public double getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public int getTenureMonths() {
		return tenureMonths;
	}
	public void setTenureMonths(int tenureMonths) {
		this.tenureMonths = tenureMonths;
	}
	public double getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public LoanStatus getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
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
	public AdminStatus getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(AdminStatus adminStatus) {
		this.adminStatus = adminStatus;
	}
	@Override
	public String toString() {
		return "Loan [Id=" + Id + ", loanNumber=" + loanNumber + ", loanType=" + loanType + ", principalAmount="
				+ principalAmount + ", interestRate=" + interestRate + ", tenureMonths=" + tenureMonths
				+ ", remainingAmount=" + remainingAmount + ", loanStatus=" + loanStatus + ", userId=" + userId
				+ ", accountNumber=" + accountNumber + ", adminStatus=" + adminStatus + "]";
	}
	
	
}
