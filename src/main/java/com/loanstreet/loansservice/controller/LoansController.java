package com.loanstreet.loansservice.controller;

import com.loanstreet.loansservice.exception.EntityNotFoundException;
import com.loanstreet.loansservice.exception.MandatoryArgumentsMissingException;
import com.loanstreet.loansservice.model.Loan;
import com.loanstreet.loansservice.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class LoansController {

    @Autowired
    ILoanService loanService;

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans(){
        return Optional
                .ofNullable(loanService.getAllLoans())
                .map(loans -> ResponseEntity.ok().body(loans))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id){
        return Optional
                .ofNullable(loanService.getLoanById(id))
                .map(loan -> ResponseEntity.ok().body(loan))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/loans")
    public ResponseEntity<Loan> create(@Valid @RequestBody Loan loan){
        /**
         * Since I'm using post/put to create/update -
         * I'm clearing ID upon "create" call to make sure it won't override existing loan
         *
         * This can be done in other ways and it depends on how the clients use this API
         * and what is the standard we allow in the API when it comes to POST/PUT calls
         **/
        loan.setId(null);
        Loan savedLoan = loanService.saveLoan(loan);

        URI location = URI.create(String.format("/loans/%s", savedLoan.getId()));

        return ResponseEntity.created(location).body(savedLoan);
    }

    @PutMapping("/loans")
    public ResponseEntity<?> update(@Valid @RequestBody Loan loan){
        if (Objects.isNull(loan.getId()))
            throw new MandatoryArgumentsMissingException(new String[]{"ID"});
        else if (Objects.isNull(loanService.getLoanById(loan.getId())))
            throw new EntityNotFoundException(String.valueOf(loan.getId()));

        loanService.saveLoan(loan);

        return ResponseEntity.ok().build();
    }
}
