package com.comedor.model;

public class Menu{
    private Boolean tipo;         //1= desayuno, 0= almuerzo
    private String plato;
    private String ingredientes;
    private String fecha;
    public Menu(String plato, String ingredientes, Boolean tipo, String fecha){
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

    public Boolean getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }
}