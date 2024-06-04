package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;

@SuppressWarnings("serial")
public class PanelPiezasAdmin extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JTextField devolverField;
	private JTextArea devolverArea;

	private DefaultListModel<String> listModel;
	private JList<String> piezasList;

	private JTextField piezaDeseada;
	private JTextArea historial;

	private static final String BOTON_DEVOLVER = "B1";
	private static final String BOTON_HIST = "B2";

	public PanelPiezasAdmin(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new GridLayout(0, 2));

		JPanel panelIzq = new JPanel();
		panelIzq.setLayout(new GridLayout(2, 1));

		JPanel devolverPieza = panelDevolverPieza();
		JPanel verPiezas = panelVerPiezas();
		JPanel verHist = panelVerHistorial();

		panelIzq.add(devolverPieza);
		panelIzq.add(verPiezas);

		add(panelIzq);
		add(verHist);

		setVisible(true);
	}

	public JPanel panelDevolverPieza() {
		JPanel panelDevolverPieza = new JPanel();
		panelDevolverPieza.setLayout(new BorderLayout(100, 20));

		JLabel devolverLabel = new JLabel("Pieza a Devolver:");
		JButton devolverButton = new JButton("Devolver Pieza");
		devolverButton.setActionCommand(BOTON_DEVOLVER);
		devolverButton.addActionListener(this);

		devolverField = new JTextField();
		devolverArea = new JTextArea(10, 30);
		devolverArea.setLineWrap(true);
		devolverArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(devolverArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel minipanel = new JPanel();
		minipanel.setLayout(new GridLayout(0, 2, 15, 20));

		minipanel.add(devolverLabel);
		minipanel.add(devolverField);
		panelDevolverPieza.add(minipanel, BorderLayout.NORTH);
		panelDevolverPieza.add(devolverButton, BorderLayout.CENTER);
		panelDevolverPieza.add(new JLabel(), BorderLayout.EAST);
		panelDevolverPieza.add(new JLabel(), BorderLayout.WEST);
		panelDevolverPieza.add(scrollPane, BorderLayout.SOUTH);

		panelDevolverPieza.setBorder(new TitledBorder("Devolver Pieza"));
		panelDevolverPieza.setVisible(true);

		return panelDevolverPieza;
	}

	public JPanel panelVerPiezas() {
		JPanel panelVerPiezas = new JPanel();
		panelVerPiezas.setLayout(new BorderLayout());

		listModel = new DefaultListModel<>();
		actualizarListModel();

		piezasList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane(piezasList);

		panelVerPiezas.add(scrollPane, BorderLayout.CENTER);

		panelVerPiezas.setBorder(new TitledBorder("Ver Piezas A La Venta"));
		panelVerPiezas.setVisible(true);

		return panelVerPiezas;
	}

	public JPanel panelVerHistorial() {
		JPanel panelVerHistorial = new JPanel();
		panelVerHistorial.setLayout(new BorderLayout());

		JPanel subpanelText = new JPanel();
		subpanelText.setLayout(new GridLayout(1, 2));

		piezaDeseada = new JTextField();
		piezaDeseada.setActionCommand(BOTON_HIST);
		piezaDeseada.addActionListener(this);
		
		subpanelText.add(new JLabel("Pieza: "));
		subpanelText.add(piezaDeseada);

		JButton piezaButton = new JButton("Buscar Historial Pieza");
		piezaButton.setActionCommand(BOTON_HIST);
		piezaButton.addActionListener(this);

		historial = new JTextArea();

		panelVerHistorial.add(subpanelText, BorderLayout.NORTH);
		panelVerHistorial.add(historial, BorderLayout.CENTER);
		panelVerHistorial.add(piezaButton, BorderLayout.SOUTH);

		panelVerHistorial.setBorder(new TitledBorder("Historial"));
		panelVerHistorial.setVisible(true);

		return panelVerHistorial;
	}

	public void actualizarListModel() {
		listModel.clear();
		for (Entry<String, Usuario> entry : padre.logins()) {
			Usuario actual = entry.getValue();
			if (actual instanceof Administrador) {
				System.out.println("Admin");
				Inventario inventario = ((Administrador) actual).getInventario();
				for (String titulo : inventario.getAlmacenadas()) {
					Pieza pieza = Pieza.piezas.get(titulo);
					if (pieza.getDisponibilidad() instanceof Fija) {
						listModel.addElement(titulo);
					}
				}
				for (String titulo : inventario.getExhibidas()) {
					Pieza pieza = Pieza.piezas.get(titulo);
					if (pieza.getDisponibilidad() instanceof Fija) {
						listModel.addElement(titulo);
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (BOTON_DEVOLVER.equals(comando)) {
			String pieza = devolverField.getText();
			String plot = padre.devolverPieza(pieza);
			devolverArea.setText(plot);
			actualizarListModel();
		} else if (BOTON_HIST.equals(comando)) {
			mostrarHistPieza();
		}
	}

	public void mostrarHistPieza() {
		String titulo = piezaDeseada.getText();
		Pieza piezaTemp = Pieza.getPieza(titulo);
		if (piezaTemp != null) {
			HashMap<String, String> info = Usuario.infoPieza(titulo);
			String plot = "";
			for (String key : info.keySet()) {
				plot += (key + ": " + info.get(key) + "\n");
			}
			historial.setText(plot);
		} else {
			JOptionPane.showMessageDialog(padre, "La pieza " + titulo + " no existe", "Error: Pieza Inexistente",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
