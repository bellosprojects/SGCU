package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.EstiloGral;
import com.comedor.view.GestionarCCBView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CCBCalculoController implements ActionListener {
    private NavigationDelegate delegate;
    private GestionarCCBView calcularCCBView;
    private PersistenciaManager persistenciaManager;
    private double precioCCB;

    public CCBCalculoController(GestionarCCBView calcularCCBView, PersistenciaManager persistenciaManager,
            NavigationDelegate delegate) {
        this.calcularCCBView = calcularCCBView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        this.precioCCB = persistenciaManager.getCCB();
        calcularCCBView.setCCB(precioCCB);
        setupListeners();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == calcularCCBView.getGuardarButton()) {
            calcularCCB();
        } else if (e.getSource() == calcularCCBView.getBackButton()) {
            gotoPanelAdminView();
        }
    }

    void setupListeners() {
        this.calcularCCBView.getGuardarButton().addActionListener(this);
        this.calcularCCBView.getBackButton().addActionListener(this);
    }

    void calcularCCB() {
         if (!IsValidInputs()){
            EstiloGral.ShowMessage("Datos invalidos", EstiloGral.ERROR_MESSAGE); 
            return;
        }

        Double CF = Double.parseDouble(calcularCCBView.getCostosFijos());
        Double CV = Double.parseDouble(calcularCCBView.getCostosVariables());
        Integer NB = Integer.parseInt(calcularCCBView.getCantidadBandejas());
        Double porcMerma = Double.parseDouble(calcularCCBView.getPorcentajeMerma());
        
        this.precioCCB = procesarCalculo(CF, CV, NB, porcMerma);
        persistenciaManager.guardarCCB(this.precioCCB);
        EstiloGral.ShowMessage("CCB guardado exitosamenmte", EstiloGral.SUCCESS_MESSAGE); 
        gotoPanelAdminView();
    }

    void gotoPanelAdminView() {
        delegate.onAdminPanelRequested();
    }

    public double procesarCalculo(double CF, double CV, int NB, double porcMerma) {
        porcMerma = porcMerma / 100.0;

        if (NB <= 0) {
            System.out.println("Error: El número de bandejas (NB) debe ser mayor a 0");
            return 0.0;
        }
        double costosTotales = CF + CV;
        double costoPorBandeja = costosTotales / NB;
        double CCB = costoPorBandeja * (1 + porcMerma);
        return CCB;
    }

    private boolean esDecimalValido(String texto) {
    return texto.matches("^[0-9]+(\\.[0-9]+)?$");
}

    private boolean IsValidInputs (){
        String CF = calcularCCBView.getCostosFijos();
        String CV = calcularCCBView.getCostosVariables();
        String NB = calcularCCBView.getCantidadBandejas();
        String porcMerma = calcularCCBView.getPorcentajeMerma();

        boolean flag = true;

        if(CF.isEmpty() || !esDecimalValido(CF)){
            flag = false;
            calcularCCBView.InvalidateInputs(calcularCCBView.getCostosFijosComponent());
        }
        if(CV.isEmpty() || !esDecimalValido(CV)){
            flag = false;
            calcularCCBView.InvalidateInputs(calcularCCBView.getCostosVariablesComponent());
        }
        if(NB.isEmpty() || !NB.matches("^[0-9]+$")){
            flag = false;
            calcularCCBView.InvalidateInputs(calcularCCBView.getCantidadBAndejasComponent());        }
        if(porcMerma.isEmpty() || !esDecimalValido(porcMerma)){
            flag = false;
            calcularCCBView.InvalidateInputs(calcularCCBView.getPorcentajeMermaComponent());
        }
        return flag;
    }

}