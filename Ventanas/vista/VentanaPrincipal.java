import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame{
    private JDesktopPane escritorio;

    public VentanaPrincipal(){
        setTitle("Farmacia Total");
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        escritorio = new JDesktopPane();
        setContentPane(escritorio);
    }
}
