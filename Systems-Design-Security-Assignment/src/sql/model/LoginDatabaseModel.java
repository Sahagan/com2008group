package src.sql.model;

import java.sql.*;

import src.sql.tables.UserCredentials;

/**
 * Database model, handles all the SQL operations for all log in related tasks.
 */
public class LoginDatabaseModel extends DatabaseModel {

    public LoginDatabaseModel() {}

    public UserCredentials getUserCredentials(String email) {
        initConnection();
        initStatement();
        UserCredentials userCredentials = new UserCredentials();
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT password, Role, Salt, Username FROM Users WHERE Email='" + email + "';");
                while (getResult().next()) {
                    String password = getResult().getString(1);
                    String role = getResult().getString(2);
                    byte[] salt = getResult().getBytes(3);
                    String username = getResult().getString(4);
                    userCredentials.setCredentials(password, salt, role, username);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userCredentials;
    }
}