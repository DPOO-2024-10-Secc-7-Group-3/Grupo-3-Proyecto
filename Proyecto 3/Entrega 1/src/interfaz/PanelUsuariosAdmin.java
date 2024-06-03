package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import modelo.piezas.Pieza;
import modelo.usuarios.Usuario;

@SuppressWarnings("serial")
public class PanelUsuariosAdmin extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JTextField userField;
	private JPasswordField passField;
	private JTextField nameField;
	private JTextField telField;
	private JComboBox<String> rolField;

	private JList<String> usersList;
	private DefaultListModel<String> listModel;

	private JTextField userDeseado;
	private JTextArea historial;

	private static final String BOTON_CREAR = "B1";
	private static final String BOTON_ARTISTA = "B2";
	private static final String BOTON_CLIENTE = "B3";

	public PanelUsuariosAdmin(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new GridLayout(0, 2));

		JPanel panelIzq = new JPanel();
		panelIzq.setLayout(new GridLayout(2, 1));

		JPanel crearUser = panelCrearUsuario();
		JPanel verUsers = panelVerUsuarios();
		JPanel verHist = panelVerHistorial();

		panelIzq.add(crearUser);
		panelIzq.add(verUsers);

		add(panelIzq);
		add(verHist);

		setVisible(true);
	}

	public JPanel panelCrearUsuario() {
		JPanel panelCrearUsuario = new JPanel();
		panelCrearUsuario.setLayout(new GridLayout(0, 2, 15, 20));

		JLabel userLabel = new JLabel("Usuario:");
		JLabel passLabel = new JLabel("Contraseña:");
		JLabel nameLabel = new JLabel("Nombre:");
		JLabel telLabel = new JLabel("Telefono:");
		JLabel rolLabel = new JLabel("Rol:");
		JButton loginButton = new JButton("Crear Usuario");
		loginButton.setActionCommand(BOTON_CREAR);
		loginButton.addActionListener(this);

		userField = new JTextField();
		passField = new JPasswordField();
		nameField = new JTextField();
		telField = new JTextField();

		rolField = new JComboBox<String>();
		rolField.addItem(Usuario.ADMIN);
		rolField.addItem(Usuario.CAJERO);
		rolField.addItem(Usuario.CLIENTE);
		rolField.addItem(Usuario.OPERADOR);

		panelCrearUsuario.add(userLabel);
		panelCrearUsuario.add(userField);
		panelCrearUsuario.add(passLabel);
		panelCrearUsuario.add(passField);
		panelCrearUsuario.add(nameLabel);
		panelCrearUsuario.add(nameField);
		panelCrearUsuario.add(telLabel);
		panelCrearUsuario.add(telField);
		panelCrearUsuario.add(rolLabel);
		panelCrearUsuario.add(rolField);
		panelCrearUsuario.add(new JLabel());
		panelCrearUsuario.add(loginButton);

		panelCrearUsuario.setBorder(new TitledBorder("Crear Usuario"));
		panelCrearUsuario.setVisible(true);

		return panelCrearUsuario;
	}

	public JPanel panelVerUsuarios() {
		JPanel panelVerUsuario = new JPanel();
		panelVerUsuario.setLayout(new BorderLayout());

		listModel = new DefaultListModel<>();
		for (Entry<String, Usuario> entry : Usuario.logins.entrySet()) {
			listModel.addElement(entry.getKey() + " // " + capitalizeFirstLetter(entry.getValue().getTipo()));
		}

		usersList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane(usersList);

		panelVerUsuario.add(scrollPane, BorderLayout.CENTER);

		panelVerUsuario.setBorder(new TitledBorder("Ver Usuarios"));
		panelVerUsuario.setVisible(true);

		return panelVerUsuario;
	}

	public JPanel panelVerHistorial() {
		JPanel panelVerHistorial = new JPanel();
		panelVerHistorial.setLayout(new BorderLayout());

		JPanel subpanelText = new JPanel();
		subpanelText.setLayout(new GridLayout(1, 2));

		userDeseado = new JTextField();
		subpanelText.add(new JLabel("Artista o Cliente: "));
		subpanelText.add(userDeseado);

		JPanel subpanelBotones = new JPanel();
		subpanelBotones.setLayout(new FlowLayout());

		JButton artistaButton = new JButton("Buscar Historial Artista");
		artistaButton.setActionCommand(BOTON_ARTISTA);
		artistaButton.addActionListener(this);

		JButton clienteButton = new JButton("Buscar Historial Cliente");
		clienteButton.setActionCommand(BOTON_CLIENTE);
		clienteButton.addActionListener(this);

		subpanelBotones.add(artistaButton);
		subpanelBotones.add(clienteButton);

		historial = new JTextArea();

		panelVerHistorial.add(subpanelText, BorderLayout.NORTH);
		panelVerHistorial.add(historial, BorderLayout.CENTER);
		panelVerHistorial.add(subpanelBotones, BorderLayout.SOUTH);

		panelVerHistorial.setBorder(new TitledBorder("Historiales"));
		panelVerHistorial.setVisible(true);

		return panelVerHistorial;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (BOTON_CREAR.equals(comando)) {
			crearUsuario();
		} else if (BOTON_ARTISTA.equals(comando)) {
			mostrarHistArtista();
		} else if (BOTON_CLIENTE.equals(comando)) {
			mostrarHistCliente();
		}
	}

	public void crearUsuario() {
		String usuario = userField.getText();
		String password = new String(passField.getPassword());
		String name = nameField.getText();
		int telefono = Integer.parseInt(telField.getText());
		String rol = (String) rolField.getSelectedItem();
		listModel.addElement(usuario + " // " + capitalizeFirstLetter(rol));
		padre.crearUsuario(usuario, password, name, telefono, rol);
	}

	public void mostrarHistArtista() {
		String usuario = userDeseado.getText();
		Usuario userTemp = Usuario.logins.get(usuario);
		if (userTemp != null) {
			String tipo = userTemp.getTipo();
			if (Usuario.CLIENTE.equals(tipo)) {
				HashMap<String, ArrayList<String>> info = Usuario.infoArtista(usuario);
				String plot = "";
				for (String key : info.keySet()) {
					plot += ("Pieza: " + key + "\n");
					plot += ("creacion: " + Pieza.piezas.get(key).getAnio() + "\n");
					plot += ("ventas: \n");
					for (String infoParcial : info.get(key)) {
						plot += (infoParcial + "\n");
					}
					plot += "\n";
				}
				historial.setText(plot);
			} else {
				JOptionPane.showMessageDialog(padre,
						"El Usuario debe ser de clase Cliente, no de clase " + capitalizeFirstLetter(tipo),
						"Error: Usuario Invalido", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(padre, "El usuario " + usuario + " no existe", "Error: Usuario Inexistente",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarHistCliente() {
		String cliente = userDeseado.getText();
		Usuario userTemp = Usuario.logins.get(cliente);
		if (userTemp != null) {
			String tipo = userTemp.getTipo();
			if (Usuario.CLIENTE.equals(tipo)) {
				HashMap<String, ArrayList<String>> info = padre.infoCliente(cliente);
				String plot = "";
				for (String key : info.keySet()) {
					plot += ("\n" + capitalizeFirstLetter(key) + ":\n");
					for (String infoParcial : info.get(key)) {
						plot += (infoParcial + "\n");
					}
				}

				plot += ("\nValor: \n");
				int monto = 0;
				ArrayList<String> actuales = info.get("actuales");
				for (String titulo : actuales) {
					Pieza nPieza = Pieza.piezas.get(titulo);
					monto += nPieza.getPrecio();
				}
				plot += (monto + "\n");
				plot += "\n";
				historial.setText(plot.substring(1));
			} else {
				JOptionPane.showMessageDialog(padre,
						"El Usuario debe ser de clase Cliente, no de clase " + capitalizeFirstLetter(tipo),
						"Error: Usuario Invalido", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(padre, "El usuario " + cliente + " no existe", "Error: Usuario Inexistente",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
