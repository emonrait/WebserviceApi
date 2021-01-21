/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erainfotechbd.agentbaning.dbconnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 09-09-2018
 *
 * @author E N A M U L (RPL Macdicine)
 * Fro Exception java.sql.SQLException: ORA-28040: No matching authentication protocol
 * I deleted the ojdbc14.jar file and used ojdbc6.jar instead and it worked for me
 * https://stackoverflow.com/questions/24100117/ora-28040-no-matching-authentication-protocol-exception
 */
public class DBConnectionHandler {

    Connection con = null;
    static String sDriverName = "";
    static String sDBUrl = "";
    static String sUserName = "";
    static String sPassword = "";
    static String developlent_state = "D";
    //Getting Connectio to oracle database

    public static Connection getConnection(String dbType) {
        if ("M".equals(dbType)) {
            return getMysqlConnection();
        } else {
            return getOracleConnection();
        }
    }

    public static Connection getMysqlConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            readMysqlConnProperties();
            con = DriverManager.getConnection(sDBUrl, sUserName, sPassword);
            //con = DriverManager.getConnection("jdbc:mysql://10.11.201.15/eportal?user=root&password=root123");
            //con = DriverManager.getConnection("jdbc:mysql://10.11.201.15/eportal?","root","root123");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    static void readMysqlConnProperties() {
        Properties prop = new Properties();
        InputStream propFile = null;
        try {
            String filename = "./com/erainfotechbd/agentbaning/dbconnection/DBConnection.properties";
            propFile = DBConnectionHandler.class.getClassLoader().getResourceAsStream(filename);
            if (propFile == null) {
                System.out.println("Sorry, unable to find " + filename);
            }
            //load a properties file from class path, inside static method
            prop.load(propFile);

            developlent_state = prop.getProperty("developlent_state");

            if (developlent_state.equals("D")) {//Development
                sDriverName = prop.getProperty("mdrivername");
                sDBUrl = prop.getProperty("mdburl");
                sUserName = prop.getProperty("musername");
                sPassword = prop.getProperty("mpassword");
                //System.out.println("sPassword = " + sPassword);
            } else if (developlent_state.equals("T")) {//Development
                sDriverName = prop.getProperty("mdrivername");
                sDBUrl = prop.getProperty("mdburl");
                sUserName = prop.getProperty("musername");
                sPassword = prop.getProperty("mpassword");
                //System.out.println("sPassword = " + sPassword);
            }

        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }

    public static Connection getOracleConnection() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            readOracleConnProperties();
            con = DriverManager.getConnection(sDBUrl, sUserName, sPassword);
            //con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.201.161:1521:orcl", "mybank", "mybank");
            //con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.201.136:1521:moon", "cmsemn", "cmsemn");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    static void readOracleConnProperties() {
        Properties prop = new Properties();
        InputStream propFile = null;
        try {
            String filename = "./com/erainfotechbd/agentbaning/dbconnection/DBConnection.properties";
            propFile = DBConnectionHandler.class.getClassLoader().getResourceAsStream(filename);
            if (propFile == null) {
                System.out.println("Sorry, unable to find " + filename);
            }
            //load a properties file from class path, inside static method
            prop.load(propFile);

            developlent_state = prop.getProperty("developlent_state");

            if (developlent_state.equals("D")) {//Development
                sDriverName = prop.getProperty("odrivername");
                sDBUrl = prop.getProperty("odburl");
                sUserName = prop.getProperty("ousername");
                sPassword = prop.getProperty("opassword");
                //System.out.println("sPassword = " + sPassword);
            }else if (developlent_state.equals("U")) {//UAT
                sDriverName = prop.getProperty("udrivername");
                sDBUrl = prop.getProperty("udburl");
                sUserName = prop.getProperty("uusername");
                sPassword = prop.getProperty("upassword");
                //System.out.println("sPassword = " + sPassword);
            }else if (developlent_state.equals("UL")) {//UAT
                sDriverName = prop.getProperty("uldrivername");
                sDBUrl = prop.getProperty("uldburl");
                sUserName = prop.getProperty("ulusername");
                sPassword = prop.getProperty("ulpassword");
                //System.out.println("sPassword = " + sPassword);
            }else if (developlent_state.equals("L")) {//UAT
                sDriverName = prop.getProperty("ldrivername");
                sDBUrl = prop.getProperty("ldburl");
                sUserName = prop.getProperty("lusername");
                sPassword = prop.getProperty("lpassword");
                //System.out.println("sPassword = " + sPassword);
            }

        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }

    //Close Connection
    public static void releaseAll(Connection con, Statement statement, PreparedStatement preStatement, ResultSet resultSet) {
        try {
            releaseConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            releasePrepareStatement(preStatement);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            releaseResultSetAndStatement(resultSet, statement);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            releaseResultSetAndStatement(resultSet, statement);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void releaseCallStatement(CallableStatement cs) {

        try {
            if (cs != null) {
                cs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Close Connection
    public static void releaseConnection(Connection con) {
        try {
            if (con != null) {
               con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Close Result Set and Statement
    public static void releaseResultSetAndStatement(ResultSet resultSet, Statement statement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Close PreparedStatement
    public static void releasePrepareStatement(PreparedStatement preStatement) {
        try {
            if (preStatement != null) {
                preStatement.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
//    public static void main(String[] args) {
//       System.out.println("args = " + new DBConnectionHandler().getConnection("O")); 
//    }
    
    
    
//    public static Connection getTestMysqlConnection() {
//        Connection con = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            //con = DriverManager.getConnection("jdbc:mysql://10.11.201.15/eportal?user=root&password=root123");
//            con = DriverManager.getConnection("jdbc:mysql://10.11.201.96:3320/eraas?user=eraas&password=eraas123");
//        } catch (SQLException ex) {
//            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return con;
//    }
    //"insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)"
}
