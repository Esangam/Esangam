package org.esangam.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Represents notifications delivered to members (used with SSE).
 */
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Member who receives the notification. */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mobile", nullable = false)
    private Member member;

    /** Notification message text. */
    @Column(nullable = false)
    private String message;

    /** Whether the user has seen the notification. */
    private boolean readFlag = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public Member getMember() { return member; }

    public void setMember(Member member) { this.member = member; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public boolean isReadFlag() { return readFlag; }

    public void setReadFlag(boolean readFlag) { this.readFlag = readFlag; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
