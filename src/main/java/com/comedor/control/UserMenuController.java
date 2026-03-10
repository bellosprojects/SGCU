package com.comedor.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Reserva;
import com.comedor.model.User;
import com.comedor.model.User.Role;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.UserMenuView;

import aura.layouts.AuraRow;

public class UserMenuController {
    private final NavigationDelegate delegate;
    private final PersistenciaManager persistenciaManager;
    private final UserMenuView menuView;
    private String cedula;

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView, NavigationDelegate delegate) {
        this.delegate = delegate;
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
        setup();
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.equals(this.cedula)) {
            return;
        }
        this.cedula = cedula;
        refreshView();
    }

    private void refreshView() {
        menuView.clearReservas();
        sendUser();
        sendMenu();
        loadReservas();
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
        Double precioFinal = (persistenciaManager.getPrecioForUser(cedula));
        menuView.setPrecio(precioFinal);
    }

    private void sendUser() {
        User user = persistenciaManager.getUserFromCedula(cedula); 
        if (user != null) {
            menuView.setUser(user);
            double precio = persistenciaManager.getPrecioForUser(cedula);
            menuView.setPrecio(precio);
        }
    }

    private void setup() {
        
        menuView.find("backBtn").onClick(b -> 
            delegate.onBackToLoginRequested()
        );

        menuView.find("rechargeBtn").onClick(b -> {
            Role rolUser = persistenciaManager.getRoleFromCedula(cedula);
                if(rolUser == Role.EXONERADO){

                    EstiloGral.ShowMessage("Eres exonerado, no tienes habilitado la opcion de recargar saldo", EstiloGral.ERROR_MESSAGE);
                } else {
                    menuView.showRecharge();  
                }
            
        });

        SwingUtilities.invokeLater(() -> {

            menuView.getModalRecargar().find("confirmRechargeBtn").onClick(b -> {
                recargarSaldo();
            });

            menuView.getModalSaldoPana().find("confirmRechargeSaldoPanaBtn").onClick(b -> {
                String cedulaPana = menuView.getCedulaForSaldoPana();
                if(isValidCedula(cedulaPana))
                    recargarSaldoPana(cedulaPana);
            });
            
        });

        menuView.find("rechargeSaldoPanaBtn").onClick(b -> {
            menuView.showSaldoPana(); 
        });


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

        refreshView();
    }

    private void loadReservas() {
        Reserva desayunoRev = persistenciaManager.getReservaFromCedula(cedula, TipoMenu.DESAYUNO);
        sendReserva(desayunoRev, TipoMenu.DESAYUNO);
        Reserva almuerzoRev = persistenciaManager.getReservaFromCedula(cedula, TipoMenu.ALMUERZO);
        sendReserva(almuerzoRev, TipoMenu.ALMUERZO);
    }

    private void sendReserva(Reserva rev, TipoMenu tipoRev){ 

        if(rev == null) return;

        AuraRow reservaCard = menuView.createReservaCard(rev, tipoRev);

        if(rev.getEstadoReserva() != Reserva.EstadoReserva.CANCELADO){
            reservaCard.find("cancelarReserva").onClick(b -> {
                reservaCard.setVisible(false);
                sendReserva(new Reserva(cedula, Reserva.EstadoReserva.CANCELADO), tipoRev);
                persistenciaManager.cancelarReserva(cedula, tipoRev);
                EstiloGral.ShowMessage("Reserva cancelada", EstiloGral.SUCCESS_MESSAGE);
                menuView.updateSaldo(persistenciaManager.getSaldoFromCedula(cedula));
            });
        }

        menuView.addReserva(reservaCard, tipoRev);
        
    }

    public boolean isValidInputs(String montoStr, String numeroReferencia) {
        boolean flag = true;
        if(montoStr == null ||  montoStr.trim().isEmpty()){
            menuView.InvalidateInputs("rechargeMonto");
            EstiloGral.ShowMessage("Ingrese un monto para recargar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(!ModelUtils.esDecimalValido(montoStr)){
            menuView.InvalidateInputs("rechargeMonto");
            EstiloGral.ShowMessage("Ingrese solo numeros en el monto", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(numeroReferencia.trim().isEmpty()){
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

    public boolean isValidMontoForSaldoPana(String montoStr) {
        boolean flag = true;
        if(montoStr == null ||  montoStr.trim().isEmpty()){
            menuView.InvalidateInputs("monto");
            EstiloGral.ShowMessage("Ingrese un monto para recargar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(!ModelUtils.esDecimalValido(montoStr)){
            menuView.InvalidateInputs("monto");
            EstiloGral.ShowMessage("Ingrese solo numeros en el monto", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        double monto = Double.parseDouble(montoStr);
        if (monto <= 0) {
            menuView.InvalidateInputs("monto");
            EstiloGral.ShowMessage("Ingrese un monto mayor a 0", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }

        Double montoActual = persistenciaManager.getSaldoFromCedula(cedula);
        if(monto > montoActual){
            menuView.InvalidateInputs("monto");
            EstiloGral.ShowMessage("No tienes suficiente saldo para transferir ese monto, tu saldo actual es: " + montoActual, EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        
        return flag;
    }

    public double recargarSaldo() { //Para el propio usuario
        String montoStr = menuView.getMonto();
        String numeroReferencia = menuView.getNumeroReferencia();
        if (!isValidInputs(montoStr, numeroReferencia)) {
            return -1;
        }
        double monto = Double.parseDouble(montoStr);
        if (monto <= 0) {
            EstiloGral.ShowMessage("Ingrese un monto mayor a 0", EstiloGral.ERROR_MESSAGE);
            return -1;
        }
        persistenciaManager.sumarSaldo(cedula, monto);
        EstiloGral.ShowMessage("Recarga exitosa. Saldo recargado: " + monto, EstiloGral.SUCCESS_MESSAGE);
        
        menuView.hideRecharge();
        menuView.updateSaldo(persistenciaManager.getSaldoFromCedula(cedula));  
        return monto;          
    }

    public double recargarSaldoPana(String cedulaInput) { //para saldo pana, le sumo a saldo de ese usuario y me descuento a mi ese monto
        String montoStr = menuView.getMontoForSaldoPana();
        String confirmarcion = menuView.getConfirmacionSaldoPana();
        
        if(confirmarcion == null || !persistenciaManager.autenticar(cedula, confirmarcion)){
            menuView.InvalidateInputs("password");
            EstiloGral.ShowMessage("Contraseña ingresada incorrecta, vuelva a intentarlo", EstiloGral.ERROR_MESSAGE);
            return -1;
        }

        if (!isValidMontoForSaldoPana(montoStr)) {
            return -1; }
        Double monto = Double.parseDouble(montoStr);
        persistenciaManager.sumarSaldo(cedulaInput, monto);
        EstiloGral.ShowMessage("Recarga exitosa para " + cedulaInput + ". Saldo recargado: " + monto, EstiloGral.SUCCESS_MESSAGE);
        
        menuView.hideSaldoPana();
        persistenciaManager.getSaldoFromCedula(cedulaInput);
        menuView.updateSaldo(persistenciaManager.sumarSaldo(cedula, -monto));  
        return monto;          
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
        EstiloGral.ShowMessage("Reserva en espera", EstiloGral.SUCCESS_MESSAGE);
        sendReserva(new Reserva(cedula, Reserva.EstadoReserva.EN_ESPERA), tipo);
        if(tipo == TipoMenu.DESAYUNO){
            reservarDesayuno();
        } else {
            reservarAlmuerzo();
        }
        double monto = persistenciaManager.getPrecioForUser(cedula);
        menuView.updateSaldo(persistenciaManager.sumarSaldo(cedula, -monto));
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

    private boolean isValidCedula(String cedulaInput){
        boolean flag = true;
        if (cedulaInput == null || cedulaInput.isEmpty()) {
            menuView.InvalidateInputs("cedulaSaldoPana");
            EstiloGral.ShowMessage("Ingrese una cedula para continuar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if(!ModelUtils.esEnteroValido(cedulaInput)){
            menuView.InvalidateInputs("cedulaSaldoPana");
            EstiloGral.ShowMessage("La cedula solo debe contener numeros", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if (!persistenciaManager.isCedulaRegistered(cedulaInput)) {
            menuView.InvalidateInputs("cedulaSaldoPana");
            EstiloGral.ShowMessage("Esta cedula no esta registrada en la base de datos", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if(cedulaInput.equals(this.cedula)){
            menuView.InvalidateInputs("cedulaSaldoPana");
            EstiloGral.ShowMessage("No puedes recargar saldo pana a tu propia cedula, ingresa la cedula de otro usuario", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        Role rolUser = persistenciaManager.getRoleFromCedula(cedulaInput);
        if(rolUser == Role.EXONERADO){
            menuView.InvalidateInputs("cedulaSaldoPana");
            EstiloGral.ShowMessage("Este usuario es exonerado, no es elegible para recargarle saldo", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        
        return flag;

    }
}