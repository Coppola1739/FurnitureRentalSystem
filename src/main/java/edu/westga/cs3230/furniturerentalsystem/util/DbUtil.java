package edu.westga.cs3230.furniturerentalsystem.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;


public class DbUtil {
    private static final String DB_USERNAME = "cs3230f23c";
    private static final String DB_PASSWORD = "qjvw6rTXAXCmmR7EUBU@";
    private static final String DB_URL = "160.10.217.6:3306";
    private static final String DB_DRIVER_CLASS = "driver.class.name";
    
    private static String connectionString = "jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@";

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
