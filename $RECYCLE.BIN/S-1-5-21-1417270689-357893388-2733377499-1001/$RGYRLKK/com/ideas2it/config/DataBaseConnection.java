package com.ideas2it.config;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class DataBaseConnection {

    private static Properties properties = new Properties();
    private static Logger logger = LoggerFactory.getLogger(DataBaseConnection.class);
    private static Connection connection = null;

    String driver = properties.getProperty("database.driver");
    String url = properties.getProperty("database.url");
    String user =  properties.getProperty("database.user");
    String password = properties.getProperty("database.password"); 
  
    private DataBaseConnection() {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("exception occured" + e);
        }
    
    }
  
    static {
  
        try {
            InputStream inputForPropertyExcecute = DataBaseConnection.class.getClassLoader().getResourceAsStream(/*"C:\\Users\\lenovo\\Documents\\employee_project\\com\\ideas2it\\properties\\*/"app.properties");     
            properties.load(inputForPropertyExcecute);
        } catch (IOException e) {
            logger.error("exception occured" + e);
        }
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            DataBaseConnection dbConnection = new DataBaseConnection();
        }
        return connection;
    }

}   
     
