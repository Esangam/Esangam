package org.esangam.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.esangam.entity.Member;
import org.esangam.service.MemberService;

@Path("/bootstrap")
public class BootstrapResource {

    @Inject
    MemberService memberService;

    @GET
    @Path("/create-admin")
    @Transactional
    public String createAdmin() {

        Member m = new Member();
        m.setMobileNumber("9999999999");
        m.setFirstName("System");
        m.setLastName("Admin");
        m.setPassword(memberService.hashPassword("admin123"));
        m.setRole("ADMIN");

        memberService.addMember(m);

        return "Admin user created. Login with mobile=9999999999 & password=admin123";
    }
}
