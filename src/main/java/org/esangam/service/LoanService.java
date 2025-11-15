package org.esangam.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.dto.*;
import org.esangam.entity.Loan;
import org.esangam.repository.LoanRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class LoanService {

    private static final double DEFAULT_INTEREST_RATE = 1.0; // 1% annual

    @Inject
    LoanRepository loanRepository;

    public LoanResponseDto requestLoan(String memberMobile, LoanRequestDto dto) {
        Loan loan = new Loan();
        loan.setMemberMobile(memberMobile);
        loan.setRequestedAmount(dto.getRequestedAmount());
        loan.setApprovedAmount(null);
        loan.setInterestRate(DEFAULT_INTEREST_RATE);
        loan.setStatus(Loan.LoanStatus.PENDING);
        loan.setPurpose(dto.getPurpose());

        loanRepository.persist(loan);
        return toDto(loan);
    }

    public List<LoanResponseDto> getLoansForMember(String memberMobile) {
        return loanRepository.findByMemberMobile(memberMobile)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LoanResponseDto> getPendingLoans() {
        return loanRepository.findPendingLoans()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public LoanResponseDto decideLoan(Long loanId, LoanDecisionDto dto) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found with id " + loanId);
        }

        if (loan.getStatus() != Loan.LoanStatus.PENDING) {
            throw new IllegalStateException("Loan is already decided with status " + loan.getStatus());
        }

        String decision = dto.getDecision().toUpperCase();

        switch (decision) {
            case "APPROVE":
                if (dto.getApprovedAmount() == null || dto.getApprovedAmount().compareTo(BigDecimal.ONE) < 0) {
                    throw new IllegalArgumentException("Approved amount must be provided and >= 1 for APPROVE decision");
                }
                loan.setStatus(Loan.LoanStatus.APPROVED);
                loan.setApprovedAmount(dto.getApprovedAmount());
                loan.setDecidedAt(Instant.now());
                break;

            case "REJECT":
                loan.setStatus(Loan.LoanStatus.REJECTED);
                loan.setApprovedAmount(null);
                loan.setDecidedAt(Instant.now());
                break;

            default:
                throw new IllegalArgumentException("Decision must be APPROVE or REJECT");
        }

        return toDto(loan);
    }

    public LoanAdminDashboardDto getAdminDashboard(String memberMobile, Instant from, Instant to) {
        List<Loan> loans = loanRepository.queryLoans(memberMobile, from, to).list();

        LoanAdminDashboardDto dto = new LoanAdminDashboardDto();
        dto.setTotalLoans(loans.size());
        dto.setPendingLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.PENDING).count());
        dto.setApprovedLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.APPROVED).count());
        dto.setRejectedLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.REJECTED).count());

        BigDecimal totalRequested = loans.stream()
                .map(Loan::getRequestedAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalApproved = loans.stream()
                .map(Loan::getApprovedAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalRequestedAmount(totalRequested);
        dto.setTotalApprovedAmount(totalApproved);

        return dto;
    }

    public LoanMemberDashboardDto getMemberDashboard(String memberMobile, Instant from, Instant to) {
        List<Loan> loans = loanRepository.queryLoans(memberMobile, from, to).list();

        LoanMemberDashboardDto dto = new LoanMemberDashboardDto();
        dto.setMemberMobile(memberMobile);
        dto.setTotalLoans(loans.size());
        dto.setPendingLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.PENDING).count());
        dto.setApprovedLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.APPROVED).count());
        dto.setRejectedLoans(loans.stream().filter(l -> l.getStatus() == Loan.LoanStatus.REJECTED).count());

        BigDecimal totalRequested = loans.stream()
                .map(Loan::getRequestedAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalApproved = loans.stream()
                .map(Loan::getApprovedAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalRequestedAmount(totalRequested);
        dto.setTotalApprovedAmount(totalApproved);

        return dto;
    }

    public LoanPageDto listLoans(String memberMobile, Instant from, Instant to, int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 20;
        if (size > 20) size = 20;

        PanacheQuery<Loan> query = loanRepository.queryLoans(memberMobile, from, to);

        long totalItems = query.count();

        query.page(Page.of(page, size));

        List<LoanResponseDto> items = query.list()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        LoanPageDto dto = new LoanPageDto();
        dto.setPage(page);
        dto.setSize(size);
        dto.setTotalItems(totalItems);

        long totalPages = (totalItems + size - 1) / size;
        dto.setTotalPages(totalPages);
        dto.setItems(items);

        return dto;
    }

    public LoanResponseDto toDto(Loan loan) {
        LoanResponseDto dto = new LoanResponseDto();
        dto.setId(loan.getId());
        dto.setMemberMobile(loan.getMemberMobile());
        dto.setRequestedAmount(loan.getRequestedAmount());
        dto.setApprovedAmount(loan.getApprovedAmount());
        dto.setInterestRate(loan.getInterestRate());
        dto.setStatus(loan.getStatus().name());
        dto.setPurpose(loan.getPurpose());
        dto.setCreatedAt(loan.getCreatedAt());
        dto.setDecidedAt(loan.getDecidedAt());
        return dto;
    }
}
