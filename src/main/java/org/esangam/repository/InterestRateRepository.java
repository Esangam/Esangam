package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.InterestRate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 * Repository for InterestRate entity.
 */
@ApplicationScoped
public class InterestRateRepository implements PanacheRepository<InterestRate> {

    public InterestRate findBySociety(Long societyId) {
        return find("society.id", societyId).firstResult();
    }
}
