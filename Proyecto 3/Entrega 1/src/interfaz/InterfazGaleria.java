package interfaz;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class InterfazGaleria extends JFrame {

	private PanelLogin login;
	private JTabbedPane pestañas = new JTabbedPane();

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	public InterfazGaleria() {
		// Configurar el JFrame
		setTitle("Art Gallery App");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		login = new PanelLogin(this);

		panel1.add(new JLabel("Panel 1: Artworks"));
		panel2.add(new JLabel("Panel 2: Artists"));
		panel3.add(new JLabel("Panel 3: Exhibitions"));

		pestañas.addTab("Login", login);

		add(pestañas);

		setVisible(true);
	}

	public void sesionIniciada() {
		//pestañas.removeTabAt(0);
		pestañas.addTab("Artworks", panel1);
		pestañas.addTab("Artists", panel2);
		pestañas.addTab("Exhibitions", panel3);
	}
	
	public void sesionCerrada(boolean logged) {
		int nPestañas = pestañas.getTabCount();
		for(int i = 0; i< nPestañas-1; i++) {
			if (logged) {
				System.out.println(i);
				pestañas.removeTabAt(1);
				//pestañas.addTab("Login", login);
			}
		}
	}

	public static void main(String[] args) {
		new InterfazGaleria();
	}
}