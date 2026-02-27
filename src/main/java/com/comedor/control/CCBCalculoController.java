package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.GestionarCCBView;

public class CCBCalculoController {
    private final NavigationDelegate delegate;
    private final GestionarCCBView calcularCCBView;
    private final PersistenciaManager persistenciaManager;

    public CCBCalculoController(GestionarCCBView calcularCCBView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.calcularCCBView = calcularCCBView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setup();
    }

    private void setup() {

        Double ccb = persistenciaManager.getCCB();
        if (ccb != null) {
            calcularCCBView.setCCB(ccb);
        }

        calcularCCBView.find("backBtn").onClick(b -> {
            gotoPanelAdminView();
        });

        calcularCCBView.find("calcularBtn").onClick(b -> {
            calcularCCB();
        });

        calcularCCBView.find("clearBtn").onClick(b -> {
            calcularCCBView.limpiarFormulario();
        });
        
    }

    private void calcularCCB() {
        
        String CFStr = calcularCCBView.getCostosFijos();
        String CVStr = calcularCCBView.getCostosVariables();
        String NBStr = calcularCCBView.getCantidadBandejas();
        String porcMermaStr = calcularCCBView.getPorcentajeMerma();

         if (!IsValidInputs(CFStr, CVStr, NBStr, porcMermaStr)){
            EstiloGral.ShowMessage("Datos invalidos", EstiloGral.ERROR_MESSAGE); 
            return;
        }

        double CF = Double.parseDouble(CFStr);
        double CV = Double.parseDouble(CVStr);
        int NB = Integer.parseInt(NBStr);
        double porcMerma = Double.parseDouble(porcMermaStr);

        
        double precioCCB = procesarCalculo(CF, CV, NB, porcMerma);
        persistenciaManager.guardarCCB(precioCCB);
        EstiloGral.ShowMessage("CCB guardado exitosamente", EstiloGral.SUCCESS_MESSAGE); 
        gotoPanelAdminView();
    }

    private void gotoPanelAdminView() {
        delegate.onAdminPanelRequested();
    }

    public double procesarCalculo(double CF, double CV, int NB, double porcMerma) {
        porcMerma = porcMerma / 100.0;

        if (NB <= 0) {
            EstiloGral.ShowMessage("La cantidad de bandejas debe ser mayor a cero.", EstiloGral.ERROR_MESSAGE);
            return 0.0;
        }
        double costosTotales = CF + CV;
        double costoPorBandeja = costosTotales / NB;
        double CCB = costoPorBandeja * (1 + porcMerma);
        return CCB;
    }

    private boolean IsValidInputs (String CF, String CV, String NB, String porcMerma){

        boolean flag = true;

        if(CF.isEmpty() || !ModelUtils.esDecimalValido(CF)){
            flag = false;
            calcularCCBView.InvalidateInputs("costosFijos");
        }
        if(CV.isEmpty() || !ModelUtils.esDecimalValido(CV)){
            flag = false;
            calcularCCBView.InvalidateInputs("costosVariables");
        }
        if(NB.isEmpty() || !ModelUtils.esEnteroValido(NB)){
            flag = false;
            calcularCCBView.InvalidateInputs("cantidadBandejas");        
        }
        if(porcMerma.isEmpty() || !ModelUtils.esDecimalValido(porcMerma)){
            flag = false;
            calcularCCBView.InvalidateInputs("porcentajeMerma");
        }
        return flag;
    }

}