package modelo.usuarios;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.OfertaInvalidaException;
import exceptions.PiezaNoExistenteException;
import modelo.Inventario;
import modelo.piezas.Pieza;

public class Cliente extends Usuario {

	private ArrayList<String> actuales;
	private ArrayList<String> antiguas;
	private ArrayList<String> compras;
	private int valorMaximo;
	private Administrador admin;

	public Cliente(String login, String password, String nombre, int telefono, String tipo, ArrayList<String> actuales,
			ArrayList<String> antiguas, ArrayList<String> compras, Administrador admin) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
		this.compras = compras;
		this.admin = admin;
	}

	public ArrayList<String> getActuales() {
		return actuales;
	}

	public void setActuales(ArrayList<String> actuales) {
		this.actuales = actuales;
	}

	public ArrayList<String> getAntiguas() {
		return antiguas;
	}

	public void setAntiguas(ArrayList<String> antiguas) {
		this.antiguas = antiguas;
	}

	public ArrayList<String> getCompras() {
		return compras;
	}

	public void setCompras(ArrayList<String> compras) {
		this.compras = compras;
	}

	public void setActual(Pieza nPieza) {
		actuales.add(nPieza.getTitulo());
	}

	public void setAntiguas(Pieza nPieza) {
		antiguas.add(nPieza.getTitulo());
	}

	public void setCompras(Pieza nPieza) {
		compras.add(nPieza.getTitulo());
	}

	public int getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(int valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public void entregarPieza(String titulo, boolean exhibir, boolean subasta) throws PiezaNoExistenteException {
		Inventario inventario = admin.getInventario();
		Pieza ePieza = inventario.buscarPieza(titulo);

		if (subasta) {
			ePieza.setDisponibilidad(Pieza.SUBASTA);
		} else {
			ePieza.setDisponibilidad(Pieza.VENTA);
		}

		if (exhibir) {
			ePieza.setEstado(Pieza.EXHIBIDA);
		} else {
			ePieza.setEstado(Pieza.ALMACENADA);
		}

		admin.agregarPieza(ePieza, exhibir);

	}

	public void comprar(String titulo) throws Exception {
		try {
			Pieza encontrada = admin.nuevaOferta(titulo);
			boolean verificado = admin.verificar(encontrada, this.valorMaximo);
			if (verificado) {
				Pieza nueva = admin.venderPieza(encontrada);
				compras.add(nueva.getTitulo());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void ofrecerSubasta(String titulo, int oferta) throws OfertaInvalidaException, PiezaNoExistenteException {
		try {
			Pieza nPieza = admin.obtenerPieza(titulo);

			if (oferta > this.valorMaximo) {
				throw new OfertaInvalidaException();
			} else {
				if (oferta < nPieza.getValorInicial()) {
					throw new OfertaInvalidaException();
				}
			}
		} catch (PiezaNoExistenteException e) {
			throw e;
		}
	}

	public void piezaVendida(Pieza nPieza) throws PiezaNoExistenteException {
		Pieza vendida = admin.getInventario().buscarPieza(nPieza.getTitulo());
		actuales.remove(nPieza.getTitulo());
		antiguas.add(vendida.getTitulo());
	}

	public int getPiezaIndex(String titulo, ArrayList<String> lista) {
		for (int index = 0; index < lista.size(); index++) {
			if (lista.get(index) == titulo) {
				return index;
			}
		}
		return -1;
	}

	public static JSONObject toJson(Cliente cliente) {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver las actuales en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> actuales = cliente.getActuales();
		JSONArray jsonActuales = new JSONArray(actuales);
		jsonObject.put("actuales", jsonActuales);
		// Volver las antiguas en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> antiguas = cliente.getAntiguas();
		JSONArray jsonAntiguas = new JSONArray(antiguas);
		jsonObject.put("antiguas", jsonAntiguas);
		// Volver las compras en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> compras = cliente.getCompras();
		JSONArray jsonCompras = new JSONArray(compras);
		jsonObject.put("compras", jsonCompras);
		// Agregar los demas atributos de la clase, incluyendo los de Usuario
		jsonObject.put("valorMaximo", cliente.getValorMaximo());
		Usuario.agregarAtributos(jsonObject, cliente);
		return jsonObject;
	}
}
