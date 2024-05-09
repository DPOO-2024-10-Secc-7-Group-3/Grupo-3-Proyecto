package modelo.usuarios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.PiezaNoExistenteException;
import exceptions.UserDuplicatedException;
import modelo.piezas.Escultura;
import modelo.piezas.Imagen;
import modelo.piezas.Pieza;
import modelo.piezas.Pintura;
import modelo.piezas.Video;
import modelo.ventas.Fija;
import modelo.ventas.Subasta;

public class Cliente extends Usuario {

	private ArrayList<String> actuales;
	private ArrayList<String> antiguas;
	private ArrayList<String> compras;
	private ArrayList<LocalDateTime> fechas;
	private int valorMaximo;
	private Administrador admin;
	public static final int VERIFICACION = 123;

	public Cliente(String login, String password, String nombre, int telefono, String tipo, ArrayList<String> actuales,
			ArrayList<String> antiguas, ArrayList<String> compras, Administrador admin, int valorMaximo) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
		this.compras = compras;
		this.admin = admin;
		this.valorMaximo = valorMaximo;
		
		this.fechas = new ArrayList<LocalDateTime>();
	}

	public ArrayList<LocalDateTime> getFechas() {
		return fechas;
	}

	public void setFechas(ArrayList<LocalDateTime> fechas) {
		this.fechas = fechas;
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

	public String buscarPieza(String titulo) throws Exception {
		boolean encontrado = false;

		for (int i = 0; i < actuales.size() && !encontrado; i++) {
			if (actuales.get(i).equals(titulo)) {
				encontrado = true;
			}
		}

		if (encontrado) {
			return "actuales";
		} else {
			for (int i = 0; i < antiguas.size() && !encontrado; i++) {
				if (antiguas.get(i).equals(titulo)) {
					encontrado = true;
				}
			}

			if (encontrado) {
				return "antiguas";
			} else {
				for (int i = 0; i < compras.size() && !encontrado; i++) {
					if (compras.get(i).equals(titulo)) {
						encontrado = true;
					}
				}

				if (encontrado) {
					return "compras";
				} else {
					throw new Exception(
							"La pieza " + titulo + " no está en las piezas del cliente " + this.getLogin() + ".");
				}
			}
		}
	}

	public void crearPieza(String titulo, int anio, String lugarCreacion, int valorMinimo, int valorInicial,
			double ancho, double alto, double profundidad, ArrayList<String> materiales, boolean electricidad,
			int precio, String pieza) throws Exception {
		if (Pieza.ESCULTURA.equals(pieza)) {
			ArrayList<Cliente> propietarios = new ArrayList<Cliente>();
			propietarios.add(this);
			Escultura nueva = new Escultura(titulo, anio, lugarCreacion, Pieza.FUERA, null, null, false, valorMinimo,
					valorInicial, propietarios, ancho, alto, profundidad, materiales, electricidad, precio, pieza,this);
			if (Pieza.piezas.containsKey(titulo)) {
				throw new Exception("El título " + titulo + " ya fue usado en otra pieza.");
			} else {
				Pieza.piezas.put(titulo, nueva);
				actuales.add(titulo);
			}
		} else {
			System.out.println("Se esta intentando crea un/a " + pieza + " como una escultura.");
		}
	}

	public void crearPieza(String titulo, int anio, String lugarCreacion, int valorMinimo, int valorInicial,
			double ancho, double alto, int resolucion, String tipo, int precio, String pieza) throws Exception {
		if (Pieza.IMAGEN.equals(pieza)) {
			ArrayList<Cliente> propietarios = new ArrayList<Cliente>();
			propietarios.add(this);
			Imagen nueva = new Imagen(titulo, anio, lugarCreacion, Pieza.FUERA, null, null, false, valorMinimo,
					valorInicial, propietarios, ancho, alto, resolucion, tipo, precio, pieza,this);
			if (Pieza.piezas.containsKey(titulo)) {
				throw new Exception("El título " + titulo + " ya fue usado en otra pieza.");
			} else {
				Pieza.piezas.put(titulo, nueva);
				actuales.add(titulo);
			}
		} else {
			System.out.println("Se esta intentando crea un/a " + pieza + " como una imagen.");
		}
	}

	public void crearPieza(String titulo, int anio, String lugarCreacion, int valorMinimo, int valorInicial,
			double ancho, double alto, String textura, int precio, String pieza) throws Exception {
		if (Pieza.PINTURA.equals(pieza)) {
			ArrayList<Cliente> propietarios = new ArrayList<Cliente>();
			propietarios.add(this);
			Pintura nueva = new Pintura(titulo, anio, lugarCreacion, Pieza.FUERA, null, null, false, valorMinimo,
					valorInicial, propietarios, ancho, alto, textura, precio, pieza,this);
			if (Pieza.piezas.containsKey(titulo)) {
				throw new Exception("El título " + titulo + " ya fue usado en otra pieza.");
			} else {
				Pieza.piezas.put(titulo, nueva);
				actuales.add(titulo);
			}
		} else {
			System.out.println("Se esta intentando crea un/a " + pieza + " como una pintura.");
		}
	}

	public void crearPieza(String titulo, int anio, String lugarCreacion, int valorMinimo, int valorInicial,
			int duracion, int precio, String pieza) throws Exception {
		if (Pieza.VIDEO.equals(pieza)) {
			ArrayList<Cliente> propietarios = new ArrayList<Cliente>();
			propietarios.add(this);
			Video nueva = new Video(titulo, anio, lugarCreacion, Pieza.FUERA, null, null, false, valorMinimo,
					valorInicial, propietarios, duracion, precio, pieza,this);
			if (Pieza.piezas.containsKey(titulo)) {
				throw new Exception("El título " + titulo + " ya fue usado en otra pieza.");
			} else {
				Pieza.piezas.put(titulo, nueva);
				actuales.add(titulo);
			}
		} else {
			System.out.println("Se esta intentando crea un/a " + pieza + " como un video.");
		}
	}

	public void entregarPieza(String titulo, boolean exhibir, boolean subasta, LocalDate tiempo)
			throws PiezaNoExistenteException, Exception {

		String lugar = buscarPieza(titulo);

		if (lugar.equals("actuales") || lugar.equals("compras")) {
			Pieza ePieza = Pieza.piezas.get(titulo);

			ePieza.setTiempoConsignacion(tiempo);

			if (subasta) {
				ePieza.setDisponibilidad(new Subasta(-1, null, ePieza.getTitulo(), null, null));
			} else {
				ePieza.setDisponibilidad(new Fija(ePieza.getPrecio(), null, ePieza.getTitulo(), null));
			}

			if (exhibir) {
				ePieza.setEstado(Pieza.EXHIBIDA);
			} else {
				ePieza.setEstado(Pieza.ALMACENADA);
			}

			admin.agregarPieza(titulo, exhibir);
		}
	}

	public void comprar(String titulo, String metodoDePago) throws Exception {
		admin.nuevaCompra(titulo, this, metodoDePago);
	}

	public int darCodigo() {
		return VERIFICACION;
	}

	public int getPiezaIndex(String titulo, ArrayList<String> lista) {
		for (int index = 0; index < lista.size(); index++) {
			if (lista.get(index) == titulo) {
				return index;
			}
		}
		return -1;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver las actuales en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> actuales = this.getActuales();
		JSONArray jsonActuales = new JSONArray(actuales);
		jsonObject.put("actuales", jsonActuales);
		// Volver las antiguas en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> antiguas = this.getAntiguas();
		JSONArray jsonAntiguas = new JSONArray(antiguas);
		jsonObject.put("antiguas", jsonAntiguas);
		// Volver las compras en un JSONObject y ponerlas en el JSONObject principal
		ArrayList<String> compras = this.getCompras();
		JSONArray jsonCompras = new JSONArray(compras);
		jsonObject.put("compras", jsonCompras);
		// Agregar los demas atributos de la clase, incluyendo los de Usuario
		jsonObject.put("valorMaximo", this.getValorMaximo());
		Usuario.agregarAtributos(jsonObject, this);
		return jsonObject;
	}

	public static ArrayList<Cliente> fromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		JSONArray jsonClientes = jsonObject.getJSONArray("clientes");
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		for (Object obj : jsonClientes) {
			JSONObject clienteJson = (JSONObject) obj;
			Cliente cliente = Cliente.loadClientFromJSON(clienteJson, administrador);
			clientes.add(cliente);
		}
		return clientes;
	}

	public static Cliente loadClientFromJSON(JSONObject clienteJson, Administrador administrador)
			throws UserDuplicatedException, Exception {
		Usuario.loadUserFromJSON(clienteJson, administrador);
		JSONArray actualesJson = clienteJson.getJSONArray("actuales");
		ArrayList<String> actuales = new ArrayList<String>();
		for (Object titulo : actualesJson) {
			actuales.add((String) titulo);
		}
		JSONArray antiguasJson = clienteJson.getJSONArray("antiguas");
		ArrayList<String> antiguas = new ArrayList<String>();
		for (Object titulo : antiguasJson) {
			antiguas.add((String) titulo);
		}
		JSONArray comprasJson = clienteJson.getJSONArray("actuales");
		ArrayList<String> compras = new ArrayList<String>();
		for (Object titulo : comprasJson) {
			compras.add((String) titulo);
		}
		String login = clienteJson.getString("login");
		Cliente cliente = administrador.getCliente(login);
		cliente.setActuales(actuales);
		cliente.setAntiguas(antiguas);
		cliente.setCompras(compras);
		return cliente;
	}
}
