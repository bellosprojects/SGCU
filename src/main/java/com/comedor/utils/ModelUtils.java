package com.comedor.utils;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public abstract  class ModelUtils {

    public static String encriptar(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean compararRostros(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                
                int pixelColor1 = img1.getRGB(x, y);
                int pixelColor2 = img2.getRGB(x, y);

                
                if (pixelColor1 != pixelColor2) {
                    return false;
                }
            }
        }

        return true;         
    }
    
    public static boolean esEnteroValido(String texto) {
        return texto.matches("\\d+");
    }

    public static boolean esDecimalValido(String texto) {
        return texto.matches("\\d+(\\.\\d+)?");
    }
}