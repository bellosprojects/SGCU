package com.comedor.model;

public class Prices {

    private double estudianteTarifa;
    private double trabajadorTarifa;
    private double profesorTarifa;
    private double CCB;

    public Prices(){
        estudianteTarifa = 0.0;
        profesorTarifa = 0.0;
        trabajadorTarifa = 0.0;
        CCB = 0.0;
    }

    public Prices(double estudiante, double profesor, double trabajador, double ccb){
        estudianteTarifa = estudiante;
        profesorTarifa = profesor;
        trabajadorTarifa = trabajador;
        CCB = ccb;
    }

    public String toJson(){
        return String.format("{\"Estudiante\":\"%.2f\"<>\"Profesor\":\"%.2f\"<>\"Trabajador\":\"%.2f\"<>\"CCB\":\"%.2f\"}",
            estudianteTarifa,
            profesorTarifa,
            trabajadorTarifa,
            CCB
        );
    }

    public void setEstudiante(double estudiante){
        estudianteTarifa = estudiante;
    }

    public void setProfesor(double profesor){
        profesorTarifa = profesor;
    }

    public void setTrabajador(double trabajador){
        trabajadorTarifa = trabajador;
    }

    public void setCCB(double ccb){
        CCB = ccb;
    }

    public void fromJSON(String jsonPrices){
        String clean = jsonPrices.replace("{", "").replace("}", "").replace("\"", "");
        String[] pares = clean.split("<>");

        for(String par : pares){

            String[] kv = par.split(":");

            String key = kv[0].trim();

            String value = kv[1].trim().replace(",", ".");

            switch (key) {
                case "Estudiante":
                    estudianteTarifa = Double.parseDouble(value);
                    break;
                case "Profesor":
                    profesorTarifa = Double.parseDouble(value);
                    break;
                case "Trabajador":
                    trabajadorTarifa = Double.parseDouble(value);
                    break;
                case "CCB":
                    CCB = Double.parseDouble(value);
                    break;
                default:
                    break;
            }
        }
    }

    public Double getCCB(){
        return CCB;
    }

    public Double getEstudiante(){
        return estudianteTarifa;
    }

    public Double getTrabajador(){
        return trabajadorTarifa;
    }

    public Double getProfesor(){
        return profesorTarifa;
    }
    
}
