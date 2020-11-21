package src.sql.tables;

/**
 * Class representing a collection of User Credentials
 */
public class UserCredentials {

    //private byte[] passwordHash;
    private String passwordHash;
    private byte[] salt;
    private String role;
    private String username;

    public UserCredentials() {}

    public void setCredentials(String password, byte[] salt, String role, String username) {
        this.passwordHash = password;
        this.salt = salt;
        this.role = role;
        this.username = username;
    }
    //public byte[] getPasswordHash() { return passwordHash; }
    public String getPasswordHash() { return passwordHash; }
    public byte[] getSalt() { return salt; }
    public String[] getUserInfo() { return new String[] {role, username}; }
    public Boolean isNull() { return role==null; }
}