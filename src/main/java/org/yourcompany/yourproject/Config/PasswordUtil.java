package org.yourcompany.yourproject.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordUtil {
    // Hash một mật khẩu bằng SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occured when hash", e);
        }
    }   
    
    // Kiểm tra mật khẩu thô có khớp với mật khẩu đã hash
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        String hashedAttempt = hashPassword(plainPassword);
        return hashedAttempt.equals(hashedPassword);
    }
}