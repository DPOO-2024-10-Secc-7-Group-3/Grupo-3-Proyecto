package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.usuarios.Usuario;

@SuppressWarnings("serial")
public class PanelCuenta extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private static final String BOTON_CONTRASEÑA = "B1";
	private static final String BOTON_SALIR = "B2";

	public PanelCuenta(InterfazGaleria interfaz, Usuario user) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new BorderLayout(150, 100));

		JButton contraseñaButton = new JButton("Cambiar Contraseña");
		contraseñaButton.setActionCommand(BOTON_CONTRASEÑA);
		JButton salirButton = new JButton("Cerrar Sesion");
		salirButton.setActionCommand(BOTON_SALIR);

		contraseñaButton.addActionListener(this);
		salirButton.addActionListener(this);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(0, 2, 0, 50));
		info.add(new JLabel("Usuario: "));
		info.add(new JLabel(user.getLogin()));
		info.add(new JLabel("Nombre: "));
		info.add(new JLabel(user.getNombre()));
		info.add(new JLabel("Telefono: "));
		info.add(new JLabel(user.getTelefono() + ""));
		info.add(new JLabel("Rol: "));
		info.add(new JLabel(user.getTipo()));
		info.add(contraseñaButton);
		info.add(salirButton);

		info.setVisible(true);

		add(info, BorderLayout.CENTER);
		add(new JLabel(""), BorderLayout.WEST);
		add(new JLabel(""), BorderLayout.EAST);
		add(new JLabel(""), BorderLayout.NORTH);
		add(new JLabel(""), BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (BOTON_CONTRASEÑA.equals(comando)) {
			String password = JOptionPane.showInputDialog("Ingrese la nueva contraseña");
			padre.setPassword(password);
			JOptionPane.showMessageDialog(this, "Se ha cambiado la contraseña");
		} else if (BOTON_SALIR.equals(comando)) {
			padre.sesionCerrada();
			JOptionPane.showMessageDialog(this, "Se ha cerrado la sesión");
		}
	}
}
