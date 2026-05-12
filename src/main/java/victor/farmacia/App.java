package victor.farmacia;

public class App{
    public static void main (String[] ags){
        System.out.println("Hola desde farmacia app");
        Operacion op = new Operacion();
        System.out.print(op.sumar(1, 2));        
        System.out.println("Prueba de conexión a la base de datos:");
        
        BaseDatos db = new BaseDatos(
            "jdbc:mysql://localhost:3306/farmacia_db", 
            "victor", 
            "victor123");
        
        for (Usuario u : db.obtenerUsuarios()) {
            System.out.println(u);  
        }
    }
}