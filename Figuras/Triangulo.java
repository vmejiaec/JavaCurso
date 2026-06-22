public class Triangulo{
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

  public int area (){
    return (base * altura )/2;
  }

  public int perimetro(){
    return ladoA + ladoB + ladoC;
  }
}
