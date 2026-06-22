import java.awt.*;
import javax.swing.*;

public class AppWindows{
    public static void main(String[] args)
    {
        JFrame ventana = new JFrame();
        // Configuraciòn de la ventan
        ventana.setSize(500,400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new GridLayout(4,2,20,20));
        // Crear las filas
        JLabel lblCodigo = new JLabel("Código: ");
        JTextField txtCodigo = new JTextField("");

        JLabel lblNombre = new JLabel("Nombre: ");
        JTextField txtNombre = new JTextField("");

        JLabel lblLaboratorio = new JLabel("Laboratorio: ");
        JTextField txtLaboratorio = new JTextField("");

        JButton boton = new JButton("ACEPTAR");

        // Colocar los objetos en la ventana
        ventana.add(lblCodigo);
        ventana.add(txtCodigo);

        ventana.add(lblNombre);
        ventana.add(txtNombre);

        ventana.add(lblLaboratorio);
        ventana.add(txtLaboratorio);

        ventana.add(boton);

        // Publicar la ventana
        ventana.setVisible(true);
    }
}
