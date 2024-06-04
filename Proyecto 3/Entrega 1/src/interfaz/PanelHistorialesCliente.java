package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import modelo.piezas.Pieza;
import modelo.usuarios.Usuario;

@SuppressWarnings("serial")
public class PanelHistorialesCliente extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JTextField userDeseado;
	private JTextArea historial;

	private static final String BOTON_PIEZA = "B1";
	private static final String BOTON_ARTISTA = "B2";

	public PanelHistorialesCliente(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		setLayout(new BorderLayout());

		JPanel subpanelText = new JPanel();
		subpanelText.setLayout(new GridLayout(1, 2));

		userDeseado = new JTextField();
		subpanelText.add(new JLabel("Artista o Pieza: "));
		subpanelText.add(userDeseado);

		JPanel subpanelBotones = new JPanel();
		subpanelBotones.setLayout(new FlowLayout());

		JButton artistaButton = new JButton("Buscar Historial Artista");
		artistaButton.setActionCommand(BOTON_ARTISTA);
		artistaButton.addActionListener(this);

		JButton clienteButton = new JButton("Buscar Historial Pieza");
		clienteButton.setActionCommand(BOTON_PIEZA);
		clienteButton.addActionListener(this);

		subpanelBotones.add(artistaButton);
		subpanelBotones.add(clienteButton);

		historial = new JTextArea();

		add(subpanelText, BorderLayout.NORTH);
		add(historial, BorderLayout.CENTER);
		add(subpanelBotones, BorderLayout.SOUTH);

		setVisible(true);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (BOTON_ARTISTA.equals(comando)) {
			mostrarHistArtista();
		} else if (BOTON_PIEZA.equals(comando)) {
			mostrarHistPieza();
		}
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
						"El Usuario debe ser de clase Cliente, no de clase " + padre.capitalizeFirstLetter(tipo),
						"Error: Usuario Invalido", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(padre, "El usuario " + usuario + " no existe", "Error: Usuario Inexistente",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarHistPieza() {
		String pieza = userDeseado.getText();
		Pieza piezaTemp = padre.getPieza(pieza);
		if (piezaTemp != null) {
			HashMap<String, String> info = Usuario.infoPieza(pieza);
			String plot = "";

			for (String key : info.keySet()) {
				plot += (key + ": " + info.get(key) + "\n");
			}
			historial.setText(plot);
		} else {
			JOptionPane.showMessageDialog(padre, "La pieza " + pieza + " no existe", "Error: Pieza Inexistente",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
