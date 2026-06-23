
package modelo;

public class Circulo{
   private int radio ;

   public Circulo(int radio){
      this.radio = radio;
   }

   public Circulo(String radio){
      this.radio = Integer.parseInt(radio);
   }

   public Double area(){
      return Math.PI * radio * radio;
   }

   public Double perimetro(){
      return 2 * Math.PI * radio;
   }

   public String estado(){
        return "Círculo de área: "+ area() + " y perímetro: " + perimetro();
    }
}
