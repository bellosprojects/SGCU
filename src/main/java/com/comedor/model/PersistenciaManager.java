package com.comedor.model;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;


public class PersistenciaManager {
    private final Path carpetaData = Path.of("src", "main", "java", "com", "comedor", "model", "Data");
    private final Path rutaArchivo = carpetaData.resolve("UsuariosRegistrados.txt");
    private final Path rutaMenuDesayuno = carpetaData.resolve("Desayuno.txt");
    private final Path rutaMenuAlmuerzo = carpetaData.resolve("Almuerzo.txt");
    public PersistenciaManager(){}

    public void guardarUsuario(User user) {
    String newUser= user.getCedula()+"<>"+user.getRole()+"<>"+user.getPassword()+"<>"+user.getNombres()+"<>"+user.getEmail()+"<>"+user.getFacultadSeleccionada(); // ... etc

    try (FileWriter escritor = new FileWriter(rutaArchivo.toFile(), true)) {
        escritor.write(newUser + System.lineSeparator());
        System.out.println("Usuario guardado con éxito.");
    } catch (IOException e) {
        System.err.println("Error al escribir en el archivo: " + e.getMessage());
        e.printStackTrace();
    }
}

    public String getUserDataFromCedula(String cedula, int index){
        try{
            if(Files.exists(rutaArchivo)){                                    //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(rutaArchivo);        //crea una lista con todas las lineas del archivo
                for(int i=0; i<lineas.size(); i++){                           //recorre todas las lineas del archivo                                  
                    String linea = lineas.get(i);                             //obtiene la linea i
                    String[] partes = linea.split("<>");                      //divide la linea en partes separadas por <>
                    if(partes[0].equals(cedula)){                             //si la primera parte coincide con la cedula retorna el dato solicitado
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
          
    public String getPasswordFromCedula(String cedula){                //pide password
        return getUserDataFromCedula(cedula,3);
    }

    public User getUserFromCedula(String cedula){                      //crea un user con los datos obtenidos de la cedula registrada
        return new User(getUserDataFromCedula(cedula, 2),
        getUserDataFromCedula(cedula, 0),
        getUserDataFromCedula(cedula, 3),
        getUserDataFromCedula(cedula, 4),
        getUserDataFromCedula(cedula, 5), 
        " "                    
        );
    }

    public String getRoleFromCedula(String cedula) {                   //pide rol
        return getUserDataFromCedula(cedula,1);
    }

    public boolean isCedulaRegistered(String cedula){                 //verifica si la cedula ya esta registrada
        return getUserDataFromCedula(cedula,0) != null; 
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
