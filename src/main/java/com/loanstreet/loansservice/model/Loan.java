package com.loanstreet.loansservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="LOANS")
@EntityListeners(AuditingEntityListener.class)
public class Loan {
    @Id
    @GeneratedValue
    @Column(name="id")
    Long id;

    @Column(name="amount")
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    BigDecimal amount;

    @Column(name="interestRate")
    @NotNull
    @DecimalMin(value = "0")
    BigDecimal interestRate;

    @Column(name="lengthOfLoan")
    @NotNull
    @Min(value = 1)
    Integer lengthOfLoan;

    @Column(name="monthlyPaymentAmount")
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    BigDecimal monthlyPaymentAmount;

    @Column(name="createdDate", updatable = false, nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @CreationTimestamp
    LocalDateTime createdDate;

    @Column(name="lastUpdateDate")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @UpdateTimestamp
    LocalDateTime lastUpdateDate;
}
