package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.esangam.entity.Member;
import org.esangam.repository.MemberRepository;

/**
 * Handles authentication logic such as verifying login credentials.
 */
@ApplicationScoped
public class AuthService {

    @Inject
    MemberRepository memberRepository;

    /**
     * Validates a member's login credentials.
     *
     * @param mobile   mobile number
     * @param password raw password
     * @return Member if valid, otherwise null
     */
    @Transactional
    public Member validateLogin(String mobile, String password) {
        Member m = memberRepository.findByMobile(mobile);
        if (m == null) return null;
        if (!m.getPassword().equals(password)) return null;
        return m;
    }
}
