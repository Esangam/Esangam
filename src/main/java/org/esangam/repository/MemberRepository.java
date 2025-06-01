package org.esangam.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Member;

@ApplicationScoped
public class MemberRepository implements PanacheRepository<Member>{

}
