package com.tcs.entity.values;


public enum LoanType {
	PERSONAL(10.5), HOME(8.2), VEHICLE(9.0), EDUCATION(6.1), BUSINESS(12.5), GOLD(7.0), PROPERTY(9.5), AGRICULTURE(5.2),
	CONSUMER(12.3);

	private final double interestRate;

	LoanType(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getInterestRate() {
		return interestRate;
	}

}

