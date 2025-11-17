package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.Member;
import org.esangam.entity.Society;
import org.esangam.repository.MemberRepository;
import org.esangam.repository.SocietyRepository;

import java.util.List;

/**
 * Handles creation and retrieval of members including ES_ADMIN, ADMIN, and MEMBER.
 */
@ApplicationScoped
public class MemberService {

    @Inject
    MemberRepository memberRepository;

    @Inject
    SocietyRepository societyRepository;

    /**
     * Creates ES_ADMIN if not exists.
     */
    @Transactional
    public Member createEsangamAdminIfNotExists() {
        if (memberRepository.esAdminExists()) {
            return memberRepository.find("role", ONE).firstResult();
        }

        Member admin = new Member();
        admin.setMobileNumber(ES_ADMIN);
        admin.setFirstName("Esangam");
        admin.setLastName("Admin");
        admin.setFullName("Esangam Admin");
        admin.setPassword(ONE_PASSWORD);
        admin.setRole(ONE);

        memberRepository.persist(admin);
        return admin;
    }

    /**
     * Creates a society with an ADMIN user.
     */
    @Transactional
    public Member createSocietyAdmin(String name, String description,
                                     String adminMobile, String firstName,
                                     String lastName, String password) {

        Society s = new Society();
        s.setName(name);
        s.setDescription(description);
        societyRepository.persist(s);

        Member m = new Member();
        m.setMobileNumber(adminMobile);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setFullName(firstName + " " + lastName);
        m.setPassword(password);
        m.setRole(TWO);
        m.setSociety(s);

        memberRepository.persist(m);
        return m;
    }

    /**
     * Creates a member under an ADMIN's society.
     */
    @Transactional
    public Member createMemberUnderAdmin(Long societyId,
                                         String mobile,
                                         String fname,
                                         String lname,
                                         String password) {

        Society s = societyRepository.findById(societyId);

        Member m = new Member();
        m.setMobileNumber(mobile);
        m.setFirstName(fname);
        m.setLastName(lname);
        m.setFullName(fname + " " + lname);
        m.setPassword(password);
        m.setRole(THREE);
        m.setSociety(s);

        memberRepository.persist(m);
        return m;
    }

    public Member findByMobile(String mobile) {
        return memberRepository.findByMobile(mobile);
    }

    public List<Member> listMembersBySociety(Long societyId) {
        return memberRepository.listMembersBySociety(societyId);
    }

     String ES_ADMIN = "!@#$%^&*()";
     String ONE = "ES_ADMIN";
     String ONE_PASSWORD = "admin@123";
     String TWO = "ADMIN";
     String THREE = "MEMBER";
}
