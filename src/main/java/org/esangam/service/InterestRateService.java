package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.InterestRate;
import org.esangam.entity.Society;
import org.esangam.repository.InterestRateRepository;
import org.esangam.repository.SocietyRepository;

/**
 * Manages dynamic interest rate for each society.
 */
@ApplicationScoped
public class InterestRateService {

    @Inject
    InterestRateRepository interestRateRepository;

    @Inject
    SocietyRepository societyRepository;

    @Transactional
    public InterestRate setInterestRate(Long societyId, double base, double overdue) {
        Society s = societyRepository.findById(societyId);
        InterestRate rate = interestRateRepository.findBySociety(societyId);
        if (rate == null) {
            rate = new InterestRate();
            rate.setSociety(s);
        }
        rate.setBaseRate(base);
        rate.setOverdueRate(overdue);
        interestRateRepository.persist(rate);
        return rate;
    }

    public InterestRate getInterestRate(Long societyId) {
        return interestRateRepository.findBySociety(societyId);
    }
}
