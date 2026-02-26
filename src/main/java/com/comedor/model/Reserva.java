package com.comedor.model;

public class Reserva {
    public enum EstadoReserva {
        RESERVADO,
        CANCELADO,
        EN_ESPERA
    }

    public static enum EstadoIntento {
        YA_TIENE_RESERVA,
        SALDO_INSUFICIENTE,
        RESERVA_EXITOSA,
        RESERVA_CANCELADA,
        NO_HAY_CUPO,
        NO_HAY_MENU,
        ERROR_DESCONOCIDO
    }

    private String userCedula;
    private EstadoReserva estadoReserva;

    public Reserva(){};

    public Reserva (String cedula, EstadoReserva estado){
        userCedula= cedula;
        estadoReserva= estado;
    }

    //hacer funciones
    public String toJSON(){
        return String.format("{\"userCedula\":\"%s\",\"estadoReserva\":\"%s\"}",
            userCedula,
            estadoReserva
        );
    }
    public void fromJSON(String jsonMenu) {
        String clean = jsonMenu.replace("{", "").replace("}", "").replace("\"", "");
        String[] pares = clean.split(",");

        for (String par : pares) {
            String[] kv = par.split(":");
            if (kv.length < 2) continue; 

            String key = kv[0].trim();
            String value = kv[1].trim();

            switch (key) {
                case "userCedula" -> this.userCedula = value;
                case "estadoReserva" -> {
                    if (!value.isEmpty()) {
                        this.estadoReserva = EstadoReserva.valueOf(value);
                    }
                }
            }
        }
    }
    public String getCedula(){
        return userCedula;
    }
    public EstadoReserva getEstadoReserva(){
        return estadoReserva;
    }
    public void setEstadoReserva(EstadoReserva nuevoEstado){
        estadoReserva= nuevoEstado;
    }
}
