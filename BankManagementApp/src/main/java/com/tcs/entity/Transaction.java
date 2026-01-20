package com.tcs.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tcs.entity.values.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;
	private String fromAccount;
	private TransactionType transactionType;
	private double amount;
	private LocalDateTime timestamp;
	private String toAccount;
	
	public Transaction() {
		super();
		
	}
	public Transaction(TransactionType transactionType, double amount, LocalDateTime timestamp,
			String fromAccount,String toAccount) {
		super();
		
		this.transactionType = transactionType;
		this.amount = amount;
		this.timestamp = timestamp;
		this.fromAccount=fromAccount;
		this.toAccount=toAccount;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	
	
	
	

}
