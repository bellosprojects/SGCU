package com.comedor.control;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Reserva;
import com.comedor.model.User;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.UserMenuView;

import aura.core.AuraBox;

public class UserMenuController {
    private final NavigationDelegate delegate;
    private final PersistenciaManager persistenciaManager;
    private final UserMenuView menuView;
    private final String cedula;
    private String tipoUsuario;

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView, NavigationDelegate delegate) {
        this.delegate = delegate;
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
        setup();
    }

    private void sendMenu() {
        Menu Desayuno = persistenciaManager.getMenu(TipoMenu.DESAYUNO);
        Menu Almuerzo = persistenciaManager.getMenu(TipoMenu.ALMUERZO);
        if (Desayuno != null && Desayuno.isValidMenu()) {
            menuView.setDesayuno(Desayuno);
        }
        if (Almuerzo != null && Almuerzo.isValidMenu()) {
            menuView.setAlmuerzo(Almuerzo);
        }
        Double precioFinal = (persistenciaManager.getCCB() * persistenciaManager.getPorcentajeFromRole(tipoUsuario)) / 100;
        menuView.setPrecio(precioFinal);
    }
//MODIFICADAAA
    private void sendUser() {
        User user = persistenciaManager.getUserByCedula(cedula); 
        if (user != null) {
            this.tipoUsuario = user.getRole();
            menuView.setUser(user);
            double precio = persistenciaManager.getPrecioForUser(tipoUsuario);
            menuView.setPrecio(precio);
        }
    }

    private void setup() {
        
        menuView.find("backBtn").onClick(b -> 
            delegate.onBackToLoginRequested()
        );

        menuView.find("rechargeBtn").onClick(b -> {
            menuView.showRecharge();
        });

        SwingUtilities.invokeLater(() -> 

            menuView.getModal().find("confirmRechargeBtn").onClick(b -> {
                recargarSaldo();
            })
            
        );

        menuView.find("bookBreakfastBtn").onClick(b -> {
            iniciarReserva(TipoMenu.DESAYUNO);
        });

        menuView.find("bookLunchBtn").onClick(b -> {
            iniciarReserva(TipoMenu.ALMUERZO);
        });

        menuView.find("menusBtn").onClick(b -> {
            menuView.showMenus();
        });

        menuView.find("reservationsBtn").onClick(b -> {
            menuView.showReservas();
        });

        sendUser();
        sendMenu();
        sendReservas();
    }

    private void sendReservas() {
        Reserva reservaDes = persistenciaManager.getReservaFromCedula(cedula, TipoMenu.DESAYUNO);
        if(reservaDes != null) {
            menuView.addReserva(reservaDes, TipoMenu.DESAYUNO);
        }

        Reserva reservaAlm = persistenciaManager.getReservaFromCedula(cedula, TipoMenu.ALMUERZO);
        if(reservaAlm != null) {
            menuView.addReserva(reservaAlm, TipoMenu.ALMUERZO);
        }

        for(AuraBox<?> btn : menuView.findAll("cancelarReserva")){
            btn.onClick(b -> {
                AuraBox<?> parent = (AuraBox<?>) btn.getParent();
                String tipoStr = parent.getId();
                TipoMenu tipo = TipoMenu.valueOf(tipoStr);
                persistenciaManager.cancelarReserva(cedula, tipo);
                parent.setVisible(false);
                EstiloGral.ShowMessage("Reserva cancelada", EstiloGral.SUCCESS_MESSAGE);
                menuView.updateSaldo(persistenciaManager.getSaldoFromCedula(cedula));
            });
        }
        
    }

    private boolean isValidInputs(String montoStr, String numeroReferencia) {
        boolean flag = true;
        if(montoStr.isEmpty() ){
            menuView.InvalidateInputs("rechargeMonto");
            EstiloGral.ShowMessage("Ingrese un monto para recargar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(!ModelUtils.esDecimalValido(montoStr)){
            menuView.InvalidateInputs("rechargeMonto");
            EstiloGral.ShowMessage("Ingrese solo nÃºmeros en el monto", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(numeroReferencia.isEmpty()){
            menuView.InvalidateInputs("rechargeRef");
            EstiloGral.ShowMessage("Ingrese un numero de referencia para recargar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } 
        else if(numeroReferencia.length() != 12){ //los numeros de referencia son de 12 digitos
            menuView.InvalidateInputs("rechargeRef");
            EstiloGral.ShowMessage("El numero de referencia debe tener 12 digitos", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        
        return flag;
    }

    private void recargarSaldo() {
        String montoStr = menuView.getMonto();
        String numeroReferencia = menuView.getNumeroReferencia();
        if (!isValidInputs(montoStr, numeroReferencia)) {
            return;
        }
        double monto = Double.parseDouble(montoStr);
        if (monto <= 0) {
            EstiloGral.ShowMessage("Ingrese un monto mayor a 0", EstiloGral.ERROR_MESSAGE);
            return;
        }
        persistenciaManager.sumarSaldo(cedula, monto);
        EstiloGral.ShowMessage("Recarga exitosa. Nuevo saldo: " + monto, EstiloGral.SUCCESS_MESSAGE);
        menuView.hideRecharge();
        menuView.updateSaldo(persistenciaManager.getSaldoFromCedula(cedula));            
    }

    private void reservarDesayuno(){
        try {
            persistenciaManager.reservarMenu(cedula, TipoMenu.DESAYUNO);
            EstiloGral.ShowMessage("Solicitud en espera", EstiloGral.SUCCESS_MESSAGE);
        } catch (Exception e) {
            EstiloGral.ShowMessage("Error al reservar desayuno: ", EstiloGral.ERROR_MESSAGE);
        }
    }

    private void reservarAlmuerzo(){
        try {
            persistenciaManager.reservarMenu(cedula, TipoMenu.ALMUERZO);
            EstiloGral.ShowMessage("Solicitud en espera", EstiloGral.SUCCESS_MESSAGE);
        } catch (Exception e) {
            EstiloGral.ShowMessage("Error al reservar almuerzo: ", EstiloGral.ERROR_MESSAGE);
        }
    }

    private void verificarFaceId(TipoMenu tipo){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una foto para verificar tu identidad");

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(menuView);

        if (result == JFileChooser.APPROVE_OPTION) {

            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            menuView.verificarFaceId(rutaArchivo);

            try {
                BufferedImage img1 = ImageIO.read(new File(rutaArchivo));
                BufferedImage img2 = ImageIO.read(new File(EstiloGral.getImgPath(cedula)));

                new Thread(() -> {

                    try {
                        Thread.sleep(2000);

                        if(ModelUtils.compararRostros(img1, img2)){
                            EstiloGral.ShowMessage("Reserva exitosa", EstiloGral.SUCCESS_MESSAGE);
                            if(tipo == TipoMenu.DESAYUNO){
                                reservarDesayuno();
                            } else {
                                reservarAlmuerzo();
                            }
                            double monto = persistenciaManager.getPrecioForUser(persistenciaManager.getRoleFromCedula(cedula));
                            menuView.updateSaldo(persistenciaManager.sumarSaldo(cedula, -monto));
                            sendReservas();
                        } else {
                            EstiloGral.ShowMessage("La verificación facial ha fallado. Reserva cancelada.", EstiloGral.ERROR_MESSAGE);
                        }

                    } catch (InterruptedException e) {
                        
                    }

                }).start();

            } catch (Exception e) {
                EstiloGral.ShowMessage("Error al procesar la imagen. Reserva cancelada.", EstiloGral.ERROR_MESSAGE);
            }
            
        } else {
            EstiloGral.ShowMessage("No se seleccionó ninguna foto. Reserva cancelada.", EstiloGral.ERROR_MESSAGE);
        }
    }

    private void iniciarReserva(TipoMenu tipo){
        Reserva.EstadoIntento resultado = persistenciaManager.intentarReservar(cedula, tipo);
        switch (resultado) {
            case YA_TIENE_RESERVA -> EstiloGral.ShowMessage("Ya tienes una reserva para este menú", EstiloGral.INFO_MESSAGE);
            case SALDO_INSUFICIENTE -> EstiloGral.ShowMessage("No tienes suficiente saldo para reservar este menú", EstiloGral.INFO_MESSAGE);
            case RESERVA_EXITOSA -> {
                verificarFaceId(tipo);
            }
            case RESERVA_CANCELADA -> EstiloGral.ShowMessage("Tu reserva ha sido cancelada", EstiloGral.INFO_MESSAGE);
            case NO_HAY_CUPO -> EstiloGral.ShowMessage("No hay cupo disponible para este menú", EstiloGral.INFO_MESSAGE);
            case NO_HAY_MENU -> EstiloGral.ShowMessage("No hay menú disponible para este tipo", EstiloGral.INFO_MESSAGE);
            case ERROR_DESCONOCIDO -> EstiloGral.ShowMessage("Ha ocurrido un error desconocido al intentar reservar", EstiloGral.ERROR_MESSAGE);
        }
    }

}