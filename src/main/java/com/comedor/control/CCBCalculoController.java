package com.comedor.control;

public class CCBCalculoController {
    
    public double procesarCalculo(double CF, double CV, int NB, double porcMerma) {
        porcMerma= porcMerma/100.0;

        if (NB <= 0) {
            System.out.println("Error: El número de bandejas (NB) debe ser mayor a 0");
            return 0.0; 
        }

        // Formula para calcular CCB       
        double costosTotales = CF + CV;
        double costoPorBandeja = costosTotales / NB;
        
        double CCB = costoPorBandeja * (1 + porcMerma);

        // modelo.guardarCalculoCCB("datos_comedor.txt", CCB); no entendí bien sobre como manejar el model, asi que
        //supongo q solo se llama a una funcion que guarde el valor del CCB en el txt

        return CCB;
    }

}
