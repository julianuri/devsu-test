package com.devsu.utils;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashUtils {

    private PasswordHashUtils() {
    }

    public String createHash(String password) {
       return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
