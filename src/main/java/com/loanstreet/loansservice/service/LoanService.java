package com.loanstreet.loansservice.service;

import com.loanstreet.loansservice.model.Loan;
import com.loanstreet.loansservice.repository.ILoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService implements ILoanService{

    @Autowired
    ILoansRepository loansRepository;

    public List<Loan> getAllLoans(){
        return loansRepository.findAll();
    }

    public Loan getLoanById(Long id){
        return loansRepository.findById(id).orElse(null);
    }

    public Loan saveLoan(Loan loan){
        return loansRepository.save(loan);
    }
}
