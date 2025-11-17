package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.CreateAdminRequest;
import org.esangam.entity.Member;
import org.esangam.entity.Society;
import org.esangam.service.MemberService;
import org.esangam.service.SocietyService;

import java.util.List;

/**
 * ES_ADMIN creates new societies with ADMINs.
 */
@Path("/society")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SocietyResource {

    @Inject
    MemberService memberService;

    @Inject
    SocietyService societyService;

    /** Create a new society + ADMIN. */
    @POST
    @Path("/create")
    @RolesAllowed("ES_ADMIN")
    public Member createSocietyAdmin(CreateAdminRequest req) {
        return memberService.createSocietyAdmin(
                req.societyName,
                req.description,
                req.adminMobile,
                req.firstName,
                req.lastName,
                req.password
        );
    }

    /** List all societies. */
    @GET
    @Path("/all")
    @RolesAllowed("ES_ADMIN")
    public List<Society> listAll() {
        return societyService.listAll();
    }
}
