package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.ApproveLoanDTO;
import org.esangam.dto.LoanRequestDTO;
import org.esangam.entity.Loan;
import org.esangam.entity.Member;
import org.esangam.service.LoanService;
import org.esangam.service.MemberService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

/**
 * Loan operations for Member and Admin.
 */
@Path("/loan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoanResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    LoanService loanService;

    @Inject
    MemberService memberService;

    /** MEMBER requests a loan. */
    @POST
    @Path("/request")
    @RolesAllowed("MEMBER")
    public Loan requestLoan(LoanRequestDTO req) {
        String mobile = jwt.getSubject();
        return loanService.requestLoan(mobile, req.amount);
    }

    /** ADMIN approves loan. */
    @POST
    @Path("/approve")
    @RolesAllowed("ADMIN")
    public Loan approveLoan(ApproveLoanDTO req) {
        Member admin = memberService.findByMobile(jwt.getSubject());
        return loanService.approveLoan(req.loanId, admin);
    }

    /** ADMIN rejects loan. */
    @POST
    @Path("/reject")
    @RolesAllowed("ADMIN")
    public Loan rejectLoan(ApproveLoanDTO req) {
        return loanService.rejectLoan(req.loanId);
    }

    /** ADMIN fetches pending loan requests. */
    @GET
    @Path("/pending")
    @RolesAllowed("ADMIN")
    public List<Loan> pending() {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return loanService.listPendingLoans(societyId);
    }

    /** MEMBER fetches their loan history. */
    @GET
    @Path("/my")
    @RolesAllowed("MEMBER")
    public List<Loan> myLoans() {
        return loanService.listMemberLoans(jwt.getSubject());
    }
}
