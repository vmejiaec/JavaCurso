
package modelo;

public class Circulo  extends Figura{
   private int radio ;

   public Circulo(int radio){
      this.radio = radio;
   }

   public Circulo(String radio){
      this.radio = Integer.parseInt(radio);
   }

   @Override
   public Double area(){
      return Math.PI * radio * radio;
   }

   @Override
   public Double perimetro(){
      return 2 * Math.PI * radio;
   }

   @Override
   public String etiqueta(){
      return "Círculo";
   }

}










