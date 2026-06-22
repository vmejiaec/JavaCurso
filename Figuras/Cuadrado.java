public class Cuadrado{
    private int lado;

    public Cuadrado(int lado){
        this.lado = lado;
    }

    public int area(){
        return lado * lado;
    }

    public int perimetro(){
        return 4 * lado;
    }
}
