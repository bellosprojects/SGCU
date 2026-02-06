package com.comedor.model;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
public class PersistenciaManager {
    private final Path ruta = Path.of("C:", "SGCU", "data");
    private final Path rutaArchivo = ruta.resolve("UsuariosRegistrados.txt");
    private final Path rutaMenuDesayuno = ruta.resolve("Desayuno.txt");
    private final Path rutaMenuAlmuerzo = ruta.resolve("Almuerzo.txt");
    private final Path rutaImagenesPath = Path.of("C:", "SGCU", "imagenes");
    public PersistenciaManager(){
        crearDirectorios();
        añadirAdmin();
    }

    private void crearDirectorios() {
        try {
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
                Files.createDirectories(rutaImagenesPath);
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorios: " + e.getMessage());
        }
    }

    private void añadirAdmin() {
            User admin = new User("Administrador", "00000000", "admin123", "admin@sgcu.com", "Administración", 0.0, "admin");
            guardarUsuario(admin, null);
    }

    public void guardarUsuario(User user, String imagePath){
    String newUser= user.getNombres()+"<>"+user.getCedula()+"<>"+user.getPassword()+"<>"+user.getEmail()+"<>"+user.getFacultadSeleccionada()+"<>"+user.getSaldo()+"<>"+user.getRole();
        if(isCedulaRegistered(user.getCedula())){
            return;
        }
        
        if (imagePath != null && !imagePath.isEmpty()) {
        try {
            Path origen = Path.of(imagePath);    //se copia la ruta de la imagen en un path
            Path destino = rutaImagenesPath.resolve(user.getCedula() + ".jpg");         //se crea un nuevo path con la ruta de imagenes y el nombre de la cedula con la extension jpg
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Imagen de perfil guardada correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
        }
        }
        try{
            Files.writeString(rutaArchivo, newUser + System.lineSeparator(), 
            java.nio.charset.StandardCharsets.UTF_8,
            StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            System.out.println("Usuario guardado con éxito (UTF-8).");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserDataFromCedula(String cedula, int index){
        try{
            if(Files.exists(rutaArchivo)){                                    //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(rutaArchivo, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
                for(int i=0; i<lineas.size(); i++){                           //recorre todas las lineas del archivo                                  
                    String linea = lineas.get(i);                             //obtiene la linea i
                    String[] partes = linea.split("<>");                      //divide la linea en partes separadas por <>
                    if(partes[1].equals(cedula)){                             //si la primera parte coincide con la cedula retorna el dato solicitado
                        //System.out.println("DATO" + partes[index]);
                        return partes[index];   
                    }
                }
            }
        } catch (IOException e){ 
            e.printStackTrace();                  //caso error
    }
    return null;                 //return null si no se encuentra la cedula
    }
          
    public User getUserFromCedula(String cedula){ //crea un user con los datos obtenidos de la cedula registrada
        return new User(getUserDataFromCedula(cedula, 0),//fullname
        getUserDataFromCedula(cedula, 1),//cedula
        getUserDataFromCedula(cedula, 2),//password
        getUserDataFromCedula(cedula, 3),// email
        getUserDataFromCedula(cedula, 4),//facultad 
        Double.parseDouble(getUserDataFromCedula(cedula, 5)),//saldo                    
        getUserDataFromCedula(cedula, 6) //role
        );
    }

    private String getPasswordFromCedula(String cedula){                
            return getUserDataFromCedula(cedula,2);
    }

    public String getRoleFromCedula(String cedula) {                  
        return getUserDataFromCedula(cedula,6);
    }

    public Double getSaldoFromCedula(String cedula) {                  
        return Double.parseDouble(getUserDataFromCedula(cedula,5));
    }

    public boolean isCedulaRegistered(String cedula){                 
        return getUserDataFromCedula(cedula,1) != null; 
    }

    public boolean autenticar(String cedula, String password){
        boolean cedulaExists = isCedulaRegistered(cedula);
        String storedPassword = getPasswordFromCedula(cedula);
        return cedulaExists && storedPassword.equals(password);
    }

    public void guardarMenu(String nombre, String ingredientes, boolean tipo, String Fecha){
        String menuString= nombre+"<>"+ingredientes+"<>"+Fecha;
        if(tipo){
            try{                                                                                     //try para manejar casos de error
            Files.writeString(rutaMenuDesayuno, menuString + System.lineSeparator(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);    //Escribe el string en el archivo sin borrar los datos anteriores, en la ruta indicada, 
        } catch (IOException e){
            e.printStackTrace();       //caso de error  
        }
        }    
        else{
            try{                                                                                     //try para manejar casos de error
            Files.writeString(rutaMenuAlmuerzo, menuString + System.lineSeparator(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);    //Escribe el string en el archivo sin borrar los datos anteriores, en la ruta indicada, 
        } catch (IOException e){
            e.printStackTrace();       //caso de error
        }
        }                  
        return;
    }

    public Menu getMenu(boolean tipo){
        Menu menu = null;
        try{
            Path ruta = tipo ? rutaMenuDesayuno : rutaMenuAlmuerzo;
            if(Files.exists(ruta)){                                    //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(ruta);        //crea una lista con todas las lineas del archivo
                if(!lineas.isEmpty()){
                    String linea = lineas.get(0);                             
                    String[] partes = linea.split("<>");                      //divide la linea en partes separadas por <>
                    menu = new Menu(partes[0], partes[1], tipo, partes[2]);
                }
            }
        } catch (IOException e){ 
            e.printStackTrace();                  //caso error
    }
    return menu;
    }
}
