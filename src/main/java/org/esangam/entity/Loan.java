package org.esangam.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a loan taken by a member.
 * Uses Simple Interest:
 * FinalAmount = Principal + (Principal * (rate/100) * years)
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Member who requested the loan. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mobile", nullable = false)
    private Member member;

    /** Principal loan amount. */
    @Column(nullable = false)
    private double amount;

    /** Base rate at the time of approval (percentage). */
    @Column(nullable = false)
    private double baseRate;

    /** Overdue rate (percentage). */
    @Column(nullable = false)
    private double overdueRate;

    /** Loan status: REQUESTED, APPROVED, REJECTED. */
    @Column(nullable = false)
    private String status;

    /** Date on which ADMIN approved the loan. */
    private LocalDate approvalDate;

    /** Due date = approvalDate + 1 year. */
    private LocalDate dueDate;

    public Long getId() { return id; }

    public Member getMember() { return member; }

    public void setMember(Member member) { this.member = member; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public double getBaseRate() { return baseRate; }

    public void setBaseRate(double baseRate) { this.baseRate = baseRate; }

    public double getOverdueRate() { return overdueRate; }

    public void setOverdueRate(double overdueRate) { this.overdueRate = overdueRate; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public LocalDate getApprovalDate() { return approvalDate; }

    public void setApprovalDate(LocalDate approvalDate) { this.approvalDate = approvalDate; }

    public LocalDate getDueDate() { return dueDate; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    /** Calculates simple interest for 1 year with base rate. */
    public double calculateInterestBeforeDue() {
        if (approvalDate == null) return 0;
        double years = 1.0;
        return amount * (baseRate / 100.0) * years;
    }

    /** Calculates overdue interest roughly based on days after due date. */
    public double calculateOverdueInterest() {
        if (dueDate == null) return 0;
        LocalDate today = LocalDate.now();
        if (!today.isAfter(dueDate)) return 0;
        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
        double yearsDelayed = daysLate / 365.0;
        return amount * (overdueRate / 100.0) * yearsDelayed;
    }

    /** Total final amount payable. */
    public double getFinalAmount() {
        return amount + calculateInterestBeforeDue() + calculateOverdueInterest();
    }
}
