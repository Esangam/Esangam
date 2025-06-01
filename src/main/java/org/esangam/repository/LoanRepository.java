package org.esangam.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Loan;

@ApplicationScoped
public class LoanRepository implements PanacheRepository<Loan> {
}
