package org.esangam.dto;

import java.math.BigDecimal;

public class LoanMemberDashboardDto {

    private String memberMobile;

    private long totalLoans;
    private long pendingLoans;
    private long approvedLoans;
    private long rejectedLoans;

    private BigDecimal totalRequestedAmount;
    private BigDecimal totalApprovedAmount;

    public String getMemberMobile() { return memberMobile; }
    public void setMemberMobile(String memberMobile) { this.memberMobile = memberMobile; }

    public long getTotalLoans() { return totalLoans; }
    public void setTotalLoans(long totalLoans) { this.totalLoans = totalLoans; }

    public long getPendingLoans() { return pendingLoans; }
    public void setPendingLoans(long pendingLoans) { this.pendingLoans = pendingLoans; }

    public long getApprovedLoans() { return approvedLoans; }
    public void setApprovedLoans(long approvedLoans) { this.approvedLoans = approvedLoans; }

    public long getRejectedLoans() { return rejectedLoans; }
    public void setRejectedLoans(long rejectedLoans) { this.rejectedLoans = rejectedLoans; }

    public BigDecimal getTotalRequestedAmount() { return totalRequestedAmount; }
    public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) { this.totalRequestedAmount = totalRequestedAmount; }

    public BigDecimal getTotalApprovedAmount() { return totalApprovedAmount; }
    public void setTotalApprovedAmount(BigDecimal totalApprovedAmount) { this.totalApprovedAmount = totalApprovedAmount; }
}
