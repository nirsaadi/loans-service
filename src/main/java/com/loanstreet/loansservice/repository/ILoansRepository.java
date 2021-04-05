package com.loanstreet.loansservice.repository;

import com.loanstreet.loansservice.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ILoansRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findById(Long id);
}
