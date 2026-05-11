package victor.farmacia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperacionTest {
    @Test
    void sumar_1_mas_2(){
        Operacion op = new Operacion();
        int res = op.sumar(1,2);
        assertEquals(3, res);
    }

    @Test
    void sumar_2_menos_3(){
        Operacion op = new Operacion();
        int res = op.sumar(2, -3);
        assertEquals(-1,res);
    }
}
