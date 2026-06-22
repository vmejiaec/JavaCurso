public class Circulo{
   private int radio ;

   public Circulo(int radio){
      this.radio = radio;
   }

   public Double area(){
      return Math.PI * radio * radio;
   }

   public Double perimetro(){
      return 2 * Math.PI * radio;
   }
}
