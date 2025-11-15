package org.esangam.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class LoanDecisionDto {

    @NotBlank(message = "Decision is required and must be APPROVE or REJECT")
    private String decision;      // APPROVE or REJECT

    // required when APPROVE
    private BigDecimal approvedAmount;

    private String comment;

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
