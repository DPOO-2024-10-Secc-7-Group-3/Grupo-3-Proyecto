package modelo.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.PiezaNoExistenteException;
import exceptions.UserDuplicatedException;
import exceptions.UserNotFoundException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.ventas.Fija;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;

public class Administrador extends Usuario {

	private Inventario inventario;
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Cajero> cajeros = new ArrayList<Cajero>();
	private ArrayList<Operador> operadores = new ArrayList<Operador>();
	public static ArrayList<Administrador> administradores = new ArrayList<Administrador>();

	public Administrador(String login, String password, String nombre, int telefono, String tipo, Inventario inventario,
			ArrayList<Cliente> clientes, ArrayList<Cajero> cajeros, ArrayList<Operador> operadores) {
		super(login, password, nombre, telefono, tipo);
		this.inventario = inventario;
		this.clientes = clientes;
		this.cajeros = cajeros;
		this.operadores = operadores;
		Administrador.administradores.add(this);
	}

	public Administrador(String login, String password, String nombre, int telefono, String tipo) {
		super(login, nombre, password, telefono, tipo);
		Administrador.administradores.add(this);
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public Cliente getCliente(String nLogin) throws UserNotFoundException {
		Cliente clienteObjetivo = null;
		for (Cliente cliente : this.clientes) {
			if (cliente.getLogin().equals(nLogin)) {
				clienteObjetivo = cliente;
			}
		}
		if (clienteObjetivo != null) {
			return clienteObjetivo;
		} else {
			throw new UserNotFoundException(nLogin);
		}
	}

	public Cajero getCajero(String nLogin) throws UserNotFoundException {
		Cajero cajeroObjetivo = null;
		for (Cajero cajero : this.cajeros) {
			if (cajero.getLogin().equals(nLogin)) {
				cajeroObjetivo = cajero;
			}
		}
		if (cajeroObjetivo != null) {
			return cajeroObjetivo;
		} else {
			throw new UserNotFoundException(nLogin);
		}
	}

	public Operador getOperador(String nLogin) throws UserNotFoundException {
		Operador operadorObjetivo = null;
		for (Operador operador : this.operadores) {
			if (operador.getLogin().equals(nLogin)) {
				operadorObjetivo = operador;
			}
		}
		if (operadorObjetivo != null) {
			return operadorObjetivo;
		} else {
			throw new UserNotFoundException(nLogin);
		}
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Cajero> getCajeros() {
		return cajeros;
	}

	public void setCajeros(ArrayList<Cajero> cajeros) {
		this.cajeros = cajeros;
	}

	public void agregarCajero(Cajero cajero) {
		this.cajeros.add(cajero);
	}

	public ArrayList<Operador> getOperadores() {
		return operadores;
	}

	public void setOperadores(ArrayList<Operador> operadores) {
		this.operadores = operadores;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public void crearUsuario(String nLogin, String nPassword, String nNombre, int nTelefono, String nTipo)
			throws UserDuplicatedException, Exception {

		if (estaDuplicado(nLogin)) {
			throw new UserDuplicatedException(nLogin);
		} else {
			Usuario newCliente;
			if (nTipo.equals(Usuario.CLIENTE)) {
				newCliente = new Cliente(nLogin, nPassword, nNombre, nTelefono, nTipo, new ArrayList<String>(),
						new ArrayList<String>(), new ArrayList<String>(), this, 2000000);
				clientes.add((Cliente) newCliente);
			} else if (nTipo.equals(Usuario.OPERADOR)) {
				newCliente = new Operador(nLogin, nPassword, nNombre, nTelefono, nTipo, new ArrayList<Subasta>());
				operadores.add((Operador) newCliente);
			} else if (nTipo.equals(Usuario.CAJERO)) {
				newCliente = new Cajero(nLogin, nPassword, nNombre, nTelefono, nTipo, new ArrayList<Pago>(), false);
				cajeros.add((Cajero) newCliente);
			} else if (nTipo.equals(Usuario.ADMIN)) {
				newCliente = new Administrador(nLogin, nPassword, nNombre, nTelefono, nTipo,
						new Inventario(new ArrayList<String>(), new ArrayList<String>()), new ArrayList<Cliente>(),
						new ArrayList<Cajero>(), new ArrayList<Operador>());
			} else {
				throw new Exception("El rol no es permitido.");
			}

			// logins.put(nLogin, newCliente);
		}
	}

	public boolean estaDuplicado(String login) {
		if (Usuario.logins.containsKey(login)) {
			return true;
		} else {
			return false;
		}
	}

	public void agregarPieza(String titulo, boolean exhibir) {
		inventario.agregarPieza(titulo, exhibir);
	}

	public void devolverPieza(String titulo) throws PiezaNoExistenteException, Exception {
		inventario.buscarPieza(titulo);

		if (LocalDate.now().isAfter(Pieza.piezas.get(titulo).getTiempoConsignacion())) {
			inventario.sacarPieza(titulo);
			Pieza.piezas.get(titulo).setEstado(Pieza.FUERA);
			Pieza.piezas.get(titulo).setDisponibilidad(null);
			Pieza.piezas.get(titulo).setTiempoConsignacion(null);
		} else {
			throw new Exception("La pieza " + titulo + " aún no ha terminado su tiempo de consignación.");
		}
	}

	public void cerrarSubasta(String titulo, String nCliente, Subasta subasta) throws Exception {
		Cliente nDuenio = this.getCliente(nCliente);
		Cajero cajero = null;
		for (Cajero actual : this.cajeros) {
			if (!actual.isOcupado()) {
				cajero = actual;
				break;
			}
		}
		if (cajero == null) {
			throw new Exception("No hay cajeros libres para hacer el pago ahora. Intente más tarde");
		} else {

			cajero.setOcupado(true);
			Pago pago = subasta.getPago();
			cajero.aniadirPago(pago);
			cajero.setOcupado(false);

			Pieza vPieza = Pieza.getPieza(titulo);
			inventario.sacarPieza(titulo);
			vPieza.setBloqueada(false);
			vPieza.setEstado(Pieza.FUERA);
			ArrayList<Cliente> nuevos = new ArrayList<Cliente>();
			nuevos.add(nDuenio);
			vPieza.setPropietarios(nuevos);
			vPieza.setTiempoConsignacion(null);
			nDuenio.getCompras().add(titulo);
			vPieza.setDisponibilidad(subasta);

			ArrayList<Cliente> propietarios = vPieza.getPropietarios();
			for (Cliente propietario : propietarios) {
				propietario.getActuales().remove(titulo);
				propietario.getAntiguas().add(titulo);
			}
		}
	}

	public void nuevaCompra(String titulo, Cliente cliente, String metodoDePago) throws Exception {
		inventario.buscarPieza(titulo);

		Pieza vPieza = Pieza.piezas.get(titulo);
		vPieza.setBloqueada(true);

		boolean verificado = true;

		if (cliente.getCompras().size() == 0) {
			verificado = verificar(cliente);
		}

		if (verificado) {
			Cajero cajero = null;
			for (Cajero actual : this.cajeros) {
				if (!actual.isOcupado()) {
					cajero = actual;
					break;
				}
			}
			if (cajero == null) {
				throw new Exception("No hay cajeros libres para hacer el pago ahora. Intente más tarde");
			} else {
				if (vPieza.getPrecio() > cliente.getValorMaximo()) {
					throw new Exception("El cliente " + cliente.getLogin() + " debe ampliar su límite de compras.");
				} else {
					cajero.setOcupado(true);
					Pago pago = cajero.nuevoPago(metodoDePago, Pieza.piezas.get(titulo).getPrecio());
					cajero.setOcupado(false);

					inventario.sacarPieza(titulo);
					vPieza.setBloqueada(false);
					vPieza.setEstado(Pieza.FUERA);
					ArrayList<Cliente> nuevos = new ArrayList<Cliente>();
					nuevos.add(cliente);
					vPieza.setPropietarios(nuevos);
					vPieza.setTiempoConsignacion(null);
					cliente.getCompras().add(titulo);

					Fija nueva = new Fija(vPieza.getPrecio(), cliente, vPieza.getTitulo(), pago);
					vPieza.setDisponibilidad(nueva);

					ArrayList<Cliente> propietarios = vPieza.getPropietarios();
					for (Cliente propietario : propietarios) {
						propietario.getActuales().remove(titulo);
						propietario.getAntiguas().add(titulo);
					}
				}
			}
		} else {
			throw new Exception("Cliente " + cliente.getLogin() + " no verificado.");
		}

	}

	public boolean verificar(Cliente cliente) {
		return cliente.darCodigo() == 123;
	}

	public String crearId() {
		char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		int charsLength = chars.length;
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			buffer.append(chars[random.nextInt(charsLength)]);
		}
		return buffer.toString();
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver el inventario en un JSONObject y ponerlos en el JSONObject principal
		JSONObject jsonInventario = Inventario.toJSON(this.getInventario());
		jsonObject.put("inventario", jsonInventario);
		// Volver los clientes en un JSONObject y ponerlos en el JSONObject principal
		JSONArray jsonClientes = new JSONArray();
		for (Cliente cliente : this.getClientes()) {
			JSONObject clienteJSON = cliente.toJSON();
			jsonClientes.put(clienteJSON);
		}
		// Aniadir al JSONObject principal
		jsonObject.put("clientes", jsonClientes);
		// Volver los cajeros en un JSONObject y ponerlos en el JSONObject principal
		JSONArray jsonCajeros = new JSONArray();
		for (Cajero cajero : this.getCajeros()) {
			JSONObject cajeroJSON = cajero.toJSON();
			jsonCajeros.put(cajeroJSON);
		}
		// Aniadir al JSONObject principal
		jsonObject.put("cajeros", jsonCajeros);
		// Volver los operadores en un JSONObject y ponerlos en el JSONObject principal
		JSONArray jsonOperadores = new JSONArray();
		for (Operador operador : this.getOperadores()) {
			JSONObject operadorJSON = operador.toJSON();
			jsonOperadores.put(operadorJSON);
		}
		// Aniadir al JSONObject principal
		jsonObject.put("operadores", jsonOperadores);
		Usuario.agregarAtributos(jsonObject, this);
		return jsonObject;
	}

	public static void fromJSON(JSONObject jsonObject) throws UserDuplicatedException, Exception {
		String login = jsonObject.getString("login");
		String password = jsonObject.getString("password");
		String nombre = jsonObject.getString("nombre");
		int telefono = jsonObject.getInt("telefono");
		String tipo = jsonObject.getString("tipo");
		Administrador admin = new Administrador(login, password, nombre, telefono, tipo);
		Inventario inventario = Inventario.fromJSON(jsonObject);
		admin.setInventario(inventario);
		ArrayList<Cliente> clientes = Cliente.fromJSON(jsonObject, admin);
		admin.setClientes(clientes);
		ArrayList<Cajero> cajeros = Cajero.fromJSON(jsonObject, admin);
		admin.setCajeros(cajeros);
		ArrayList<Operador> operadores = Operador.fromJSON(jsonObject, admin);
		admin.setOperadores(operadores);
	}
}
