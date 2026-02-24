package com.comedor.utils;

import java.security.MessageDigest;
import java.util.Base64;

public abstract  class ModelUtils {

    public static String encriptar(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
    
}