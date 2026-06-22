public class App{

   public static void main(String[] args){
    // Intancias de triangulo y cuadrado
    Triangulo dorito = new Triangulo(3,4,5,5,6);
    Cuadrado dado = new Cuadrado(5);
    Circulo ficha = new Circulo(3);

    // Publicar area y perímetro de las figuras
    int areaDorito = dorito.area();
    int perimetroDorito = dorito.perimetro();
    int areaDado = dado.area();
    int perimetroDado = dado.perimetro();

    Double areaFicha = ficha.area();
    Double perimetroFicha = ficha.perimetro();


    System.out.println("Dorito: " + areaDorito + " " + perimetroDorito);
    System.out.println("Dado: "+ areaDado + " " +perimetroDado);
    System.out.println(
        String.format("Ficha: %.2f %.2f", areaFicha, perimetroFicha) );
   }
}











