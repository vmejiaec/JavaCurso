package modelo;

public class Cuadrado extends Figura{
    private int lado;

    public Cuadrado(int lado){
        this.lado = lado;
    }

    public Cuadrado(String lado){
        this.lado = Integer.parseInt( lado);
    }

    @Override
    public Double area(){
        double respuesta = lado * lado;
        return respuesta;
    }

    @Override
    public Double perimetro(){
        double respuesta = 4 * lado;
        return respuesta;
    }

    @Override
    public String etiqueta(){
      return "Cuadrado";
   }


}













