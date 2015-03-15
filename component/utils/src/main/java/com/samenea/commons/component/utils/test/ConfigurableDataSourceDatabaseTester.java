package com.samenea.commons.component.utils.test;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.IDatabaseConnection;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/22/12
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurableDataSourceDatabaseTester extends DataSourceDatabaseTester {
    private Map<String, Object> connectionConfigs;

    public ConfigurableDataSourceDatabaseTester(DataSource dataSource, Map<String, Object> connectionConfigs) {
        super(dataSource);
        this.connectionConfigs = connectionConfigs;

    }

    public ConfigurableDataSourceDatabaseTester(DataSource dataSource) {
        super(dataSource);


    }

    public void setConnectionConfigs(Map<String, Object> connectionConfigs) {
        this.connectionConfigs = connectionConfigs;
    }

    public Map<String, Object> getConnectionConfigs() {
        return connectionConfigs;
    }

    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection connection = super.getConnection();
        if (connectionConfigs == null || connectionConfigs.isEmpty()) {
            return connection;
        }
        for (Map.Entry<String, Object> stringObjectEntry : connectionConfigs.entrySet()) {
            connection.getConfig().setProperty(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return connection;
    }
}
