package victor.farmacia;

public class App{
    public static void main (String[] ags){
        System.out.println("Hola desde farmacia app");
        Operacion op = new Operacion();
        System.out.print(op.sumar(1, 2));        
    }
}