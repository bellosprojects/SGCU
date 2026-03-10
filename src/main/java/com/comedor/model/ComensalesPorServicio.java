package com.comedor.model;
import com.comedor.model.User.Role;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ComensalesPorServicio {
    private String fecha= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private int cantidadTrabajador;
    private int cantidadProfesor;
    private int cantidadEstudiante;
    private int cantidadExonerado;
    private int cantidadBecario;

    public ComensalesPorServicio() {
        cantidadBecario= 0;
        cantidadExonerado= 0;
        cantidadEstudiante= 0;
        cantidadProfesor= 0;
        cantidadTrabajador= 0;
    }

    public String toJson(){
        return String.format("{\"Fecha\":\"%s\"<>\"Trabajador\":\"%d\"<>\"Profesor\":\"%d\"<>\"Estudiante\":\"%d\"<>\"Exonerado\":\"%d\"<>\"Becario\":\"%d\"}",
            fecha,
            cantidadTrabajador,
            cantidadProfesor,
            cantidadEstudiante,
            cantidadExonerado,
            cantidadBecario
        );
    }

    public void fromJSON(String jsonComensales){
        String clean = jsonComensales.replace("{", "").replace("}", "").replace("\"", "");
        String[] pares = clean.split("<>");

        for(String par : pares){

            String[] kv = par.split(":");

            switch(kv[0]){
                case "Trabajador":
                    cantidadTrabajador = Integer.parseInt(kv[1]);
                    break;
                case "Profesor":
                    cantidadProfesor = Integer.parseInt(kv[1]);
                    break;
                case "Estudiante":
                    cantidadEstudiante = Integer.parseInt(kv[1]);
                    break;
                case "Exonerado":
                    cantidadExonerado = Integer.parseInt(kv[1]);
                    break;
                case "Becario":
                    cantidadBecario = Integer.parseInt(kv[1]);
                    break;
            }
        }
    }

    public void sumarComensal(Role role){
        switch (role) {
            case ESTUDIANTE:
                cantidadEstudiante++;
                break;
            case PROFESOR:
                cantidadProfesor++;
                break;
            case TRABAJADOR:
                cantidadTrabajador++;
                break;
            case EXONERADO:
                cantidadExonerado++;
                break;
            case BECARIO:
                cantidadBecario++;
                break;
            default:
                break;
        }
    }
}