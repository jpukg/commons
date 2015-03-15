package com.samenea.commons.component.model.query;

import com.samenea.commons.component.model.ValueObject;
import com.samenea.commons.component.model.exceptions.QueryException;
import com.samenea.commons.component.utils.AssertUtil;
@Deprecated
public class Range<T extends Comparable> extends ValueObject{
    private String fieldName;
    private T from;
    private T to;




    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getFrom() {
        return from;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public Range(String fieldName, T from, T to) {
        AssertUtil.isTrue(from.compareTo(to)<=0, QueryException.class);
        this.fieldName = fieldName;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Range");
        sb.append("{fieldName='").append(fieldName).append('\'');
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (fieldName != null ? !fieldName.equals(range.fieldName) : range.fieldName != null) return false;
        if (from != null ? !from.equals(range.from) : range.from != null) return false;
        if (to != null ? !to.equals(range.to) : range.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fieldName != null ? fieldName.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
