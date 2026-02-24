package com.comedor.model;

import com.comedor.utils.ModelUtils;

public class User {
    private String fullname;
    private String cedula;
    private String password;
    private String role;
    private String email;
    private String facultadSeleccionada;
    private Double saldo;

    public User(){
        this.fullname = "";
        this.cedula = "";
        this.password = "";
        this.role = null;
        this.email = "";
        this.facultadSeleccionada = "";
        this.saldo = 0.0;
    }

    public User(String fullname, String cedula, String password, String email, String facultadSeleccionada, Double saldo, String role){
        this.fullname = fullname; //0
        this.cedula = cedula;     //1
        this.password = password; //2
        this.email = email;       //3
        this.facultadSeleccionada = facultadSeleccionada; //4
        this.saldo = saldo;    //5
        this.role = role;    //6
    }

    public String getCedula() {
        return cedula;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    
    public String getEmail() {
        return email;
    }
    public String getFacultadSeleccionada() {
        return facultadSeleccionada;
    }
    public Double getSaldo() {
        return saldo;
    }
    public Boolean saldoSuficiente(double precio){
        return this.saldo >= precio;
    }
    public String getNombres() {
        return fullname;
    }

    public String toJson(){

        try{

            return String.format(
                "{\"name\":\"%s\",\"ci\":\"%s\",\"pass\":\"%s\",\"email\":\"%s\",\"facultad\":\"%s\",\"saldo\":\"%s\",\"role\":\"%s\"}",
                fullname,
                cedula,
                ModelUtils.encriptar(password),
                email,
                facultadSeleccionada,
                saldo.toString(),
                role
            );

        } catch (Exception e){
            return "{}";
        } 
    }

    public void fromJSON(String jsonUser){

        String clean = jsonUser.replace("{", "").replace("}", "").replace("\"", "");
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
                case "name":
                    fullname = value;
                    break;
                case "ci":
                    cedula = value;
                    break;
                case "pass":
                    password = value;
                    break;
                case "email":
                    email = value;
                    break;
                case "facultad":
                    facultadSeleccionada = value;
                    break;
                case "saldo":
                    saldo = Double.parseDouble(value);
                    break;
                case "role":
                    role = value;
                    break;
                default:
                    break;
            }
        }

    }
}
