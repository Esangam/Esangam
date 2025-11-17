package org.esangam.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import org.esangam.service.NotificationService;

/**
 * SSE endpoint used for live notifications.
 */
@Path("/notifications")
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void stream(
            @QueryParam("mobile") String mobile,
            @Context SseEventSink sink,
            @Context Sse sse
    ) {
        notificationService.setSse(sse);
        notificationService.register(mobile, sink);

        sink.send(sse.newEvent("Connected to SSE notifications"));
    }
}
