package org.esangam.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Represents interest rate configuration for a society.
 * ADMIN controls both:
 * - base interest rate
 * - overdue interest rate (applied after the loan due date)
 */
@Entity
@Table(name = "interest_rate")
public class InterestRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Base interest rate applied from approval until due date (1 year). */
    @Column(nullable = false)
    private double baseRate;

    /** Overdue interest rate applied after the due date. */
    @Column(nullable = false)
    private double overdueRate;

    /** One-to-one relationship with Society. */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "society_id", nullable = false, unique = true)
    private Society society;

    public Long getId() { return id; }

    public double getBaseRate() { return baseRate; }

    public void setBaseRate(double baseRate) { this.baseRate = baseRate; }

    public double getOverdueRate() { return overdueRate; }

    public void setOverdueRate(double overdueRate) { this.overdueRate = overdueRate; }

    public Society getSociety() { return society; }

    public void setSociety(Society society) { this.society = society; }
}
