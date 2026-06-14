public class Medicina{
    private String nombre;
    private String laboratorio;
    private String tipo;
    private int cantidad;
    private Double precio;

    public Medicina(
        String nombre,
        String laboratorio,
        String tipo,
        String cantidad,
        String precio)
    {
        this.nombre = nombre;
        this.laboratorio = laboratorio;
        this.tipo = tipo;
        this.cantidad = Integer.parseInt(cantidad);
        this.precio = Double.parseDouble(precio);
    }

    public String toString(){
        return 
            " - " + nombre +
            " " + laboratorio +
            " " + tipo +
            " " + cantidad +
            " " + precio ;
    }
}