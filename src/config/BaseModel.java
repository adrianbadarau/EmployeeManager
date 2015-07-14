/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adrian
 */
public class BaseModel {
    
    public static java.sql.Connection getConnection() throws SQLException
    {

            return DriverManager.getConnection("jdbc:mysql://localhost/EmployeeManager", "root", "");

    }
    
    public static java.sql.ResultSet getQueryResultSet(String querry) throws SQLException
    {
        Connection con = BaseModel.getConnection();
        Statement st = con.createStatement();
        return st.executeQuery(querry);
        
    }
    
    }
