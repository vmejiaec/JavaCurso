import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class App {

    public static void main(String[] args) throws Exception {

        // Obtener lista de medicinas
        Fuente fuente = new Fuente();
        List<String> lineas = fuente.leerURL("https://raw.githubusercontent.com/vmejiaec/JavaCurso/refs/heads/main/Leer/medicinas.txt");
        Servicio servicio = new Servicio();
        List<Medicina> medicinas  = servicio.convertir(lineas);

        // Crear la ventana de medicinas
        JFrame ventana = new JFrame("Farmacia Salud Total");
        ventana.setSize(900, 500);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BorderLayout(5,5));

        // Panel de campos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));        

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1,3,15,15));

        // Panel para la tabla de medicinas
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout(5,5));

        String[] columnas = {"Codigo", "Nombre", "Laboratorio", "Tipo", "Cantidad", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        for (Medicina medicina : medicinas) {
            modelo.addRow( medicina.toArray());
        }
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panelTabla.add(scroll, BorderLayout.CENTER);

        // Campos
        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField("A12");

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField("Acetaminofen");

        JLabel lblLaboratorio = new JLabel("Laboratorio");
        JTextField txtLaboratorio = new JTextField("Bayer");

        JLabel lblTipo = new JLabel("Tipo");
        JTextField txtTipo = new JTextField("Genérico");

        JLabel lblCantidad = new JLabel("Cantidad");
        JTextField txtCantidad = new JTextField("23");

        JLabel lblPrecio = new JLabel("Precio");
        JTextField txtPrecio = new JTextField("0.34");

        // Agregar campos al formulario
        panel.add(lblCodigo);
        panel.add(txtCodigo);

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblLaboratorio);
        panel.add(txtLaboratorio);

        panel.add(lblTipo);
        panel.add(txtTipo);

        panel.add(lblCantidad);
        panel.add(txtCantidad);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        // Botones
        JButton btnConsultar = new JButton("Consultar");
        JButton btnGrabar = new JButton("Grabar");
        JButton btnBorrar = new JButton("Borrar");

        // Configurar los botones
        

        // Agregar botones al panel botones
        panelBotones.add(btnConsultar);
        panelBotones.add(btnGrabar);
        panelBotones.add(btnBorrar);

        // Agregar los paneles a la ventana
        ventana.add(panelFormulario);
        panelFormulario.setBounds(20,20,350,6*30+10);

        panelFormulario.add(panel, BorderLayout.CENTER);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        ventana.add(panelTabla);
        panelTabla.setBounds(20+350+20,20,500,300);
        
        ventana.setVisible(true);
    }
}