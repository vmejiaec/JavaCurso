package modelo;

public record EstadoMedicina(
    String codigo,
    String nombre,
    String farmaceutica,
    String tipo,    
    int cantidad,
    double precio    
){}