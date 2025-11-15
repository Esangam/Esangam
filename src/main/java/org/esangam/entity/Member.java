package org.esangam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobileNumber;  // primary key

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Transient
    private String fullName;

    @Column(nullable = false)
    private String password;      // hashed

    @Column(nullable = false)
    private String role;          // "ADMIN" or "MEMBER"

    public Member() {
    }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        if (firstName == null && lastName == null) return null;
        if (lastName == null) return firstName;
        if (firstName == null) return lastName;
        return firstName + " " + lastName;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
