package com.loanstreet.loansservice.service;

import com.loanstreet.loansservice.model.Loan;

import java.util.List;

public interface ILoanService {
    Loan getLoanById(Long id);
    Loan saveLoan(Loan loan);
    List<Loan> getAllLoans();
}
