package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.esangam.entity.Society;
import org.esangam.repository.SocietyRepository;

import java.util.List;

/**
 * Society management service used by ES_ADMIN.
 */
@ApplicationScoped
public class SocietyService {

    @Inject
    SocietyRepository societyRepository;

    public List<Society> listAll() {
        return societyRepository.listAll();
    }

    public Society findById(Long id) {
        return societyRepository.findById(id);
    }
}
