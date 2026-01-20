package com.tcs.serviceimpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.dto.EMIPaymentRequestDTO;
import com.tcs.dto.LoanApplyRequestDTO;
import com.tcs.dto.LoanResponseDTO;
import com.tcs.entity.Account;
import com.tcs.entity.Loan;
import com.tcs.entity.Transaction;
import com.tcs.entity.User;
import com.tcs.entity.values.AdminStatus;
import com.tcs.entity.values.LoanStatus;
import com.tcs.entity.values.LoanType;
import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;
import com.tcs.entity.values.TransactionType;
import com.tcs.exceptions.AccountBlockedException;
import com.tcs.exceptions.AccountNotFoundException;
import com.tcs.exceptions.InvalidEMIAmountException;
import com.tcs.exceptions.InvalidLoanTypeException;
import com.tcs.exceptions.LoanClosedException;
import com.tcs.exceptions.LoanNotFoundException;
import com.tcs.exceptions.UnauthorizedAccessException;
import com.tcs.exceptions.UserNotFoundException;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.LoanRepository;
import com.tcs.repository.TransactionRepository;
import com.tcs.repository.UserRepository;
import com.tcs.service.LoanService;

import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;

@Service
public class LoanServiceImpl implements LoanService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public void applyLoan(String role, int userId, LoanApplyRequestDTO loanApplyRequestDTO) {

		if (role.equals("ADMIN") || role.equals("SUPER_ADMIN")) {
			userId = loanApplyRequestDTO.getUserId();
		} else {

			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UnauthorizedAccessException("You are not authorized"));

			Account userAccount = accountRepository.findFirstByUserIdOrderByIdAsc(userId)
					.orElseThrow(() -> new AccountNotFoundException("No linked account found for the user"));

			loanApplyRequestDTO.setAccountNumber(userAccount.getAccountNumber());
		}

		Loan loan = new Loan();
		loan.setLoanNumber("LN" + new Random().nextInt(900000));

		LoanType type;
		try {
			type = LoanType.valueOf(loanApplyRequestDTO.getLoanType());
		} catch (Exception e) {
			throw new InvalidLoanTypeException("Invalid loan type: " + loanApplyRequestDTO.getLoanType());
		}
		loan.setLoanType(type);

		loan.setPrincipalAmount(loanApplyRequestDTO.getPrincipalAmount());
		loan.setInterestRate(type.getInterestRate());
		loan.setTenureMonths(loanApplyRequestDTO.getTenureMonths());
		loan.setRemainingAmount(loanApplyRequestDTO.getPrincipalAmount());
		loan.setLoanStatus(LoanStatus.PENDING);
		loan.setAdminStatus(AdminStatus.PENDING);

		if ((role.equals("ADMIN") || role.equals("SUPER_ADMIN"))) {

			loan.setUserId(loanApplyRequestDTO.getUserId());
			loan.setAccountNumber(getAccountNumberByUserId(loanApplyRequestDTO.getUserId()));
		} else if (role.equals("CUSTOMER")) {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UnauthorizedAccessException("You are not authorized"));
			loan.setUserId(user.getUserId());
			loan.setAccountNumber(getAccountNumberByUserId(user.getUserId()));
		}

		loanRepository.save(loan);
	}

	@Override
	public String approveLoan(String role, int userId, String loanId) {

		if (!(role.equals("ADMIN") || role.equals("SUPER_ADMIN"))) {
			throw new UnauthorizedAccessException("You are not accessed to loan management");
		}

		Loan loan = loanRepository.findByLoanNumber(loanId);

		if (loan.getLoanStatus() != LoanStatus.PENDING) {
			throw new InvalidLoanTypeException("Loan is already " + loan.getLoanStatus().name() + "!");
		}

		loan.setLoanStatus(LoanStatus.APPROVED);
		loan.setAdminStatus(AdminStatus.APPROVED);
		Account account = accountRepository.findByAccountNumber(loan.getAccountNumber())
				.orElseThrow(() -> new AccountNotFoundException("No linked account found for the user"));
		account.setBalance(account.getBalance() + loan.getPrincipalAmount());
		loanRepository.save(loan);
		accountRepository.save(account);

		return "Loan approved successfully for loan number: " + loan.getLoanNumber();
	}

	@Override
	public String rejectLoan(String role, int userId, String loanId) {

		Loan loan = loanRepository.findByLoanNumber(loanId);

		if (loan.getLoanStatus() != LoanStatus.PENDING) {
			throw new InvalidLoanTypeException("Loan is already " + loan.getLoanStatus().name() + "!");
		}

		loan.setLoanStatus(LoanStatus.REJECTED);
		loan.setAdminStatus(AdminStatus.REJECTED);

		loanRepository.save(loan);

		return "Loan rejected for loan number: " + loan.getLoanNumber();
	}

	@Override
	public String payEMI(String role, int userId, String loanId, EMIPaymentRequestDTO emiPaymentRequestDTO) {

		String accountNumber;

		Loan loan = loanRepository.findByLoanNumber(loanId);
		if (role.equals("ADMIN") || role.equals("SUPER_ADMIN")) {
			accountNumber = emiPaymentRequestDTO.getAccountNumber();

		} else {
			accountNumber = getAccountNumberByUserId(userId);
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UnauthorizedAccessException("You are not authorized"));

			Account userAccount = accountRepository.findFirstByUserIdOrderByIdAsc(userId)
					.orElseThrow(() -> new AccountNotFoundException("No linked account found for the user"));

			if (loan.getUserId() != userId) {
				throw new UnauthorizedAccessException("This loan is not tagged to you");
			}

			if (userAccount.getBalance() < emiPaymentRequestDTO.getAmount()) {
				throw new InvalidEMIAmountException("No enough amount in the account");

			}
		}

		if (loan == null) {
			throw new LoanNotFoundException("Loan not found with loanId: " + loanId);
		}

		if (loan.getLoanStatus() == LoanStatus.CLOSED) {
			throw new LoanClosedException("Loan is already closed");
		}
		if (loan.getAdminStatus() != AdminStatus.APPROVED) {
			throw new InvalidLoanTypeException("Loan is not approved yet. EMI payment not allowed.");
		}

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));

		if (account.getStatus() == Status.BLOCKED) {
			throw new AccountBlockedException("Account is blocked. EMI payment not allowed.");
		}

		if (emiPaymentRequestDTO.getAmount() <= 0 || emiPaymentRequestDTO.getAmount() > loan.getRemainingAmount()) {
			throw new InvalidEMIAmountException("Invalid EMI amount");
		}

		loan.setRemainingAmount(loan.getRemainingAmount() - emiPaymentRequestDTO.getAmount());
		account.setBalance(account.getBalance() - emiPaymentRequestDTO.getAmount());

		if (loan.getRemainingAmount() == 0) {
			loan.setLoanStatus(LoanStatus.CLOSED);
		}

		loanRepository.save(loan);
		accountRepository.save(account);

		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);
		transaction.setToAccount("EMI PAYMENT");
		transaction.setAmount(emiPaymentRequestDTO.getAmount());
		transaction.setTransactionType(TransactionType.EMI_PAYMENT);
		transaction.setTimestamp(LocalDateTime.now());

		// Save the transaction
		transactionRepository.save(transaction);

		return "EMI TRANSACTION SUCCESS";
	}

	@Override
	public LoanResponseDTO getLoanDetails(String role, int userId, String loanId) {

		Loan loan = loanRepository.findByLoanNumber(loanId);

		System.out.println("Loan found: " + loan);

		if (role.equals("CUSTOMER") && loan.getUserId() != userId) {
			throw new UnauthorizedAccessException("You have no access for this loan");
		}

		if (loan == null) {
			throw new LoanNotFoundException("No loan found");
		}
		System.out.println("Loan found------: " + convertToDTO(loan));
		return convertToDTO(loan);
	}

	@Override
	public List<LoanResponseDTO> getLoansByUser(String role, int user, int userId) {
		List<Loan> loans = loanRepository.findByUserId(userId);
		User userDetails = userRepository.findById(userId)
				.orElseThrow(() -> new UnauthorizedAccessException("You are not authorized"));

		if (loans.isEmpty()) {
			throw new LoanNotFoundException("No loans found for user id: " + userId);
		}

		return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<LoanResponseDTO> getPendingLoans(String role, int userId) {
		List<Loan> pendingLoans = loanRepository.findByLoanStatus(LoanStatus.PENDING);

		return pendingLoans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private LoanResponseDTO convertToDTO(Loan loan) {
		System.out.println("Loan found dto: " + loan);
		return new LoanResponseDTO(loan.getId(),loan.getLoanType(), loan.getLoanNumber(), loan.getPrincipalAmount(),
				loan.getInterestRate(), loan.getTenureMonths(), loan.getRemainingAmount(), loan.getLoanStatus().name(),
				loan.getAccountNumber());
	}

	private String getAccountNumberByUserId(int userId) {
		Account account = accountRepository.findByUserId(userId).stream().findFirst()
				.orElseThrow(() -> new RuntimeException("No account found for user"));
		return account.getAccountNumber(); // Return the account number
	}

}
