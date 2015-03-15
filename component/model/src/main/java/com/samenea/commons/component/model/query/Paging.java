package com.samenea.commons.component.model.query;

import com.samenea.commons.component.model.ValueObject;
import com.samenea.commons.component.model.query.enums.SortOrder;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/24/12
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Paging extends ValueObject {
    private final int first;
    private final int pageSize;
    private final String sortField;
    private final SortOrder sortOrder;
    public static final Paging NON_PAGING = new Paging();

    public Paging(int first, int pageSize, String sortField, SortOrder sortOrder) {
        this.first = first;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    private Paging() {
        this.first = 0;
        this.pageSize = 0;
        this.sortField = null;
        this.sortOrder = null;
    }

    public int getFirst() {
        return first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paging paging = (Paging) o;

        if (first != paging.first) return false;
        if (pageSize != paging.pageSize) return false;
        if (sortField != null ? !sortField.equals(paging.sortField) : paging.sortField != null) return false;
        if (sortOrder != paging.sortOrder) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first;
        result = 31 * result + pageSize;
        result = 31 * result + (sortField != null ? sortField.hashCode() : 0);
        result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Paging");
        sb.append("{first=").append(first);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", sortField='").append(sortField).append('\'');
        sb.append(", sortOrder=").append(sortOrder);
        sb.append('}');
        return sb.toString();
    }
}
