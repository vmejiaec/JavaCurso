import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Fuente;
import modelo.Medicina;

public class App {

    public static void main(String[] args) throws Exception {

        // Obtener lista de medicinas

        Fuente fuente = new Fuente();
        List<Medicina> medicinas = fuente.leerDBMedicina();

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

        String[] cabeceras = {"Còdigo","NOmbre","Laboratorio","Tipo", "Cantidad", "Precio"};

        DefaultTableModel modelo = new DefaultTableModel(cabeceras,0);
        for (Medicina medicina:medicinas){
            modelo.addRow(medicina.toArray());
        }

        JTable tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        panelTabla.add(scroll);

        // Campos
        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField("");

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField("");

        JLabel lblLaboratorio = new JLabel("Laboratorio");
        JTextField txtLaboratorio = new JTextField("");

        JLabel lblTipo = new JLabel("Tipo");
        JTextField txtTipo = new JTextField("");

        JLabel lblCantidad = new JLabel("Cantidad");
        JTextField txtCantidad = new JTextField("");

        JLabel lblPrecio = new JLabel("Precio");
        JTextField txtPrecio = new JTextField("");

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
                    if (nombre.equalsIgnoreCase (medicinaBuscada)){
                       medicinaEncontrada = true;
                    }
                }
                // Decidir si la medicina es creada
                if (medicinaEncontrada){
                    lblMensaje.setText("ERROR. La medicina ya existe.");
                } else {
                    // Inserta una nueva fila al modelo
                    Medicina nueva = new Medicina(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtLaboratorio.getText(),
                        txtTipo.getText(),
                        Integer.parseInt(txtCantidad.getText()),
                        Double.parseDouble(txtPrecio.getText())
                    );

                    if (fuente.insertarMedicina(nueva)) {
                        modelo.addRow(nueva.toArray());
                        lblMensaje.setText("La medicina ha sido creada.");
                    } else {
                        lblMensaje.setText("Error al guardar en la base de datos.");
                    }
                }
            }
        );

        btnModificar.addActionListener(
            e -> {

                int noFila = tabla.getSelectedRow();

                if (noFila == -1){
                    lblMensaje.setText("Seleccione una medicina.");
                    return;
                }

                try{

                    Medicina medicina = new Medicina(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtLaboratorio.getText(),
                        txtTipo.getText(),
                        Integer.parseInt(txtCantidad.getText()),
                        Double.parseDouble(txtPrecio.getText())
                    );

                    if(fuente.modificarMedicina(medicina)){

                        modelo.setValueAt(txtCodigo.getText(),       noFila, 0);
                        modelo.setValueAt(txtNombre.getText(),       noFila, 1);
                        modelo.setValueAt(txtLaboratorio.getText(),  noFila, 2);
                        modelo.setValueAt(txtTipo.getText(),         noFila, 3);
                        modelo.setValueAt(txtCantidad.getText(),     noFila, 4);
                        modelo.setValueAt(txtPrecio.getText(),       noFila, 5);
                        

                        lblMensaje.setText("La medicina fue modificada.");

                    }else{
                        lblMensaje.setText("No fue posible modificar la medicina.");
                    }

                }catch(NumberFormatException ex){
                    lblMensaje.setText("Cantidad debe ser un entero y precio un decimal.");
                }

            }
        );

        btnBorrar.addActionListener(
            e -> {
                int noFila = tabla.getSelectedRow();
                if (noFila == -1) {
                    lblMensaje.setText("Seleccione una fila para borrar.");
                    return;
                }
                int confirmacion = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Está seguro de borrar la medicina seleccionada?",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION){
                    String codigo = txtCodigo.getText();

                    if (fuente.borrarMedicina(codigo)) {
                        modelo.removeRow(tabla.getSelectedRow());
                        lblMensaje.setText("Registro borrado.");
                    } else {
                        lblMensaje.setText("Error al borrar en la base de datos.");
                    }
                }

                txtCodigo.setText("");
                txtNombre.setText("");
                txtLaboratorio.setText("");
                txtTipo.setText("");
                txtPrecio.setText("");
                txtCantidad.setText("");
            }
        );

        // Configurar el comportamiento de la tabla

        tabla.getSelectionModel().addListSelectionListener(
            e -> {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                int noFila = tabla.getSelectedRow();

                if (noFila == -1) {
                    return;
                }

               txtCodigo.setText(modelo.getValueAt(noFila,0).toString());
               txtNombre.setText(modelo.getValueAt(noFila,1).toString());
               txtLaboratorio.setText(modelo.getValueAt(noFila,2).toString());
               txtTipo.setText(modelo.getValueAt(noFila,3).toString());
               txtCantidad.setText(modelo.getValueAt(noFila,4).toString());
               txtPrecio.setText(modelo.getValueAt(noFila,5).toString());
               
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
