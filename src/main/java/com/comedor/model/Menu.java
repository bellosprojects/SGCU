package com.comedor.model;

public class Menu{
    private String plato;
    private String ingredientes;
    private String fecha;
    private TipoMenu tipo;

    public enum TipoMenu {
        DESAYUNO, 
        ALMUERZO
    }


    public Menu(){
        this.plato = "";
        this.ingredientes = "";
        this.fecha = "";
        this.tipo = null;
    }

    public Menu(String plato, String ingredientes, TipoMenu tipo, String fecha){
        this.plato = plato;
        this.ingredientes = ingredientes;
        this.tipo = tipo;
        this.fecha = fecha;
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
    public boolean isValidMenu(){
        return plato != null && ingredientes != null && tipo != null && fecha != null;
    }

    public String toJson(){
        return String.format("{\"plato\":\"%s\",\"ingredientes\":\"%s\",\"fecha\":\"%s\"}",
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

            String key = kv[0].trim();
            
            String value;
            if(par.length() == key.length() + 1){
                value = "";
            } else {
                value = kv[1].trim();
            }

            switch (key) {
                case "plato":
                    plato = value;
                    break;
                case "ingredientes":
                    ingredientes = value;
                    break;
                case "Trabajador":
                    fecha = value;
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