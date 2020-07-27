package com.codegym.aribnb.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {

    public static String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123456";
        String encryptedPassword = encryptedPassword(password);

        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}
