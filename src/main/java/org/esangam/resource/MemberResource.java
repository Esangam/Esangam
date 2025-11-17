package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.CreateMemberRequest;
import org.esangam.entity.Member;
import org.esangam.service.MemberService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

/**
 * ADMIN adds members under their society.
 */
@Path("/member")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MemberResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    MemberService memberService;

    /** ADMIN adds a new member. */
    @POST
    @Path("/create")
    @RolesAllowed("ADMIN")
    public Member createMember(CreateMemberRequest req) {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return memberService.createMemberUnderAdmin(
                societyId,
                req.mobile,
                req.firstName,
                req.lastName,
                req.password
        );
    }

    /** ADMIN lists members under them. */
    @GET
    @Path("/list")
    @RolesAllowed("ADMIN")
    public List<Member> listMembers() {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return memberService.listMembersBySociety(societyId);
    }
}
