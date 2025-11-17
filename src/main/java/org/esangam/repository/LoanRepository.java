package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Loan;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

/**
 * Repository for Loan entity.
 */
@ApplicationScoped
public class LoanRepository implements PanacheRepository<Loan> {

    public List<Loan> listByMember(String memberMobile) {
        return list("member.mobileNumber", memberMobile);
    }

    public List<Loan> listPendingBySociety(Long societyId) {
        return list("status = ?1 and member.society.id = ?2", "REQUESTED", societyId);
    }

    public List<Loan> listBySociety(Long societyId) {
        return list("member.society.id", societyId);
    }
}
