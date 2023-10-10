package edu.westga.cs3230.furniturerentalsystem.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

//TODO: Need to get DB info for this class
public class DbUtil {
    private static final String DB_USERNAME = "cs3230f23c";
    private static final String DB_PASSWORD = "qjvw6rTXAXCmmR7EUBU@";
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER_CLASS = "driver.class.name";

    private static ComboPooledDataSource dataSource;

    static {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(DB_DRIVER_CLASS);

            dataSource.setJdbcUrl(DB_URL);
            dataSource.setUser(DB_USERNAME);
            dataSource.setPassword(DB_PASSWORD);

            dataSource.setMinPoolSize(100);
            dataSource.setMaxPoolSize(1000);
            dataSource.setAcquireIncrement(5);

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
