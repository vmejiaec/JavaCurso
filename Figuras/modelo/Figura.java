package modelo;

public abstract class Figura{

  public abstract Double area ();

  public abstract Double perimetro();

  public abstract String etiqueta();

    public String estado(){
        return ""+etiqueta()+ " de área: "+ area() + " y perímetro: " + perimetro();
    }

}
