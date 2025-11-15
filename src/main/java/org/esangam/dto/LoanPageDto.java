package org.esangam.dto;

import java.util.List;

public class LoanPageDto {

    private int page;
    private int size;
    private long totalItems;
    private long totalPages;
    private List<LoanResponseDto> items;

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }

    public long getTotalPages() { return totalPages; }
    public void setTotalPages(long totalPages) { this.totalPages = totalPages; }

    public List<LoanResponseDto> getItems() { return items; }
    public void setItems(List<LoanResponseDto> items) { this.items = items; }
}
