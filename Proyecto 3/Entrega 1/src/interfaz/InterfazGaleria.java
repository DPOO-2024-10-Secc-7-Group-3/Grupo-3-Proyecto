package interfaz;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controlador.Controlador;
import modelo.usuarios.Usuario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;

@SuppressWarnings("serial")
public class InterfazGaleria extends JFrame implements WindowListener {

	private JTabbedPane pestañas = new JTabbedPane();
	private PanelLogin login;
	private PanelCuenta cuenta;

	// Paneles de administrador
	private PanelUsuariosAdmin usersAdmin;
	private PanelPiezasAdmin piezasAdmin;
	private PanelSubastaAdmin subastaAdmin;

	// Paneles de cliente
	private PanelHistorialesCliente histCliente;
	private PanelPiezasCliente piezasCliente;

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	private Controlador controlador;

	private Usuario user;

	public InterfazGaleria() {

		controlador = new Controlador("ensayo");

		// Configurar el JFrame
		setTitle("Art Gallery App");
		setSize(800, 600);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setLocationRelativeTo(null);
		// setResizable(false);

		login = new PanelLogin(this);

		panel1.add(new JLabel("Panel 1: Artworks"));
		panel2.add(new JLabel("Panel 2: Artists"));
		panel3.add(new JLabel("Panel 3: Exhibitions"));

		pestañas.addTab("Login", login);

		add(pestañas);

		setVisible(true);
	}

	public void sesionIniciada(String tipo, Usuario user1) {
		pestañas.removeTabAt(0);
		user = user1;
		cuenta = new PanelCuenta(this, user);
		if (Usuario.ADMIN.equals(tipo)) {
			usersAdmin = new PanelUsuariosAdmin(this);
			pestañas.addTab("Usuarios", usersAdmin);
			piezasAdmin = new PanelPiezasAdmin(this);
			pestañas.addTab("Piezas", piezasAdmin);
			subastaAdmin = new PanelSubastaAdmin(this);
			pestañas.addTab("Subastas", subastaAdmin);
		} else if (Usuario.CLIENTE.equals(tipo)) {
			piezasCliente = new PanelPiezasCliente(this);
			pestañas.addTab("Piezas", piezasCliente);
			histCliente = new PanelHistorialesCliente(this);
			pestañas.addTab("Historiales", histCliente);
		}
		pestañas.addTab("Cuenta", cuenta);
	}

	public void sesionCerrada() {
		int nPestañas = pestañas.getTabCount();
		pestañas.addTab("Login", login);
		for (int i = 0; i < nPestañas; i++) {
			pestañas.removeTabAt(0);
		}
	}

	public static void main(String[] args) {
		new InterfazGaleria();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		boolean acabar = false;
		int response = JOptionPane.showConfirmDialog(this, "¿Deseas guardar los cambios antes de salir?",
				"Confirmar Cierre", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (response == JOptionPane.YES_OPTION) {
			String input = JOptionPane.showInputDialog(this, "Ingrese un valor:");
			if (input == null) {

			} else if (input.equals("")) {
				JOptionPane.showMessageDialog(this, "El nombre no puede ser ''", "Error: Nombre Invalido",
						JOptionPane.ERROR_MESSAGE);
			} else {
				acabar = true;
			}

		} else if (response == JOptionPane.NO_OPTION) {
			acabar = true;
		}
		if (acabar) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void crearUsuario(String usuario, String password, String name, int telefono, String rol) {
		try {
			((Administrador) user).crearUsuario(usuario, password, name, telefono, rol);
			JOptionPane.showMessageDialog(this, "El usuario " + usuario + " se ha creado con exito", "Usuario Creado",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Crear Usuario",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public HashMap<String, ArrayList<String>> infoCliente(String cliente) {
		return controlador.infoCliente(user, cliente);
	}

	public static void addNLabel(int n, JPanel hijo) {
		for (int i = 0; i < n; i++) {
			hijo.add(new JLabel());
		}
	}

	public void setPassword(String password) {
		controlador.setPassoword(user, password);
	}

	public String devolverPieza(String pieza) {
		String plot = "";
		try {
			controlador.devolverPieza(user, pieza);

			plot += ("Estado de la pieza " + pieza + ": " + Pieza.piezas.get(pieza).getEstado() + "\n");

			plot += ("Inventario almacenadas: \n");
			for (String titulo : controlador.getAlmacenadas(user)) {
				plot += (titulo + "\n");
			}
			plot += ("Inventario exhibidas: \n");
			for (String titulo : controlador.getExhibidas(user)) {
				plot += (titulo + "\n");
			}
		} catch (Exception e) {
			plot += e.getMessage();
		}
		return plot;
	}

	public Set<Entry<String, Usuario>> logins() {
		return controlador.logins();
	}

	public String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public void cerrarSubasta(String subasta) {
		try {
			controlador.cerrarSubasta(user, subasta);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Cerrar Subasta",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public Pieza getPieza(String pieza) {
		return controlador.getPieza(pieza);
	}

	public void crearPieza(String titulo, int anio, String lugar, int valorMinimo, int valorInicial, double ancho,
			double alto, double profundidad, ArrayList<String> nMateriales, boolean electricidad, int precio,
			String string) {
		try {
			controlador.crearPieza(user, titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, profundidad,
					nMateriales, electricidad, precio, "escultura");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Crear Pieza",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void crearPieza(String titulo, int anio, String lugar, int valorMinimo, int valorInicial, double ancho,
			double alto, String textura, int precio, String string) {
		try {
			controlador.crearPieza(user, titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, textura, precio,
					"pintura");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Crear Pieza",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void crearPieza(String titulo, int anio, String lugar, int valorMinimo, int valorInicial, int duracion,
			int precio, String string) {
		try {
			controlador.crearPieza(user, titulo, anio, lugar, valorMinimo, valorInicial, duracion, precio, "video");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Crear Pieza",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void crearPieza(String titulo, int anio, String lugar, int valorMinimo, int valorInicial, double ancho,
			double alto, int resolucion, String nTipo, int precio, String string) {
		try {
			controlador.crearPieza(user, titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, resolucion, nTipo,
					precio, "imagen");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error: Fallo Al Crear Pieza",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}