package modelo;

public class Cuadrado{
    private int lado;

    public Cuadrado(int lado){
        this.lado = lado;
    }

    public Cuadrado(String lado){
        this.lado = Integer.parseInt( lado);
    }

    public int area(){
        return lado * lado;
    }

    public int perimetro(){
        return 4 * lado;
    }

    public String estado(){
        return "Cuadrado de área: "+ area() + " y perímetro: " + perimetro();
    }

}







