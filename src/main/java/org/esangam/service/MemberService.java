package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.Member;
import org.esangam.repository.MemberRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class MemberService {


    MemberRepository memberRepository;
    Member member;

    @Inject
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addMember(Member member){
        memberRepository.persist(member);
    }

    public List<Member> getAllMembers(){
        return memberRepository.listAll();
    }

    public Member findById(Long id){
        return memberRepository.findById(id);
    }


}
