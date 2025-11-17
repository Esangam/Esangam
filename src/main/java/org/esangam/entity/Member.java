package org.esangam.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Represents any user in Esangam.
 * ES_ADMIN -> platform owner
 * ADMIN    -> society owner
 * MEMBER   -> user under a society
 */
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    /** Role of the user: ES_ADMIN, ADMIN, MEMBER. */
    @Column(nullable = false)
    private String role;

    /** Belongs to one society (null for ES_ADMIN). */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;


    public String getMobileNumber() { return mobileNumber; }

    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public Society getSociety() { return society; }

    public void setSociety(Society society) { this.society = society; }
}
