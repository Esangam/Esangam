package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.InterestRate;
import org.esangam.entity.Loan;
import org.esangam.entity.Member;
import org.esangam.repository.InterestRateRepository;
import org.esangam.repository.LoanRepository;
import org.esangam.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Manages loan requests, approvals and interest calculation.
 */
@ApplicationScoped
public class LoanService {

    @Inject
    LoanRepository loanRepository;

    @Inject
    InterestRateRepository interestRateRepository;

    @Inject
    MemberRepository memberRepository;

    @Inject
    NotificationService notificationService;

    /** Member requests a loan. */
    @Transactional
    public Loan requestLoan(String memberMobile, double amount) {
        Member member = memberRepository.findByMobile(memberMobile);

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setAmount(amount);
        loan.setStatus("REQUESTED");
        loan.setBaseRate(0);
        loan.setOverdueRate(0);

        loanRepository.persist(loan);

        notificationService.notifyAdminOfLoanRequest(member);

        return loan;
    }

    /** ADMIN approves a loan. */
    @Transactional
    public Loan approveLoan(Long loanId, Member admin) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            return null;
        }

        InterestRate rate = interestRateRepository.findBySociety(
                admin.getSociety().getId()
        );

        if (rate == null) {
            loan.setBaseRate(0);
            loan.setOverdueRate(0);
        } else {
            loan.setBaseRate(rate.getBaseRate());
            loan.setOverdueRate(rate.getOverdueRate());
        }

        loan.setStatus("APPROVED");
        loan.setApprovalDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusYears(1));

        loanRepository.persist(loan);

        notificationService.notifyLoanApproved(loan);

        return loan;
    }

    /** ADMIN rejects a loan. */
    @Transactional
    public Loan rejectLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            return null;
        }
        loan.setStatus("REJECTED");
        loanRepository.persist(loan);
        notificationService.notifyLoanRejected(loan);
        return loan;
    }

    /** List all loan requests under a society. */
    public List<Loan> listPendingLoans(Long societyId) {
        return loanRepository.listPendingBySociety(societyId);
    }

    /** List all loans for a member. */
    public List<Loan> listMemberLoans(String mobile) {
        return loanRepository.listByMember(mobile);
    }
}
