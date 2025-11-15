package org.esangam.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.dto.MemberResponseDto;
import org.esangam.entity.Member;
import org.esangam.exception.MemberAlreadyExistsException;
import org.esangam.repository.MemberRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class MemberService {

    MemberRepository memberRepository;

    @Inject
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String hashPassword(String plainPassword) {
        return BcryptUtil.bcryptHash(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BcryptUtil.matches(plainPassword, hashedPassword);
    }

    public void addMember(Member member){
        memberRepository.persist(member);
    }

    public List<Member> getAllMembers(){
        return memberRepository.listAll();
    }

    public Member findByMobileNumber(String mobileNumber){
        return memberRepository.findByMobileNumber(mobileNumber);
    }

    public Member registerMemberAsMember(String mobileNumber, String firstName, String lastName, String password) {
        Member existing = memberRepository.findByMobileNumber(mobileNumber);
        if (existing != null) {
            throw new MemberAlreadyExistsException(mobileNumber);
        }

        Member m = new Member();
        m.setMobileNumber(mobileNumber);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setPassword(hashPassword(password));
        m.setRole("MEMBER");

        memberRepository.persist(m);
        return m;
    }

    public Member validateCredentials(String mobileNumber, String password) {
        Member member = memberRepository.findByMobileNumber(mobileNumber);
        if (member == null) {
            return null;
        }
        if (!verifyPassword(password, member.getPassword())) {
            return null;
        }
        return member;
    }

    public MemberResponseDto toDto(Member m) {
        if (m == null) return null;
        return new MemberResponseDto(
                m.getMobileNumber(),
                m.getFirstName(),
                m.getLastName(),
                m.getFullName(),
                m.getRole()
        );
    }
}
