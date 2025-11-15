package org.esangam.resource;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.esangam.dto.*;
import org.esangam.service.LoanService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Path("/api/loans")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoanResource {

    @Inject
    LoanService loanService;

    @Inject
    SecurityIdentity identity;

    private Instant toStartInstant(LocalDate date) {
        return date != null ? date.atStartOfDay(ZoneOffset.UTC).toInstant() : null;
    }

    private Instant toEndInstant(LocalDate date) {
        return date != null ? date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().minusMillis(1) : null;
    }

    @POST
    @RolesAllowed("MEMBER")
    public Response requestLoan(@Valid LoanRequestDto request) {
        String mobile = identity.getPrincipal().getName();
        return Response.status(Response.Status.CREATED)
                .entity(loanService.requestLoan(mobile, request))
                .build();
    }

    @GET
    @Path("/my")
    @RolesAllowed({"MEMBER", "ADMIN"})
    public Response myLoans() {
        String mobile = identity.getPrincipal().getName();
        return Response.ok(loanService.getLoansForMember(mobile)).build();
    }

    @GET
    @Path("/pending")
    @RolesAllowed("ADMIN")
    public Response pendingLoans() {
        return Response.ok(loanService.getPendingLoans()).build();
    }

    @POST
    @Path("/{id}/decision")
    @RolesAllowed("ADMIN")
    public Response decideLoan(@PathParam("id") Long id, @Valid LoanDecisionDto decisionDto) {
        return Response.ok(loanService.decideLoan(id, decisionDto)).build();
    }

    @GET
    @Path("/dashboard/admin")
    @RolesAllowed("ADMIN")
    public Response adminDashboard(@QueryParam("memberMobile") String memberMobile,
                                   @QueryParam("fromDate") LocalDate fromDate,
                                   @QueryParam("toDate") LocalDate toDate) {

        Instant from = toStartInstant(fromDate);
        Instant to = toEndInstant(toDate);

        LoanAdminDashboardDto dto = loanService.getAdminDashboard(memberMobile, from, to);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/dashboard/my")
    @RolesAllowed({"MEMBER", "ADMIN"})
    public Response myDashboard(@QueryParam("fromDate") LocalDate fromDate,
                                @QueryParam("toDate") LocalDate toDate) {

        String mobile = identity.getPrincipal().getName();
        Instant from = toStartInstant(fromDate);
        Instant to = toEndInstant(toDate);

        LoanMemberDashboardDto dto = loanService.getMemberDashboard(mobile, from, to);
        return Response.ok(dto).build();
    }

    @GET
    @Path("")
    @RolesAllowed("ADMIN")
    public Response listLoans(@QueryParam("memberMobile") String memberMobile,
                              @QueryParam("fromDate") LocalDate fromDate,
                              @QueryParam("toDate") LocalDate toDate,
                              @QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("size") @DefaultValue("20") int size) {

        Instant from = toStartInstant(fromDate);
        Instant to = toEndInstant(toDate);

        LoanPageDto dto = loanService.listLoans(memberMobile, from, to, page, size);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/my/paged")
    @RolesAllowed({"MEMBER", "ADMIN"})
    public Response myLoansPaged(@QueryParam("fromDate") LocalDate fromDate,
                                 @QueryParam("toDate") LocalDate toDate,
                                 @QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("size") @DefaultValue("20") int size) {

        String mobile = identity.getPrincipal().getName();
        Instant from = toStartInstant(fromDate);
        Instant to = toEndInstant(toDate);

        LoanPageDto dto = loanService.listLoans(mobile, from, to, page, size);
        return Response.ok(dto).build();
    }
}
