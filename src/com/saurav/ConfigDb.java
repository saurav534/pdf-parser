package com.saurav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDb {
    static Connection connection;
    static {
        String url = "jdbc:derby:./pdf_reader;create=true";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() throws SQLException {
        connection.createStatement().execute("drop table properties");
    }

    public static void createTable() throws SQLException {
        connection.createStatement().execute("create table properties(DB_KEY varchar(1000) PRIMARY KEY, DB_VALUE varchar(1000))");
    }

    public static void addKeyValue(String key, String value) {
        try {
            connection.createStatement().execute("insert into properties values ('" + key + "', '" + value + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateKeyValue(String key, String value) {
        try {
            connection.createStatement().execute("update properties set DB_VALUE = '" + value + "' where DB_KEY ='" + key + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("select DB_VALUE from properties where DB_KEY = '" + key + "'");
            if(resultSet.next()) {
                return resultSet.getString("DB_VALUE");
            }
            return "";
        } catch (SQLException e) {
            return "";
        }
    }
}
