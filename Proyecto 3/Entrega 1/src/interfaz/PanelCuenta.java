package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.usuarios.Usuario;

public class PanelCuenta extends JPanel implements ActionListener {

	private InterfazGaleria padre;
	
	private static final String BOTON_CONTRASEÑA = "B1";
	private static final String BOTON_SALIR = "B2";

	public PanelCuenta(InterfazGaleria interfaz, Usuario user) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new GridLayout(0, 3));

		JLabel userLabel = new JLabel("Username:");
		JLabel passLabel = new JLabel("Password:");
		JButton contraseñaButton = new JButton("Cambiar Contraseña");
		contraseñaButton.setActionCommand(BOTON_CONTRASEÑA);
		JButton salirButton = new JButton("Cerrar Sesion");
		salirButton.setActionCommand(BOTON_SALIR);

		contraseñaButton.addActionListener(this);
		salirButton.addActionListener(this);
		
		InterfazGaleria.addNLabel(4, this);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(0, 2, 0, 50));
		info.add(new JLabel("Usuario: "));
		info.add(new JLabel(user.getLogin()));
		info.add(new JLabel("Nombre: "));
		info.add(new JLabel(user.getNombre()));
		info.add(new JLabel("Telefono: "));
		info.add(new JLabel(user.getTelefono()+""));
		info.add(new JLabel("Rol: "));
		info.add(new JLabel(user.getTipo()));
		info.add(contraseñaButton);
		info.add(salirButton);
		
		info.setVisible(true);

		add(info);
		
		InterfazGaleria.addNLabel(4, this);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
