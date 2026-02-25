package com.comedor.control;

import javax.swing.SwingUtilities;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;
import com.comedor.view.EstiloGral;
import com.comedor.view.UserMenuView;

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

    private void sendUser() {
        User user = new User(
            persistenciaManager.getNameFromCedula(cedula),
            cedula,
            "",
            "",
            "",
            persistenciaManager.getSaldoFromCedula(cedula),
            persistenciaManager.getRoleFromCedula(cedula)
        );

        this.tipoUsuario = user.getRole();
        menuView.setUser(user);
    }

    private void setup() {
        
        menuView.find("backBtn").onClick(b -> 
            delegate.onBackToLoginRequested()
        );

        menuView.find("rechargeBtn").onClick(b -> {
            menuView.showRecharge();
        }
        );

        SwingUtilities.invokeLater(() -> 

            menuView.getModal().find("confirmRechargeBtn").onClick(b -> {
                recargarSaldo();
            })
            
        );

        menuView.find("bookBreakfastBtn").onClick(b -> {
            reservarDesayuno();
        }
        );

        menuView.find("bookLunchBtn").onClick(b -> {
            reservarAlmuerzo();
        }
        );

        sendUser();
        sendMenu();
    }


    private boolean isValidInputs(String montoStr, String numeroReferencia) {
        boolean flag = true;
        if(montoStr.isEmpty() ){
            menuView.InvalidateInputs("rechargeMonto");
            EstiloGral.ShowMessage("Ingrese un monto para recargar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        else if(!isAllNumbers(montoStr)){
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

        double nuevoSaldo = monto + persistenciaManager.getSaldoFromCedula(cedula);
        persistenciaManager.recargarSaldo(cedula, nuevoSaldo);
        EstiloGral.ShowMessage("Recarga exitosa. Nuevo saldo: " + nuevoSaldo, EstiloGral.SUCCESS_MESSAGE);
        menuView.hideRecharge();
        menuView.updateSaldo(nuevoSaldo);            
    }


    public boolean isAllNumbers(String str) {
        return str != null && str.matches("\\d+(\\.\\d+)?");
    }
    private void reservarDesayuno(){
        if(persistenciaManager.reservarMenu(cedula, TipoMenu.DESAYUNO)){
            EstiloGral.ShowMessage("Solicitud en espera", EstiloGral.INFO_MESSAGE);
        }
        else{
            EstiloGral.ShowMessage("Cupo ya solicitado o Cupo cancelado", EstiloGral.INFO_MESSAGE);
        }
    }

    private void reservarAlmuerzo(){
        if(persistenciaManager.reservarMenu(cedula, TipoMenu.ALMUERZO)){
            EstiloGral.ShowMessage("Solicitud en espera", EstiloGral.INFO_MESSAGE);
        }
        else{
            EstiloGral.ShowMessage("Cupo ya solicitado o Cupo cancelado", EstiloGral.INFO_MESSAGE);
        }
    }
}