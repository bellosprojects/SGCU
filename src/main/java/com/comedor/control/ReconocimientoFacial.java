package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


public class ReconocimientoFacial {
    private PersistenciaManager persistencia;

    public ReconocimientoFacial() {
        this.persistencia = new PersistenciaManager();
    }

    //comparación de fotos
    private boolean compararRostros(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }
        // Se recorre la matriz de píxeles 
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                
                // Extrae el valor numérico del color en la coordenada (x, y)
                int pixelColor1 = img1.getRGB(x, y);
                int pixelColor2 = img2.getRGB(x, y);

                
                if (pixelColor1 != pixelColor2) {
                    return false;
                }
            }
        }

        return true;         
    }



    public void procesarCobroFacial(BufferedImage fotoCapturada, String cedula) {
        
        User usuario = persistencia.getUserFromCedula(cedula);
        
        if (usuario.getCedula() == null || usuario.getCedula().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado en el sistema.");
            return;
        }

        BufferedImage fotoBaseDatos = cargarFotoUsuario(cedula);
        if (fotoBaseDatos == null) {
            JOptionPane.showMessageDialog(null, "El usuario no tiene una foto registrada para comparar.");
            return;
        }

        if (compararRostros(fotoCapturada, fotoBaseDatos)) {
            
            double ccb = persistencia.getCCB();
            double montoAPagar = calcularTarifaFinal(ccb, usuario);
            
            if (usuario.saldoSuficiente(montoAPagar)) {
                
                double nuevoSaldo = usuario.getSaldo() - montoAPagar;
                usuario.setSaldo(nuevoSaldo); //funcion para actualizar saldo
                
                persistencia.actualizarUsuario(usuario); 

                double montoMostrar = Math.round(montoAPagar * 100.0) / 100.0;
                double saldoMostrar = Math.round(nuevoSaldo * 100.0) / 100.0;
                
                JOptionPane.showMessageDialog(null, "Cobro exitoso de: " + montoMostrar + " Bs.\nNuevo saldo: " + saldoMostrar + " Bs.");
                
            } else {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente. Necesita " + montoAPagar + " Bs.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El rostro no coincide con el registro.");
        }
    }

    private double calcularTarifaFinal(double ccb, User usuario) {
        String rol = usuario.getRole().toLowerCase(); 
        double montoFinal = 0.0;

        switch (rol) {
            case "estudiante":
                montoFinal = ccb * 0.25;
                break;
            case "profesor":
                montoFinal = ccb * 0.85;
                break;
            case "trabajador":
                montoFinal = ccb * 1.00;
                break;
            default:
                montoFinal = ccb;
                break;
        }
        return montoFinal;
    }

    // Método para leer la imagen de la base de datos
    private BufferedImage cargarFotoUsuario(String cedula) {
        try {
            File file = new File("C:\\SGCU\\imagenes\\" + cedula + ".jpg");
            if (file.exists()) {
                return ImageIO.read(file);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
        return null;
    }
}