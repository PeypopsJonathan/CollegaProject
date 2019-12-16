package ucll.project.db;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String
            USERNAME = "",
            PASSWORD = "",
            SCHEMA = "";

    private static DataSource dataSource = null;

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private static void initDataSource() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:postgresql://databanken.ucll.be:51920/hakkaton?currentSchema=" + SCHEMA);
        p.setDriverClassName("org.postgresql.Driver");
        p.setUsername(USERNAME);
        p.setPassword(PASSWORD);
        p.setMaxActive(4);
        p.setInitialSize(1);
        p.setMinIdle(1);
        p.setMaxIdle(4);
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
    }

    private static DataSource getDataSource() {
        // thread safe lazy initialization
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        return dataSource;
    }
}