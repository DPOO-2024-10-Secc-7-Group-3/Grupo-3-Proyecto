package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import modelo.usuarios.Usuario;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JTextField userField;
	private JPasswordField passField;

	public PanelLogin(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new GridLayout(0, 3));

		JLabel userLabel = new JLabel("Username:");
		userField = new JTextField();
		JLabel passLabel = new JLabel("Password:");
		passField = new JPasswordField();
		JButton loginButton = new JButton("Login");

		userField.addActionListener(this);
		passField.addActionListener(this);
		loginButton.addActionListener(this);

		InterfazGaleria.addNLabel(4, this);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(3, 2, 0, 50));
		info.add(userLabel);
		info.add(userField);
		info.add(passLabel);
		info.add(passField);
		info.add(new JLabel());
		info.add(loginButton);

		add(info);

		InterfazGaleria.addNLabel(4, this);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Aquí puedes agregar lógica para autenticar al usuario
		String username = userField.getText();
		String password = new String(passField.getPassword());

		try {
			Usuario user1 = Usuario.iniciarSesion(username, password);
			if (user1.getTipo().equals(Usuario.ADMIN)) {
				padre.sesionIniciada(Usuario.ADMIN, user1);
			} else if (user1.getTipo().equals(Usuario.CAJERO)) {
				padre.sesionIniciada(Usuario.CAJERO, user1);
			} else if (user1.getTipo().equals(Usuario.CLIENTE)) {
				padre.sesionIniciada(Usuario.CLIENTE, user1);
			} else if (user1.getTipo().equals(Usuario.OPERADOR)) {
				padre.sesionIniciada(Usuario.OPERADOR, user1);
			}
			JOptionPane.showMessageDialog(this, "Login successful!");
			userField.setText("");
			passField.setText("");
		} catch (UserNotFoundException | IncorrectPasswordException e1) {
			JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
		}
	}
}
