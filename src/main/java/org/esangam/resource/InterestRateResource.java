package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.InterestRateDTO;
import org.esangam.entity.InterestRate;
import org.esangam.service.InterestRateService;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * ADMIN updates interest rate.
 */
@Path("/interest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InterestRateResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    InterestRateService interestRateService;

    @POST
    @Path("/update")
    @RolesAllowed("ADMIN")
    public InterestRate updateRate(InterestRateDTO dto) {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return interestRateService.setInterestRate(societyId, dto.baseRate, dto.overdueRate);
    }

    @GET
    @Path("/current")
    @RolesAllowed({ "ADMIN", "MEMBER" })
    public InterestRate getCurrent() {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return interestRateService.getInterestRate(societyId);
    }
}
