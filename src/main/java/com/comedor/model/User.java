package com.comedor.model;

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
        this.role = "user";
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
}
