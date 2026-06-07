package domain;

import domain.*;

public class Cuadrado implements Figura{
    public double lado;

    public Cuadrado(double lado){
        this.lado = lado;
    }

    public String tipo(){
        return "cuadrado";
    }

    public double area(){
        return lado * lado;
    }

    public double perimetro(){
        return 4*lado;
    }
}