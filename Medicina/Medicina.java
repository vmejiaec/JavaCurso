public class Medicina{
   public String nombre;
   public String farmaceutica;
   public String cantidad;
   public String precio;

   public Medicina(String nombre, String farmaceutica, String cantidad, String precio){
      this.nombre = nombre;
      this.farmaceutica = farmaceutica;
      this.cantidad = cantidad;
      this.precio = precio;
   }

   public String toString(){
      String resultado;
      resultado = " - " + nombre +
                 " " + farmaceutica +
                 " " + cantidad +
                 " " + precio;
      return resultado;
   }
}
