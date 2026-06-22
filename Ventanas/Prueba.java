import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Prueba{
    static class Fila{
        public JLabel lbl = new JLabel();
        public JTextField txt = new JTextField();
        public Fila(String lbl){
            this.lbl.setText(lbl);
        }
    }
    public static void main(String[] args){
        JFrame ventana = new JFrame();
        // Configura la ventan
        ventana.setTitle("Aplicación");
        ventana.setSize(800,500);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Paneles 
        // - Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BorderLayout(5,5));
        panelFormulario.setBounds(20,20,300,100);

        // - Panel de campos
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(0,2,5,5));
        panelFormulario.add(panelCampos,BorderLayout.CENTER);

        // Crear campos
        Map<String,Fila> filas = new HashMap<>();

        filas.put("Codigo",new Fila("Código"));
        filas.put("Nombre",new Fila("Nombre"));
        filas.put("Laboratorio",new Fila("Laboratorio"));
        filas.put("Tipo",new Fila("Código"));


        // Poner los campos en el panel
        for (Fila fila : filas.values()) {
            panelCampos.add(fila.lbl);
            panelCampos.add(fila.txt);
        }        

        // Poner paneles a la ventana
        ventana.add(panelFormulario);

        // Presenta la ventana
        ventana.setVisible(true);
    }
}