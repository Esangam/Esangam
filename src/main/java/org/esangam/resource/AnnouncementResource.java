package org.esangam.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.esangam.dto.AnnouncementDTO;
import org.esangam.entity.Announcement;
import org.esangam.service.AnnouncementService;
import org.esangam.service.MemberService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

/**
 * ADMIN posts announcements.
 */
@Path("/announcement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnnouncementResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    AnnouncementService announcementService;

    @Inject
    MemberService memberService;

    @POST
    @Path("/post")
    @RolesAllowed("ADMIN")
    public Announcement postAnnouncement(AnnouncementDTO dto) {
        var admin = memberService.findByMobile(jwt.getSubject());
        return announcementService.postAnnouncement(admin, dto.title, dto.message);
    }

    @GET
    @Path("/list")
    @RolesAllowed({ "ADMIN", "MEMBER" })
    public List<Announcement> list() {
        Long societyId = Long.valueOf(jwt.getClaim("societyId").toString());
        return announcementService.listBySociety(societyId);
    }
}
