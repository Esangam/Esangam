package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Notification;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

/**
 * Repository for Notification entity.
 */
@ApplicationScoped
public class NotificationRepository implements PanacheRepository<Notification> {

    public List<Notification> listUnread(String memberMobile) {
        return list("member.mobileNumber = ?1 and readFlag = false", memberMobile);
    }
}
