package org.esangam.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Sangam (Society) in the Esangam platform.
 * Each society is managed by one ADMIN and can have multiple members.
 */
@Entity
@Table(name = "society")
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the Sangam/Society. */
    @Column(nullable = false, unique = true)
    private String name;

    /** Description added by ADMIN. */
    @Column(length = 500)
    private String description;

    /** Members belonging to this society. */
    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    /** Interest rate configuration for this society. */
    @OneToOne(mappedBy = "society", cascade = CascadeType.ALL)
    private InterestRate interestRate;

    /** Announcements posted inside this society. */
    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Member> getMembers() { return members; }

    public void setMembers(List<Member> members) { this.members = members; }

    public InterestRate getInterestRate() { return interestRate; }

    public void setInterestRate(InterestRate interestRate) { this.interestRate = interestRate; }

    public List<Announcement> getAnnouncements() { return announcements; }

    public void setAnnouncements(List<Announcement> announcements) { this.announcements = announcements; }
}
