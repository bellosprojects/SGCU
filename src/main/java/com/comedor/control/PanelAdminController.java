package com.comedor.control;

import java.util.Queue;

import com.comedor.model.Menu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Prices;
import com.comedor.model.Reserva;
import com.comedor.view.EstiloGral;
import com.comedor.view.PanelAdminView;
import aura.core.AuraBox;

import aura.layouts.AuraColumn;

public class PanelAdminController {
    private final NavigationDelegate delegate;
    private final PanelAdminView panelAdminView;
    private final PersistenciaManager persistenciaManager;

    public PanelAdminController(PanelAdminView panelAdminView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.panelAdminView = panelAdminView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
        sendData();
        sendReservas();
    }

    private void sendData(){
        
        Double ccb = persistenciaManager.getCCB();
        Double student = persistenciaManager.getPorcentajeFromRole("Estudiante");
        Double teacher = persistenciaManager.getPorcentajeFromRole("Profesor");
        Double worker = persistenciaManager.getPorcentajeFromRole("Trabajador");

        panelAdminView.setPrices(new Prices(student, teacher, worker, ccb));

        Menu desayuno = persistenciaManager.getMenu(Menu.TipoMenu.DESAYUNO);
        String platoDesayuno = desayuno == null? null : desayuno.getPlato();

        Menu almuerzo = persistenciaManager.getMenu(Menu.TipoMenu.ALMUERZO);
        String platoAlmuerzo = almuerzo == null? null : almuerzo.getPlato();

        panelAdminView.setMenus(platoDesayuno, platoAlmuerzo);
    }

    private void sendReservas(){
         Queue<Reserva> almmuerzoRes = persistenciaManager.AlmuerzoWaitingQueue();

        panelAdminView.setReservas(almmuerzoRes);

        AuraColumn reservasColumn = (AuraColumn) panelAdminView.find("reservas");

        for(AuraBox<?> b : panelAdminView.findAll("cancelarBtn")){
            b.onClick(button -> {
                AuraColumn parent = (AuraColumn) button.getParent().getParent();
                String cedula = parent.getId();
                persistenciaManager.cancelarReserva(cedula, Menu.TipoMenu.ALMUERZO);
                reservasColumn.remove(parent);
                reservasColumn.revalidate();
                EstiloGral.ShowMessage("Reserva cancelada exitosamente.", EstiloGral.SUCCESS_MESSAGE);
            });
        }

        for(AuraBox<?> b : panelAdminView.findAll("confirmarBtn")){
            b.onClick(button -> {
                AuraColumn parent = (AuraColumn) button.getParent().getParent();
                String cedula = parent.getId();
                persistenciaManager.aceptarReserva(cedula, Menu.TipoMenu.ALMUERZO);
                reservasColumn.remove(parent);
                reservasColumn.revalidate();
                EstiloGral.ShowMessage("Reserva confirmada exitosamente.", EstiloGral.SUCCESS_MESSAGE);
            });
        }
    }

    private void actualizarTarifa() {

        String newPorcentaje = panelAdminView.getPorcentaje();
        if (newPorcentaje == null || newPorcentaje.trim().isEmpty()) {
            panelAdminView.InvalidateInputs("porcentaje");
            return;
        }

        try {
            String Role = panelAdminView.getRole();

            double parsedPorcentaje = Double.parseDouble(newPorcentaje);
            if (parsedPorcentaje < 0) {
                EstiloGral.ShowMessage("Porcentaje no puede ser negativo.", EstiloGral.ERROR_MESSAGE);
                return;
            }
            persistenciaManager.guardarTarifa(parsedPorcentaje, Role);
            sendData();
            EstiloGral.ShowMessage("Tarifa actualizada exitosamente para " + Role, EstiloGral.SUCCESS_MESSAGE);

        } catch (NumberFormatException ex) {
            EstiloGral.ShowMessage("Por favor, ingresa un número válido para el porcentaje.", EstiloGral.ERROR_MESSAGE);
        }
    }

    private void setupListeners() {
        
        panelAdminView.find("backBtn").onClick(b -> 
            gotoLoginView()
        );

        panelAdminView.find("ccbBtn").onClick(b -> 
            goToCalcularCCB()
        );

        panelAdminView.find("menuBtn").onClick(b -> 
            goToMenuGestion()
        );

        panelAdminView.find("update").onClick(b -> 
            actualizarTarifa()
        );

    }

    private void gotoLoginView() {
        delegate.onBackToLoginRequested();
    }

    private void goToCalcularCCB() {
        delegate.onCalcularCCBRequested();
    }

    private void goToMenuGestion() {
        delegate.onGestionarMenuRequested();
    }

}