package com.comedor.control;

import javax.swing.SwingUtilities;

import com.comedor.model.ComensalesPorServicio;
import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Prices;
import com.comedor.view.EstiloGral;
import com.comedor.view.PanelAdminView;

public class PanelAdminController {
    private final NavigationDelegate delegate;
    private final PanelAdminView panelAdminView;
    private final PersistenciaManager persistenciaManager;

    public PanelAdminController(PanelAdminView panelAdminView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.panelAdminView = panelAdminView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;

        SwingUtilities.invokeLater(() -> {
            setupListeners();
            sendData();
        });
    }

    public void reset(){
        panelAdminView.reset();
        sendData();
    }

    private void sendData(){
        
        Double ccb = persistenciaManager.getCCB();
        Double student = persistenciaManager.getPorcentajeFromRole("ESTUDIANTE");
        Double teacher = persistenciaManager.getPorcentajeFromRole("PROFESOR");
        Double worker = persistenciaManager.getPorcentajeFromRole("TRABAJADOR");

        panelAdminView.setPrices(new Prices(student, teacher, worker, ccb));

        Menu desayuno = persistenciaManager.getMenu(Menu.TipoMenu.DESAYUNO);
        String platoDesayuno = desayuno == null? null : desayuno.getPlato();

        Menu almuerzo = persistenciaManager.getMenu(Menu.TipoMenu.ALMUERZO);
        String platoAlmuerzo = almuerzo == null? null : almuerzo.getPlato();

        panelAdminView.setMenus(platoDesayuno, platoAlmuerzo);

        ComensalesPorServicio listadoDesayuno = persistenciaManager.getComensalesPorServicio(TipoMenu.DESAYUNO);
        panelAdminView.setListado(listadoDesayuno, TipoMenu.DESAYUNO);

        ComensalesPorServicio listadoAlmuerzo = persistenciaManager.getComensalesPorServicio(TipoMenu.ALMUERZO);
        panelAdminView.setListado(listadoAlmuerzo, TipoMenu.ALMUERZO);

        panelAdminView.setFechaCCB(persistenciaManager.getFechaCCB());
    }

    private void actualizarTarifa() {

        String newPorcentaje = panelAdminView.getPorcentaje();
        if (newPorcentaje == null || newPorcentaje.trim().isEmpty()) {
            EstiloGral.ShowMessage("Por favor, ingresa un número válido para el porcentaje.", EstiloGral.ERROR_MESSAGE);
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

        panelAdminView.find("menuBtn2").onClick(b -> 
            goToMenuGestion()
        );

        panelAdminView.find("update").onClick(b -> 
            actualizarTarifa()
        );

        panelAdminView.find("update2").onClick(b -> 
            actualizarTarifa()
        );

        panelAdminView.find("becariosBtn").onClick(b -> 
            goToGestionarBecarios()
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

    private void goToGestionarBecarios(){
        delegate.onGestionarBecariosRequested();
    }

}