package com.comedor.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Reserva;
import com.comedor.utils.ModelUtils;
import com.comedor.view.CajeroView;
import com.comedor.view.EstiloGral;

import aura.core.AuraBox;
import aura.layouts.AuraColumn;

public class CajeroController {
    private final NavigationDelegate delegate;
    private final CajeroView view;
    private final PersistenciaManager persistenciaManager;

    CajeroController(CajeroView view, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.view = view;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
        sendReservas();
    }

    private void setupListeners() {
        view.find("backBtn").onClick(b -> {
            delegate.onBackToLoginRequested();
        });
    }

    public void reset(){
        view.removeAllReservas();
        sendReservas();
    }

    private void sendReservas(){
        Queue<Reserva> almmuerzoRes = persistenciaManager.AlmuerzoWaitingQueue();
        Queue<Reserva> desayunoRes = persistenciaManager.DesayunoWaitingQueue();

        view.setReservasDesayuno(desayunoRes);
        view.setReservasAlmuerzo(almmuerzoRes);

        for(AuraBox<?> b : view.findAll("confirmarBtn")){
            b.onClick(button -> {
                AuraColumn parent = (AuraColumn) button.getParent();
                String data = parent.getId();
                String[] parts = data.split("-");
                String cedula = parts[0];
                String tipoReserva = parts[1];
                verificarFaceId(Menu.TipoMenu.valueOf(tipoReserva), cedula);
            });
        }
    }

    private void verificarFaceId(TipoMenu tipo, String cedula){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una foto para verificar tu identidad");

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {

            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            view.verificarFaceId(rutaArchivo, cedula);

            try {
                BufferedImage img1 = ImageIO.read(new File(rutaArchivo));
                BufferedImage img2 = ImageIO.read(new File(EstiloGral.getImgPath(cedula)));

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        if(ModelUtils.compararRostros(img1, img2)){
                            

                            double monto = persistenciaManager.getPrecioForUser(cedula);
                            double saldo = persistenciaManager.getSaldoFromCedula(cedula);
                            if(monto > saldo){
                                EstiloGral.ShowMessage("Saldo insuficiente, favor recargar", EstiloGral.ERROR_MESSAGE);
                            } else {
                                persistenciaManager.aceptarReserva(cedula, tipo);
                                persistenciaManager.sumarSaldo(cedula, -monto);
                                EstiloGral.ShowMessage("Reserva exitosa", EstiloGral.SUCCESS_MESSAGE);
                                view.removeReserva((AuraColumn) view.find(cedula + "-" + tipo.toString()));
                                persistenciaManager.agregarComensalesPorServicio(tipo, persistenciaManager.getRoleFromCedula(cedula));  
                            }

                        } else {
                            
                            EstiloGral.ShowMessage("La verificación facial ha fallado. Intente de nuevo.", EstiloGral.ERROR_MESSAGE);
                        }

                    } catch (InterruptedException e) {
                        
                    }

                }).start();

            } catch (IOException e) {
                EstiloGral.ShowMessage("Error al procesar la imagen. Intente de nuevo.", EstiloGral.ERROR_MESSAGE);
            }
            
        } else {
            EstiloGral.ShowMessage("No se seleccionó ninguna foto. Intente de nuevo.", EstiloGral.ERROR_MESSAGE);
        }
    }
}
