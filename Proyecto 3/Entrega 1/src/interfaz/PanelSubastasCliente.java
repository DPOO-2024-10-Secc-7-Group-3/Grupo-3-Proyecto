package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Usuario;
import modelo.ventas.Subasta;

@SuppressWarnings("serial")
public class PanelSubastasCliente extends JPanel implements ActionListener {

	private InterfazGaleria padre;

	private JList<String> subastasList;
	private DefaultListModel<String> listModel;

	public PanelSubastasCliente(InterfazGaleria interfaz) {
		super();
		padre = interfaz;

		// Crear un panel de inicio de sesión
		setLayout(new BorderLayout(150, 100));

		JButton ofertarButton = new JButton("Ofertar Subasta");

		ofertarButton.addActionListener(this);

		JPanel info = new JPanel();
		info.setLayout(new BorderLayout(0, 50));

		listModel = new DefaultListModel<>();
		actualizarListModel();

		subastasList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane(subastasList);

		info.add(new JLabel("Subastas Activas:"), BorderLayout.NORTH);
		info.add(scrollPane, BorderLayout.CENTER);
		info.add(ofertarButton, BorderLayout.SOUTH);

		info.setVisible(true);

		add(info, BorderLayout.CENTER);
		add(new JLabel(""), BorderLayout.WEST);
		add(new JLabel(""), BorderLayout.EAST);
		add(new JLabel(""), BorderLayout.NORTH);
		add(new JLabel(""), BorderLayout.SOUTH);

		setVisible(true);
	}

	public void actualizarListModel() {
		listModel.clear();
		for (Entry<String, Usuario> entry : padre.logins()) {
			Usuario actual = entry.getValue();
			if (actual instanceof Administrador) {
				Inventario inventario = ((Administrador) actual).getInventario();
				for (String titulo : inventario.getAlmacenadas()) {
					Pieza pieza = Pieza.piezas.get(titulo);
					if (pieza.getDisponibilidad() instanceof Subasta) {
						listModel.addElement(titulo);
					}
				}
				for (String titulo : inventario.getExhibidas()) {
					Pieza pieza = Pieza.piezas.get(titulo);
					if (pieza.getDisponibilidad() instanceof Subasta) {
						listModel.addElement(titulo);
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String titulo = subastasList.getSelectedValue();
		if (titulo != null) {
			String metodo = JOptionPane.showInputDialog("Ingrese el metodo de pago");
			int monto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el monto a ofertar"));
			boolean fallo = padre.ofertarPieza(monto, titulo, metodo);
			actualizarListModel();
			if (fallo) {
				JOptionPane.showMessageDialog(this, "La pieza fue ofertada exitosamente", "Pieza Ofertada",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Debe de seleccionar una subasta primero",
					"Error: Subasta Sin Seleccionar", JOptionPane.ERROR_MESSAGE);
		}
	}
}
