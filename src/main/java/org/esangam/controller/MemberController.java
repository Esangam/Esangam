package org.esangam.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.esangam.entity.Member;
import org.esangam.service.MemberService;
import java.util.List;

@Path("/esangam")
public class MemberController {

    MemberService memberService;

    @Inject
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @POST
    public void addMember(Member member){
        memberService.addMember(member);

    }
    @GET
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }
    @GET
    @Path("/{id}")
    public Member findById(@PathParam("/{id}") Long id){
        return memberService.findById(id);
    }

}
