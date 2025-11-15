package org.esangam.resource;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.esangam.dto.MemberResponseDto;
import org.esangam.entity.Member;
import org.esangam.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/members")
public class MemberResource {

    @Inject
    MemberService memberService;

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/all")
    @RolesAllowed("ADMIN")
    public Response getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        List<MemberResponseDto> dtos = members.stream()
                .map(memberService::toDto)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed({"ADMIN", "MEMBER"})
    public Response getMyDetails() {
        String mobile = identity.getPrincipal().getName();
        Member m = memberService.findByMobileNumber(mobile);

        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Member not found")
                    .build();
        }

        MemberResponseDto dto = memberService.toDto(m);
        return Response.ok(dto).build();
    }
}
