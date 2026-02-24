package com.comedor.model;

public class Menu{
    private String plato;
    private String ingredientes;
    private String fecha;
    private TipoMenu tipo;
    private String cuposDisponibles;

    public enum TipoMenu {
        DESAYUNO, 
        ALMUERZO
    }

    public Menu(){
        this.plato = "";
        this.ingredientes = "";
        this.fecha = "";
        this.tipo = null;
        this.cuposDisponibles= "0";
    }

    public Menu(String plato, String ingredientes, TipoMenu tipo, String fecha, String cupos){
        this.plato = plato;
        this.ingredientes = ingredientes;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cuposDisponibles= cupos;
    }

    public String getPlato() {
        return plato;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public TipoMenu getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCupos(){
        return cuposDisponibles;
    }

    public void sustraerCupos(){
        cuposDisponibles= String.valueOf(Integer.parseInt(cuposDisponibles)-1);
    }

    public void añadirCupos(){
        cuposDisponibles= String.valueOf(Integer.parseInt(cuposDisponibles)+1);
    }

    public boolean isValidMenu(){
        return plato != null && ingredientes != null && tipo != null && fecha != null;
    }


    //Falta añadir el cupos
    public String toJson(){
        return String.format("{\"cuposDisponibles\":\"%s\",\"plato\":\"%s\",\"ingredientes\":\"%s\",\"fecha\":\"%s\",\"tipo\":\"%s\"}",
            cuposDisponibles,
            plato,
            ingredientes,
            fecha
        );
    }

    public void fromJson(String jsonMenu){
        String clean = jsonMenu.replace("{", "").replace("}", "").replace("\"", "");
        String[] pares = clean.split(",");

        for(String par : pares){

            String[] kv = par.split(":");
            if (kv.length < 2) continue; // Evita errores si el par está vacío
            String key = kv[0].trim();
            String value = kv[1].trim();

            switch (key) {
                case "plato":
                    plato = value;
                    break;
                case "ingredientes":
                    ingredientes = value;
                    break;
                case "fecha":
                    fecha = value;
                    break;
                case "cuposDisponibles":
                    cuposDisponibles = value;
                    break;
                default:
                    break;
            }
        }
    }

    public void setTipo(TipoMenu tipo){
        this.tipo = tipo;
    }
}