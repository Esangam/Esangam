package org.esangam.resource;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.esangam.dto.MemberResponseDto;
import org.esangam.dto.RegisterMemberRequest;
import org.esangam.entity.Member;
import org.esangam.service.MemberService;
import org.esangam.service.TokenService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    MemberService memberService;

    @Inject
    TokenService tokenService;

    @Inject
    SecurityIdentity identity;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        Member member = memberService.validateCredentials(request.getMobileNumber(), request.getPassword());
        if (member == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid mobile number or password")
                    .build();
        }

        String token = tokenService.generateToken(member);
        return Response.ok(new LoginResponse(token)).build();
    }

    @POST
    @Path("/register")
    public Response register(@Valid RegisterMemberRequest request) {
        Member member = memberService.registerMemberAsMember(
                request.getMobileNumber(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword()
        );

        MemberResponseDto dto = memberService.toDto(member);
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed({"ADMIN", "MEMBER"})
    public Response me() {
        IdentityResponse resp = new IdentityResponse();
        resp.setPrincipal(identity.getPrincipal().getName());
        resp.setRoles(identity.getRoles());
        resp.setAnonymous(identity.isAnonymous());

        Object nameClaim = identity.getAttribute("name");
        if (nameClaim != null) {
            resp.setName(nameClaim.toString());
        }

        return Response.ok(resp).build();
    }

    public static class LoginRequest {

        @jakarta.validation.constraints.NotBlank(message = "Mobile number is required")
        @jakarta.validation.constraints.Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
        private String mobileNumber;

        @jakarta.validation.constraints.NotBlank(message = "Password is required")
        private String password;

        public String getMobileNumber() { return mobileNumber; }
        public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;

        public LoginResponse() {}
        public LoginResponse(String token) { this.token = token; }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }

    public static class IdentityResponse {
        private String principal;
        private String name;
        private java.util.Set<String> roles;
        private boolean anonymous;

        public String getPrincipal() { return principal; }
        public void setPrincipal(String principal) { this.principal = principal; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public java.util.Set<String> getRoles() { return roles; }
        public void setRoles(java.util.Set<String> roles) { this.roles = roles; }

        public boolean isAnonymous() { return anonymous; }
        public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }
    }
}
