package org.esangam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LoanRequest {

    @NotNull
    @Min(1)
    private Long requestedAmount;

    private String purpose;

    public Long getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Long requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
