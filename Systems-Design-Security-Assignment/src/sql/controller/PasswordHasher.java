package src.sql.controller;

import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that contains methods for generating unique salts
 * and generated hashed passwords.
 */
public class PasswordHasher {

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * @param string password to be hashed
     * @return object array containing the hashed password and the salt
     */
    public static byte[] generateHashPassword(String password, byte[] salt) {

        byte[] hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
            return hashedPassword;
        }
    }
    public static void main(String[] args) {
        byte[] salt = PasswordHasher.generateSalt();
        System.out.println(PasswordHasher.generateHashPassword("123", salt));

    }
}