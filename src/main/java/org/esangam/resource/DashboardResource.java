package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.DashboardDTO;
import org.esangam.service.*;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Aggregated dashboard endpoint – reduces number of API calls.
 */
@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    MemberService memberService;

    @Inject
    LoanService loanService;

    @Inject
    AnnouncementService announcementService;

    @Inject
    InterestRateService interestRateService;

    @Inject
    SocietyService societyService;

    @GET
    @RolesAllowed({ "ADMIN", "MEMBER" })
    public DashboardDTO getDashboard() {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        var s = societyService.findById(societyId);

        DashboardDTO dto = new DashboardDTO();
        dto.societyName = s.getName();
        dto.societyDescription = s.getDescription();
        dto.members = memberService.listMembersBySociety(societyId);
        dto.loans = loanService.listPendingLoans(societyId);
        dto.announcements = announcementService.listBySociety(societyId);

        var rate = interestRateService.getInterestRate(societyId);
        if (rate != null) {
            dto.baseRate = rate.getBaseRate();
            dto.overdueRate = rate.getOverdueRate();
        }

        return dto;
    }
}
