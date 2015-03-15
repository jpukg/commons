package com.samenea.commons.model.repository.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jalal Ashrafi
 */
public class StringAsMapUserType implements UserType {
    @Transient
    public static final String KEY_VALUE_DELIMETER = "#!#";
    @Transient
    public static final String ITEM_DELIMETER = "#;#";

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return HashMap.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        } else if (x == null || y == null) {
            return false;
        } else {
            return x.equals(y);
        }

    }

    public int hashCode(Object arg0) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Map<String,String > result = null;
        if (!rs.wasNull()) {
            if (rs.getString(names[0]) != null) {
                final String serializedMapAsString = rs.getString(names[0]);
                result = getKeyValueMap(serializedMapAsString);
            }
        }
        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, convertToString((Map<String, String>) value));
        }
    }



    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    private static String convertToString(Map<String, String> parameters) {
        String result = "";
        if(parameters != null){
            for (String key : parameters.keySet()) {
                result += String.format("%s%s%s%s",key,KEY_VALUE_DELIMETER,parameters.get(key),ITEM_DELIMETER);
            }
        }
        return result;
    }
    public Map<String,String> getKeyValueMap(String serializedKeyValues) {
        Map<String ,String> result = new HashMap<String, String>();
        if("".equals(serializedKeyValues)){
            return result;
        }
        String[] items = serializedKeyValues.split(ITEM_DELIMETER);
        for (String item : items) {
            String[] keyValue = item.split(KEY_VALUE_DELIMETER);
            final String value = keyValue.length <= 1 ? "" : keyValue[1];
            result.put(keyValue[0], value);
        }
        return result;
    }

}