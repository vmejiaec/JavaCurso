package victor.farmacia.model;

public class Usuario {
    private Integer id;
    private String nombre;
    private String email;


    // Constructores
    public Usuario() {
    }

    public Usuario(Integer id, String nombre, String email){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters
    public Integer getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
}