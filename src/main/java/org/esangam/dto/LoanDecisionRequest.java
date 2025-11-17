package org.esangam.dto;

import jakarta.validation.constraints.NotBlank;

public class LoanDecisionRequest {

    @NotBlank
    private String decision; // APPROVE or REJECT

    private Long approvedAmount; // required if APPROVE

    private String comment;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Long getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(Long approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
