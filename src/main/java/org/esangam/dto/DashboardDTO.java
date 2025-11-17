package org.esangam.dto;

import java.util.List;

/**
 * Aggregated dashboard data returned to ADMIN/MEMBER to reduce API calls.
 */
public class DashboardDTO {
    public String societyName;
    public String societyDescription;
    public List<?> members;
    public List<?> loans;
    public List<?> announcements;
    public double baseRate;
    public double overdueRate;
}
