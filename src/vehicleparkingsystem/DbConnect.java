/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleparkingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author autocount
 */
public class DbConnect {
    public Statement dbConnect() {
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkmelaka_admin"+
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "testAdmin","123456");
            System.out.println("Database Connected");
            Statement stmt = con.createStatement();  
            return stmt;
            } catch(Exception e) {
                    e.printStackTrace();
            }
        return null;
    }
    
    public Statement userDbConnect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkmelaka"+
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "testAdmin","123456");
            System.out.println("Database Connected");
            Statement stmt=con.createStatement();  
            return stmt;
            } catch(Exception e) {
                e.printStackTrace();
            }
        return null;
    }
    
}
