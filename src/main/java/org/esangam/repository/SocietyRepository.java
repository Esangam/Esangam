package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Society;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 * Repository for Society entity.
 */
@ApplicationScoped
public class SocietyRepository implements PanacheRepository<Society> {

    public Society findByName(String name) {
        return find("name", name).firstResult();
    }
}
