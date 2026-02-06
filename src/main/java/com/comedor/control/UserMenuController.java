package com.comedor.control;
import com.comedor.view.UserMenuView;
import com.comedor.model.*;

public class UserMenuController{
    private PersistenciaManager persistenciaManager;      
    private UserMenuView menuView;                            
    private String cedula;             

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView){ 
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
    }

    public void sendData(){
        User user = persistenciaManager.getUserFromCedula(this.cedula);
        Menu menuDiario = persistenciaManager.getMenu();
        menuView.setAlmuerzo(menuDiario);
        menuView.setDesayuno(menuDiario);
        menuView.setUser(user);
    }                               
}