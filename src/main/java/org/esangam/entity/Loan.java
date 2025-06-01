package org.esangam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double issuedAmount;

    private LocalDate loanIssuedDate = LocalDate.now();

    private boolean repaid;

    private LocalDate loanDueDate = setDueDate(loanIssuedDate);

    private final double dueAmount = calculateDueAmount();

    @ManyToOne
    @JoinColumn(name = "member_mobile_number", referencedColumnName = "mobileNumber")
    private Member member;

    public double calculateDueAmount() {
        long years = ChronoUnit.YEARS.between(loanIssuedDate, LocalDate.now());
        return issuedAmount * Math.pow(1.01, years);
    }

    public LocalDate setDueDate(LocalDate issueDate) {
        return issueDate.plusYears(1).minusDays(1);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return issuedAmount;
    }

    public void setAmount(double amount) {
        this.issuedAmount = amount;
    }

    public LocalDate getIssueDate() {
        return loanIssuedDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.loanIssuedDate = issueDate;
    }

    public boolean isRepaid() {
        return repaid;
    }

    public void setRepaid(boolean repaid) {
        this.repaid = repaid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double getIssuedAmount() {
        return issuedAmount;
    }

    public void setIssuedAmount(double issuedAmount) {
        this.issuedAmount = issuedAmount;
    }

    public LocalDate getLoanIssuedDate() {
        return loanIssuedDate;
    }

    public void setLoanIssuedDate(LocalDate loanIssuedDate) {
        this.loanIssuedDate = loanIssuedDate;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public LocalDate getLoanDueDate() {
        return loanDueDate;
    }

    public void setLoanDueDate(LocalDate loanDueDate) {
        this.loanDueDate = loanDueDate;
    }
}
