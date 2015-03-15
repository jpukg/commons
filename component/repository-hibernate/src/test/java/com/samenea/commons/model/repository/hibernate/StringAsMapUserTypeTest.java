package com.samenea.commons.model.repository.hibernate;

import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @author Jalal Ashrafi
 */
public class StringAsMapUserTypeTest {

    //region Test fields
    StringAsMapUserType stringAsMapUserType;
    private ResultSet resultSet;
    private SessionImplementor session;
    String[] names = new String[]{"colName"};
    private PreparedStatement preparedStatement;
    //endregion


    @Before
    public void setup(){
        stringAsMapUserType = new StringAsMapUserType();
        resultSet = mock(ResultSet.class);
        preparedStatement = mock(PreparedStatement.class);

    }

    //region nullSafeGet_tests
    @Test
    public void nullSafeGet_should_return_null_map_for_null_string() throws SQLException {
        when(resultSet.getString(names[0])).thenReturn(null);
        final Map<String,String > result = (Map<String, String>) stringAsMapUserType.nullSafeGet(resultSet, names, session, null);
        Assert.assertNull("result map should be null for null string", result);
    }
    @Test
    public void nullSafeGet_should_return_empty_map_for_empty_string() throws SQLException {
        when(resultSet.getString(names[0])).thenReturn("");
        final Map<String,String > result = (Map<String, String>) stringAsMapUserType.nullSafeGet(resultSet, names, session, null);
        Assert.assertNotNull("result can not be null",result);
        Assert.assertTrue(result.isEmpty());
    }
    @Test
    public void nullSafeGet_should_return_string_as_map() throws SQLException {
        Map<String,String> map = Maps.newHashMap();
        map.put("key","value");
        String mapAsString = convertToString(map);
        when(resultSet.getString(names[0])).thenReturn(mapAsString);
        final Map<String,String > result = (Map<String, String>) stringAsMapUserType.nullSafeGet(resultSet, names, session, null);
        Assert.assertEquals(map, result);
    }
    @Test
    public void nullSafeGet_should_work_correctly_if_value_is_empty() throws SQLException {
        Map<String,String> map = Maps.newHashMap();
        map.put("key","");
        String mapAsString = convertToString(map);
        when(resultSet.getString(names[0])).thenReturn(mapAsString);
        final Map<String,String > result = (Map<String, String>) stringAsMapUserType.nullSafeGet(resultSet, names, session, null);
        Assert.assertEquals(map, result);
    }
    //endregion

    //region nullSafeSet tests
    @Test
    public void nullSafeSet_should_set_params_correctly_on_preparedStatment() throws Exception {
        final int index = 1;
        Map<String,String> map = Maps.newHashMap();
        map.put("key","value");
        String mapAsString = convertToString(map);

        stringAsMapUserType.nullSafeSet(preparedStatement, map, index, null);
        verify(preparedStatement,times(1)).setString(index,mapAsString);

    }
    @Test
    public void nullSafeSet_should_set_empty_string_when_passed_empty_map() throws Exception {
        final int index = 1;
        stringAsMapUserType.nullSafeSet(preparedStatement, new HashMap<String,String>(), index, null);
        verify(preparedStatement,times(1)).setString(index,"");
    }
    @Test
    public void nullSafeSet_should_set_null_when_passed_null_value() throws Exception {
        final int index = 1;
        stringAsMapUserType.nullSafeSet(preparedStatement, null, index, null);
        verify(preparedStatement,times(1)).setNull(index, Types.VARCHAR);
    }
    //endregion

    private String convertToString(Map<String, String> map) {
        String mapAsString = "";
        for (String key : map.keySet()) {
            mapAsString += String.format("%s%s%s%s", key, StringAsMapUserType.KEY_VALUE_DELIMETER,map.get(key), StringAsMapUserType.ITEM_DELIMETER);
        }
        return mapAsString;
    }
}
