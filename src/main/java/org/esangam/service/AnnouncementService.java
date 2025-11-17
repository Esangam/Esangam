package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.Announcement;
import org.esangam.entity.Member;
import org.esangam.repository.AnnouncementRepository;

import java.util.List;

/**
 * Manages announcements inside a society.
 */
@ApplicationScoped
public class AnnouncementService {

    @Inject
    AnnouncementRepository announcementRepository;

    @Inject
    NotificationService notificationService;

    /** ADMIN posts announcement. */
    @Transactional
    public Announcement postAnnouncement(Member admin, String title, String msg) {
        Announcement a = new Announcement();
        a.setSociety(admin.getSociety());
        a.setTitle(title);
        a.setMessage(msg);
        announcementRepository.persist(a);
        notificationService.notifyAnnouncement(admin.getSociety(), title);
        return a;
    }

    public List<Announcement> listBySociety(Long societyId) {
        return announcementRepository.listBySociety(societyId);
    }
}
