package domain;

public class Persona{
    public String nombre;
    public String correo;
    public String telefono;
    public String titulo;

    // Constructor que genera una persona con los datos que vienen en una linea
    public Persona(String linea){
        String[] campos = linea.split(";");

        nombre = campos[0];
        correo = campos[1];
        telefono = campos[2];
        titulo = campos[3];
    }

    // Construye una cadena con el saludo a la persona
    public String saludo(){
        String saludo = "Saludos " + titulo + " " + nombre + 
                        " - " + correo +
                        " (" + telefono + ").";
        return saludo;
    }

}