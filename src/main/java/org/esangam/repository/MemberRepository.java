package org.esangam.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Member;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;

/**
 * Repository for Member entity.
 */
@ApplicationScoped
public class MemberRepository implements PanacheRepository<Member> {

    public Member findByMobile(String mobile) {
        return find("mobileNumber", mobile).firstResult();
    }

    public boolean esAdminExists() {
        return count("role", "ES_ADMIN") > 0;
    }

    public List<Member> listAllSocietyAdmins() {
        return list("role", "ADMIN");
    }

    public List<Member> listMembersBySociety(Long societyId) {
        return list("society.id", societyId);
    }
}
