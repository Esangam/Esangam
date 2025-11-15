package org.esangam.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Loan;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class LoanRepository implements PanacheRepositoryBase<Loan, Long> {

    public List<Loan> findByMemberMobile(String mobile) {
        return list("memberMobile", mobile);
    }

    public List<Loan> findPendingLoans() {
        return list("status", Loan.LoanStatus.PENDING);
    }

    public PanacheQuery<Loan> queryLoans(String memberMobile, Instant from, Instant to) {
        StringBuilder jpql = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (memberMobile != null && !memberMobile.isBlank()) {
            jpql.append(" and memberMobile = :mobile");
            params.put("mobile", memberMobile);
        }
        if (from != null) {
            jpql.append(" and createdAt >= :from");
            params.put("from", from);
        }
        if (to != null) {
            jpql.append(" and createdAt <= :to");
            params.put("to", to);
        }

        return find(jpql.toString(), params);
    }
}
