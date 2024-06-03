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

@SuppressWarnings("serial")
public class PanelLogin extends JPanel implements ActionListener {

	InterfazGaleria padre;

	private JTextField userField;
	private JPasswordField passField;

	private boolean isUserLogged = false;

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
		loginButton.addActionListener(this);

		addNLabel(4);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(3, 2, 0, 50));
		info.add(userLabel);
		info.add(userField);
		info.add(passLabel);
		info.add(passField);
		info.add(new JLabel());
		info.add(loginButton);

		add(info);

		addNLabel(4);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Aquí puedes agregar lógica para autenticar al usuario
		String username = userField.getText();
		String password = new String(passField.getPassword());
		if (username.equals("admin") && password.equals("password")) {
			padre.sesionIniciada();
			isUserLogged = true;
			JOptionPane.showMessageDialog(this, "Login successful!");
		} else {
			padre.sesionCerrada(isUserLogged);
			isUserLogged = false;
			JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
		}
	}

	public void addNLabel(int n) {
		for (int i = 0; i < n; i++) {
			add(new JLabel());
		}
	}
}
