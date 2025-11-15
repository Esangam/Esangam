package org.esangam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LoanRequestDto {

    @NotNull(message = "Requested amount is required")
    @Min(value = 1, message = "Requested amount must be at least 1")
    private BigDecimal requestedAmount;

    private String purpose;

    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
