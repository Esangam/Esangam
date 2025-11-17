package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Announcement;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

/**
 * Repository to manage announcements.
 */
@ApplicationScoped
public class AnnouncementRepository implements PanacheRepository<Announcement> {

    public List<Announcement> listBySociety(Long societyId) {
        return list("society.id", societyId);
    }
}
