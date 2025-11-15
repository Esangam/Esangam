package org.esangam.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Member;

@ApplicationScoped
public class MemberRepository implements PanacheRepositoryBase<Member, String> {

    public Member findByMobileNumber(String mobileNumber) {
        return find("mobileNumber", mobileNumber).firstResult();
    }
}
