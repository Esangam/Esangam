package org.esangam.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Announcements posted by ADMIN inside the society.
 */
@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Society to which the announcement belongs. */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public Society getSociety() { return society; }

    public void setSociety(Society society) { this.society = society; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
