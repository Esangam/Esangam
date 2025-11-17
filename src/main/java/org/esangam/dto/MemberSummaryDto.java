package org.esangam.dto;

import org.esangam.entity.Member;
import org.esangam.entity.Society;

public class MemberSummaryDto {

    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String fullName;
    private String role;
    private Long societyId;
    private String societyName;

    public static MemberSummaryDto from(Member member) {
        MemberSummaryDto dto = new MemberSummaryDto();
        dto.mobileNumber = member.getMobileNumber();
        dto.firstName = member.getFirstName();
        dto.lastName = member.getLastName();
        dto.fullName = member.getFullName();
        dto.role = member.getRole();
        Society s = member.getSociety();
        if (s != null) {
            dto.societyId = s.getId();
            dto.societyName = s.getName();
        }
        return dto;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public Long getSocietyId() {
        return societyId;
    }

    public String getSocietyName() {
        return societyName;
    }
}
