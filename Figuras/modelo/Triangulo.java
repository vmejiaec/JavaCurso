
package modelo;

public class Triangulo extends Figura{
  private int ladoA;
  private int ladoB;
  private int ladoC;
  private int base;
  private int altura;

  public Triangulo(int ladoA, int ladoB, int ladoC, int base, int altura){
    this.ladoA = ladoA;
    this.ladoB = ladoB;
    this.ladoC = ladoC;
    this.base = base;
    this.altura = altura;
  }

  public Triangulo(String ladoA, String  ladoB, String  ladoC, String  base, String  altura){
    this.ladoA = Integer.parseInt(ladoA);
    this.ladoB = Integer.parseInt(ladoB);
    this.ladoC = Integer.parseInt(ladoC);
    this.base = Integer.parseInt(base);
    this.altura = Integer.parseInt(altura);
  }

  @Override
  public Double area (){
    double respuesta = (base * altura )/2;
    return respuesta;
  }

  @Override
  public Double perimetro(){
    double respuesta = ladoA + ladoB + ladoC;
    return respuesta;
  }

  @Override
  public String etiqueta(){
      return "Triángulo";
   }


}





