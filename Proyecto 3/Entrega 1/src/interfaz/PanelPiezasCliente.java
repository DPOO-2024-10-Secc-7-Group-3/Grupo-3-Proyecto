package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
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

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;

@SuppressWarnings("serial")
public class PanelPiezasCliente extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JComboBox<String> tipoField;
	private JTextField tituloField;
	private JTextField anioField;
	private JTextField lugarField;
	private JTextField valMinField;
	private JTextField valIniField;
	private JTextField precioField;

	private JTextField tituloEntregarField;
	private JTextField exhibirEntregarField;
	private JTextField subastarEntregarField;

	private DefaultListModel<String> listModel;
	private JList<String> piezasList;

	private JTextField tituloComprarField;
	private JComboBox<String> metodoField;

	private static final String BOTON_CREAR = "B1";
	private static final String BOTON_ENTREGAR = "B2";
	private static final String BOTON_COMPRAR = "B3";

	public PanelPiezasCliente(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new GridLayout(0, 2));

		JPanel panelIzq = new JPanel();
		panelIzq.setLayout(new GridLayout(2, 1));

		JPanel crearPieza = panelCrearPieza();
		JPanel entregarPieza = panelEntregarPieza();
		JPanel verPiezas = panelVerPiezas();
		JPanel comprarPieza = panelComprarPieza();

		add(crearPieza);
		add(entregarPieza);
		add(verPiezas);
		add(comprarPieza);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		if (BOTON_CREAR.equals(comando)) {
			crearPieza();
		} else if (BOTON_ENTREGAR.equals(comando)) {
			entregarPieza();
		} else if (BOTON_COMPRAR.equals(comando)) {
			comprarPieza();
		}
	}

	public void comprarPieza() {
		String titulo = tituloComprarField.getText();
		String metodo = (String) metodoField.getSelectedItem();
		boolean fallo = padre.comprar(titulo, metodo);
		if (fallo) {
			JOptionPane.showMessageDialog(this, "La pieza fue comprada exitosamente", "Pieza Comprada",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public JPanel panelComprarPieza() {
		JPanel panelComprarPieza = new JPanel();
		panelComprarPieza.setLayout(new GridLayout(0, 2, 10, 50));

		JLabel tituloLabel = new JLabel("Titulo:");
		JLabel tipoLabel = new JLabel("Metodo:");

		tituloComprarField = new JTextField();

		metodoField = new JComboBox<String>();
		metodoField.addItem("PayPal");
		metodoField.addItem("Payu");
		metodoField.addItem("Sire");

		JButton comprarButton = new JButton("Comprar Pieza");
		comprarButton.setActionCommand(BOTON_COMPRAR);
		comprarButton.addActionListener(this);

		panelComprarPieza.add(tituloLabel);
		panelComprarPieza.add(tituloComprarField);
		panelComprarPieza.add(tipoLabel);
		panelComprarPieza.add(metodoField);
		panelComprarPieza.add(new JLabel());
		panelComprarPieza.add(comprarButton);

		panelComprarPieza.setBorder(new TitledBorder("Entregar Pieza"));
		panelComprarPieza.setVisible(true);

		return panelComprarPieza;
	}

	public void entregarPieza() {
		String titulo = tituloEntregarField.getText();
		String subasta = subastarEntregarField.getText();
		boolean nSubasta;
		if (subasta.equalsIgnoreCase("s")) {
			nSubasta = true;
		} else {
			nSubasta = false;
		}
		String exhibir = exhibirEntregarField.getText();
		boolean nExhibir;
		if (exhibir.equalsIgnoreCase("s")) {
			nExhibir = true;
		} else {
			nExhibir = false;
		}
		boolean fallo = padre.entregarPieza(titulo, nExhibir, nSubasta);
		if (fallo) {
			JOptionPane.showMessageDialog(this, "La pieza fue entregada exitosamente", "Pieza Entregada",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public JPanel panelEntregarPieza() {
		JPanel panelEntregarPieza = new JPanel();
		panelEntregarPieza.setLayout(new GridLayout(0, 2, 10, 35));

		JLabel tituloEntregarLabel = new JLabel("Pieza a Entregar:");
		JLabel exhibirEntregarLabel = new JLabel("Se va a exhibir? (S/N):");
		JLabel subastarEntregarLabel = new JLabel("Se va a subastar? (S/N):");
		JButton entregarButton = new JButton("Entregar Pieza");
		entregarButton.setActionCommand(BOTON_ENTREGAR);
		entregarButton.addActionListener(this);

		tituloEntregarField = new JTextField();
		exhibirEntregarField = new JTextField();
		subastarEntregarField = new JTextField();

		JPanel minipanel = new JPanel();
		minipanel.setLayout(new GridLayout(0, 2, 15, 20));

		panelEntregarPieza.add(tituloEntregarLabel);
		panelEntregarPieza.add(tituloEntregarField);
		panelEntregarPieza.add(exhibirEntregarLabel);
		panelEntregarPieza.add(exhibirEntregarField);
		panelEntregarPieza.add(subastarEntregarLabel);
		panelEntregarPieza.add(subastarEntregarField);
		panelEntregarPieza.add(new JLabel());
		panelEntregarPieza.add(entregarButton);

		panelEntregarPieza.setBorder(new TitledBorder("Entregar Pieza"));
		panelEntregarPieza.setVisible(true);

		return panelEntregarPieza;
	}

	public void crearPieza() {
		String tipo = (String) tipoField.getSelectedItem();
		String titulo = tituloField.getText();
		int anio = Integer.parseInt(anioField.getText());
		String lugar = lugarField.getText();
		int valorMinimo = Integer.parseInt(valMinField.getText());
		int valorInicial = Integer.parseInt(valIniField.getText());
		int precio = Integer.parseInt(precioField.getText());
		boolean fallo = false;
		if (Pieza.ESCULTURA.equals(tipo)) {
			double alto = Double.parseDouble(JOptionPane.showInputDialog("Cual es el alto de la pieza"));
			double ancho = Double.parseDouble(JOptionPane.showInputDialog("Cual es el ancho de la pieza"));
			double profundidad = Double.parseDouble(JOptionPane.showInputDialog("Cual es la profundidad de la pieza"));
			String material = JOptionPane.showInputDialog("De que material es la pieza");
			ArrayList<String> nMateriales = new ArrayList<String>();
			nMateriales.add(material);
			boolean electricidad = JOptionPane.showConfirmDialog(padre,
					"La pieza usa electricidad?") == JOptionPane.YES_OPTION;
			fallo = padre.crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, profundidad,
					nMateriales, electricidad, precio, "escultura");
		} else if (Pieza.PINTURA.equals(tipo)) {
			double alto = Double.parseDouble(JOptionPane.showInputDialog("Cual es el alto de la pieza"));
			double ancho = Double.parseDouble(JOptionPane.showInputDialog("Cual es el ancho de la pieza"));
			String textura = JOptionPane.showInputDialog("De que textura es la pieza");
			fallo = padre.crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, textura, precio,
					"pintura");
		} else if (Pieza.VIDEO.equals(tipo)) {
			int duracion = Integer
					.parseInt(JOptionPane.showInputDialog("Cual es la duracion de la pieza (en segundos)"));
			fallo = padre.crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, duracion, precio, "video");
		} else {
			double alto = Double.parseDouble(JOptionPane.showInputDialog("Cual es el alto de la pieza"));
			double ancho = Double.parseDouble(JOptionPane.showInputDialog("Cual es el ancho de la pieza"));
			int resolucion = Integer
					.parseInt(JOptionPane.showInputDialog("Cual es la resolucion de la pieza (solo el primer numero)"));
			String nTipo = JOptionPane.showInputDialog("Cual es el tipo de imagen");
			fallo = padre.crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, resolucion, nTipo,
					precio, "imagen");
		}
		if (fallo) {
			JOptionPane.showMessageDialog(this, "La pieza fue creada exitosamente", "Pieza Creada",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public JPanel panelCrearPieza() {
		JPanel panelCrearPieza = new JPanel();
		panelCrearPieza.setLayout(new GridLayout(0, 2, 15, 12));

		JLabel tipoLabel = new JLabel("Tipo:");
		JLabel tituloLabel = new JLabel("Titulo:");
		JLabel anioLabel = new JLabel("Año de Creación:");
		JLabel lugarLabel = new JLabel("Lugar de Creación:");
		JLabel valMinLabel = new JLabel("Valor Minimo:");
		JLabel valIniLabel = new JLabel("Valor Inicial:");
		JLabel precioLabel = new JLabel("Precio de Venta:");
		JButton crearButton = new JButton("Crear Pieza");
		crearButton.setActionCommand(BOTON_CREAR);
		crearButton.addActionListener(this);

		tituloField = new JTextField();
		anioField = new JTextField();
		lugarField = new JTextField();
		valMinField = new JTextField();
		valIniField = new JTextField();
		precioField = new JTextField();

		tipoField = new JComboBox<String>();
		tipoField.addItem(Pieza.ESCULTURA);
		tipoField.addItem(Pieza.IMAGEN);
		tipoField.addItem(Pieza.PINTURA);
		tipoField.addItem(Pieza.VIDEO);

		panelCrearPieza.add(tipoLabel);
		panelCrearPieza.add(tipoField);
		panelCrearPieza.add(tituloLabel);
		panelCrearPieza.add(tituloField);
		panelCrearPieza.add(anioLabel);
		panelCrearPieza.add(anioField);
		panelCrearPieza.add(lugarLabel);
		panelCrearPieza.add(lugarField);
		panelCrearPieza.add(valMinLabel);
		panelCrearPieza.add(valMinField);
		panelCrearPieza.add(valIniLabel);
		panelCrearPieza.add(valIniField);
		panelCrearPieza.add(precioLabel);
		panelCrearPieza.add(precioField);
		panelCrearPieza.add(new JLabel());
		panelCrearPieza.add(crearButton);

		panelCrearPieza.setBorder(new TitledBorder("Crear Pieza"));
		panelCrearPieza.setVisible(true);

		return panelCrearPieza;
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
}
