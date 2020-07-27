package org.egov.collection.cdg.finance.model;

public class PageContract {

    private Long totalResults;
    private Long totalPages;
    private Long pageSize;
    private Long currentPage;
    private Long offSet;

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getOffSet() {
        return offSet;
    }

    public void setOffSet(final Long offSet) {
        this.offSet = offSet;
    }
}
