package org.esangam.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import jakarta.transaction.Transactional;
import org.esangam.entity.Member;
import org.esangam.entity.Notification;
import org.esangam.entity.Loan;
import org.esangam.entity.Society;
import org.esangam.repository.NotificationRepository;
import org.esangam.repository.MemberRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    MemberRepository memberRepository;

    // key = mobileNumber
    private final Map<String, SseEventSink> sinks = new ConcurrentHashMap<>();

    private Sse sse;

    public void setSse(Sse sse) {
        this.sse = sse;
    }

    /** Registers a new SSE connection for a mobile number. */
    public void register(String mobile, SseEventSink sink) {
        // Close old sink if there is one
        SseEventSink existing = sinks.put(mobile, sink);
        if (existing != null && !existing.isClosed()) {
            try {
                existing.close();
            } catch (Exception ignored) {
            }
        }
    }

    /** Optional: allow explicit unregister on close. */
    public void unregister(String mobile) {
        SseEventSink sink = sinks.remove(mobile);
        if (sink != null && !sink.isClosed()) {
            try {
                sink.close();
            } catch (Exception ignored) {
            }
        }
    }

    /** Sends a notification event to a member and persists it. */
    @Transactional
    public void sendToMember(String mobile, String message) {
        Member member = memberRepository.findByMobile(mobile);
        if (member == null) {
            return;
        }

        // Persist notification
        Notification n = new Notification();
        n.setMessage(message);
        n.setMember(member);
        notificationRepository.persist(n);

        // No SSE configured or no active sink → nothing more to do
        SseEventSink sink = sinks.get(mobile);
        if (sse == null || sink == null) {
            return;
        }

        // If sink is already closed, remove it and skip sending
        if (sink.isClosed()) {
            sinks.remove(mobile);
            return;
        }

        try {
            // Build an event (you can add name/id as needed)
            var event = sse.newEventBuilder()
                    .name("notification")
                    .data(String.class, message)
                    .build();

            // synchronize per sink in case multiple threads send to same connection
            synchronized (sink) {
                sink.send(event);
            }
        } catch (IllegalStateException e) {
            // Sink got closed between isClosed() check and send()
            sinks.remove(mobile);
            // IMPORTANT: do NOT rethrow, so /loan/request still succeeds
            System.out.println("SSE sink already closed for " + mobile + ", skipping notification");
        } catch (Exception e) {
            // Log, but don't break the business logic
            e.printStackTrace();
        }
    }

    /** Notify ADMIN that a new loan request arrived. */
    public void notifyAdminOfLoanRequest(Member member) {
        Society society = member.getSociety();
        if (society == null) return;
        for (Member m : society.getMembers()) {
            if ("ADMIN".equals(m.getRole())) {
                sendToMember(m.getMobileNumber(), "New loan request from " + member.getFullName());
            }
        }
    }

    /** Notify member loan approved. */
    public void notifyLoanApproved(Loan loan) {
        sendToMember(
                loan.getMember().getMobileNumber(),
                "Your loan request is APPROVED! Amount: " + loan.getAmount()
        );
    }

    /** Notify member loan rejected. */
    public void notifyLoanRejected(Loan loan) {
        sendToMember(
                loan.getMember().getMobileNumber(),
                "Your loan request is REJECTED."
        );
    }

    /** Notify all members in a society about new announcement. */
    public void notifyAnnouncement(Society society, String title) {
        for (Member m : society.getMembers()) {
            sendToMember(m.getMobileNumber(), "Announcement: " + title);
        }
    }
}
