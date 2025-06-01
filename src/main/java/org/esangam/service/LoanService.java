package org.esangam.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.Loan;
import org.esangam.entity.Member;
import org.esangam.repository.LoanRepository;
import org.esangam.repository.MemberRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class LoanService {

    LoanRepository loanRepository;
    MemberRepository memberRepository;

    @Inject
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void addLoan(Loan loan){
        Member member = memberRepository.findById(loan.getMember().getMobileNumber());

        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }

        loan.setMember(member); // attach managed Member

        loanRepository.persist(loan);
    }

    public List<Loan> getAllLoans(){
        return loanRepository.listAll();
    }
}
