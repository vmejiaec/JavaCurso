public class Medicina{
    private String nombre;
    private String farmaceutica;
    private String tipo;
    private int cantidad;
    private Double precio;

    public Medicina(
        String nombre,
        String farmaceutica,
        String tipo,
        String cantidad,
        String precio){
            this.nombre = nombre;
            this.farmaceutica = farmaceutica;
            this.tipo = tipo;
            this.cantidad = Integer.parseInt(cantidad);
            this.precio = Double.parseDouble(precio);
    }

    public String toString(){
        return 
            " - " + nombre +
            " " + farmaceutica +
            " " + tipo +
            " " + cantidad +
            " " + precio ;
    }
}