package org.esangam.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.esangam.dto.LoginRequest;
import org.esangam.dto.LoginResponse;
import org.esangam.service.AuthService;
import org.esangam.service.TokenService;
import org.esangam.service.MemberService;
import org.esangam.entity.Member;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Authentication endpoints for login and profile details.
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    TokenService tokenService;

    @Inject
    MemberService memberService;

    @Inject
    JsonWebToken jwt;

    /** Login endpoint. */
    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        Member m = authService.validateLogin(req.mobileNumber, req.password);
        if (m == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{'error':'Invalid credentials'}")
                    .build();
        }
        return Response.ok(new LoginResponse(tokenService.generateToken(m))).build();
    }

    /** Returns logged-in user information. */
    @GET
    @Path("/me")
    public Response me() {
        if (jwt == null || jwt.getSubject() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String mobile = jwt.getSubject();
        Object role = jwt.getClaim("role");
        Object societyId = jwt.getClaim("societyId");
        Object societyName = jwt.getClaim("societyName");

        return Response.ok(
                java.util.Map.of(
                        "mobile", mobile,
                        "role", role != null ? role : "",
                        "societyId", societyId != null ? societyId : "",
                        "societyName", societyName != null ? societyName : ""
                )
        ).build();
    }


    /** Bootstrap endpoint to create the Super Admin (ES_ADMIN). */
    @GET
    @Path("/bootstrap/create-admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String createEsAdmin() {
        Member m = memberService.createEsangamAdminIfNotExists();
        return "ES_ADMIN ready: mobile=" + m.getMobileNumber() + " password=" + m.getPassword();
    }
}
