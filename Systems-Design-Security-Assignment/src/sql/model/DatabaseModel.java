package src.sql.model;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for database models, containing
 * methods for opening connections, executing SQL commands etc.
 */
public abstract class DatabaseModel {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    public void insertIntoDatabase(String table, String values){
        execute("INSERT INTO " + table + " VALUES " + values + ";");
    }
    
    public void removeFromDatabase(String table, String conditions){
        execute("DELETE FROM " + table + " WHERE " + conditions + ";");
    }
    
    private void execute(String querry){
        try{
            openConnection();
            openStatement();
            try {
                int count = getStatement().executeUpdate(querry);
            }
            finally {
                closeStatement();
                closeConnection();
            }
        }
        catch (SQLException ex){
            Logger.getLogger(DatabaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean executeWithBool(String querry) {
        try {
            openConnection();
            openStatement();
            try {
                int count = getStatement().executeUpdate(querry);
            } finally {
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            if (ex instanceof SQLIntegrityConstraintViolationException)
                return false;
            Logger.getLogger(DatabaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    protected void initConnection() {
        connection = null;
    }
    
    protected void openConnection() throws SQLException {
        //connection = DriverManager.getConnection("jdbc:mysql://localhost/com2008", "root", "905528280xrq");
        connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team021", "team021", "5f4306d9");
    }
    protected Connection getConnection() throws SQLException {
        return connection;
    }
    protected void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    protected void initStatement() {
        statement = null; 
    }
    
    protected void openStatement() throws SQLException {
        statement = connection.createStatement(); 
    }
    
    protected void closeStatement() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
    
    protected void openResultQuery(String query) throws SQLException{
        resultSet = getStatement().executeQuery(query); 
    }
    
    protected void closeResultQuery() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }
    
    protected ResultSet getResult() { 
        return resultSet; 
    }
    
    protected Statement getStatement() {
        if (statement != null) {
            return statement;
        } else {
            return null;
        }
    }
    
}