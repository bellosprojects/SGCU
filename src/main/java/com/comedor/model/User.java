package com.comedor.model;

public class User {
    private String fullname;
    private String cedula;
    private String password;
    private String role;
    private String email;
    private String facultadSeleccionada;
    private String imagePath;

    public User(){
        this.fullname = "";
        this.cedula = "";
        this.password = "";
        this.role = "user";
        this.email = "";
        this.facultadSeleccionada = "";
        this.imagePath = "";
    }

    public User(String fullname, String cedula, String password, String email, String facultadSeleccionada, String imagePath){
        this.fullname = fullname;
        this.cedula = cedula;
        this.password = password;
        this.email = email;
        this.facultadSeleccionada = facultadSeleccionada;
        this.imagePath = imagePath;
        this.role = "user";
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
    public String getImagePath() {
        return imagePath;
    }
    public String getNombres() {
        return fullname;
    }
}
