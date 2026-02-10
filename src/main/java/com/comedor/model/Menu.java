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
}