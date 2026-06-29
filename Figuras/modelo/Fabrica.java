package modelo;

public class Fabrica{

  public Figura crear(String linea){

        Object[] campos = linea.split(";");

        String tipoFigura = campos[0].toString();
        switch(tipoFigura){
            case "Cuadrado":
                Cuadrado cuadrado = new Cuadrado(campos[1].toString());
                return cuadrado;
            case "Triangulo":
                Triangulo triangulo = new Triangulo(
                    campos[1].toString(),
                    campos[2].toString(),
                    campos[3].toString(),
                    campos[4].toString(),
                    campos[5].toString()
                );
                return triangulo;
            case "Circulo":
                Circulo circulo = new Circulo(campos[1].toString());
                return circulo;
        }
        return null;
  }

}
