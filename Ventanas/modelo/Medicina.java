package modelo;

public class Medicina{
   public String codigo;
   public String nombre;
   public String farmaceutica;
   public String tipo;
   public String cantidad;
   public String precio;

   public Medicina(String codigo, String nombre, String farmaceutica, String tipo, String cantidad, String precio){
      this.codigo = codigo;
      this.nombre = nombre;
      this.farmaceutica = farmaceutica;
      this.tipo = tipo;
      this.cantidad = cantidad;
      this.precio = precio;
   }

   // Retorna el contenido en un String
   public String toString(){
      String resultado;
      resultado = 
         " - " + codigo +
         " " + nombre +
         " " + farmaceutica +
         " " + tipo +
         " " + cantidad +
         " " + precio;
      return resultado;
   }

   // Retrona el contenido en un Object Array
   public Object[] toArray(){
      return new Object[] {codigo, nombre, farmaceutica, tipo, cantidad, precio};
   }

}












