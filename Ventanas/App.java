import modelo.Fuente;
import modelo.Medicina;
import modelo.MedicinaRepositorio;
import controlador.Servicio;


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
        JPanel panelTabla = new JPanel(new BorderLayout());
        ventana.add(panelTabla);
        panelTabla.setBounds(390,20,490,400);

        String[] cabeceras = {
            "Còdigo","NOmbre","Laboratorio","Tipo","Precio", "Cantidad"
        };

        DefaultTableModel modelo = new DefaultTableModel(cabeceras,0);
        for (Medicina medicina:medicinas){
            modelo.addRow(medicina.toArray());
        }

        JTable tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        panelTabla.add(scroll);

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

        // Etiqueta para publicar mensajes
        JLabel lblMensaje = new JLabel("...");

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

        panel.add(lblMensaje);

        // Botones
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");

        // Configurar el comportamiento de los botones
        btnCrear.addActionListener(
            e -> {
                // Validar que no exista la medicina
                String medicinaBuscada = txtNombre.getText();
                int NoFilas = modelo.getRowCount();
                boolean medicinaEncontrada = false;
                // Buscar la medicina
                for(int i =0; i<NoFilas; i++){
                    String nombre = modelo.getValueAt(i,1).toString();
                    System.out.println("Prueba: "+nombre);
                    if (nombre.equalsIgnoreCase (medicinaBuscada)){
                       medicinaEncontrada = true;
                    }
                }
                // Decidir si la medicina es creada
                if (medicinaEncontrada){
                    lblMensaje.setText("ERROR. La medicina ya existe.");
                } else {
                    // Inserta una nueva fila al model
                    modelo.addRow(
                    new Object[]{
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtLaboratorio.getText(),
                        txtTipo.getText(),
                        txtPrecio.getText(),
                        txtCantidad.getText()
                        }
                    );
                    lblMensaje.setText("La medicina ha sido creada.");
                }
            }
        );

        btnModificar.addActionListener(
            e -> {

            }
        );

        btnBorrar.addActionListener(
            e -> {
                int noFila = tabla.getSelectedRow();
                int confirmacion = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de borrar la medicina seleccionada?",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION){
                    modelo.removeRow(noFila);
                    lblMensaje.setText("Registro Borrado");
                }
            }
        );

        // Configurar el comportamiento de la tabla

        tabla.getSelectionModel().addListSelectionListener(
            e -> {
               int noFila = tabla.getSelectedRow();

               System.out.println("Fila seleccionada: "+ noFila);

               txtCodigo.setText(modelo.getValueAt(noFila,0).toString());
               txtNombre.setText(modelo.getValueAt(noFila,1).toString());
               txtLaboratorio.setText(modelo.getValueAt(noFila,2).toString());
               txtTipo.setText(modelo.getValueAt(noFila,3).toString());
               txtPrecio.setText(modelo.getValueAt(noFila,4).toString());
               txtCantidad.setText(modelo.getValueAt(noFila,5).toString());
            }
        );

        // Agregar botones al panel botones
        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBorrar);

        // Agregar los paneles a la ventana
        ventana.add(panelFormulario);
        panelFormulario.setBounds(20,20,350,6*30+10);

        panelFormulario.add(panel, BorderLayout.CENTER);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        
        ventana.setVisible(true);
    }
}
