package com.tcs.service;

import java.util.List;

import com.tcs.dto.EMIPaymentRequestDTO;
import com.tcs.dto.LoanApplyRequestDTO;
import com.tcs.dto.LoanResponseDTO;


public interface LoanService {

    void applyLoan(String role,int userId,LoanApplyRequestDTO loanApplyRequestDTO);

    String approveLoan(String role,int userId,String loanId);

    String rejectLoan(String role,int userId,String loanId);

    String payEMI(String role,int userId,String loanId,EMIPaymentRequestDTO emiPaymentRequestDTO);

    LoanResponseDTO getLoanDetails(String role,int userId,String loanId);

    List<LoanResponseDTO> getLoansByUser(String role,int user,int userId);

    List<LoanResponseDTO> getPendingLoans(String role,int userId);
}
