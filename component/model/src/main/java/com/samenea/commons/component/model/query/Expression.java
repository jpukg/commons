package com.samenea.commons.component.model.query;

import com.samenea.commons.component.model.ValueObject;
import com.samenea.commons.component.model.exceptions.QueryException;
import com.samenea.commons.component.model.query.enums.Operator;
import com.samenea.commons.component.utils.AssertUtil;
import com.samenea.commons.component.utils.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class Expression extends ValueObject{
    private final String fieldName;
    private final Operator operator;
    private final List<Object> params;

    public Expression(String fieldName, Operator operator, Object... params) {
        AssertUtil.notNull(fieldName,QueryException.class);
        AssertUtil.notNull(operator,QueryException.class);
        AssertUtil.notEmpty(params, QueryException.class);
        operator.validateParamSize(params.length);
        this.fieldName = fieldName;
        this.operator = operator;
        this.params = CollectionUtils.paramsAsList(params);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Operator getOperator() {
        return operator;
    }

    public List<Object> getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expression that = (Expression) o;

        if (fieldName != null ? !fieldName.equals(that.fieldName) : that.fieldName != null) return false;
        if (operator != that.operator) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals

        return true;
    }

    @Override
    public int hashCode() {
        int result = fieldName != null ? fieldName.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
            return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Expression");
        sb.append("{fieldName='").append(fieldName).append('\'');
        sb.append(", operator=").append(operator);
        sb.append(", params=").append(params == null ? "null" : Arrays.asList(params).toString());
        sb.append('}');
        return sb.toString();
    }
}
