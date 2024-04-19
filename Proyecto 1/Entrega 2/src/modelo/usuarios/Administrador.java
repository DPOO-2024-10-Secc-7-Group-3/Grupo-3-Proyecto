package modelo.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.PiezaNoExistenteException;
import exceptions.UserDuplicatedException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.ventas.Fija;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;

public class Administrador extends Usuario {

	private Inventario inventario;
	private ArrayList<Cliente> clientes;
	private ArrayList<Cajero> cajeros;
	private ArrayList<Operador> operadores;
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

	public Administrador(String login, String password, String nombre, int telefono, String tipo, Inventario inventario,
			ArrayList<Cliente> clientes, ArrayList<Cajero> cajeros, ArrayList<Operador> operadores, String id) {
		super(login, password, nombre, telefono, tipo);
		this.inventario = inventario;
		this.clientes = clientes;
		this.cajeros = cajeros;
		this.operadores = operadores;
		Administrador.administradores.add(this);
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
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

			//logins.put(nLogin, newCliente);
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

	public void nuevaCompra(String titulo, Cliente cliente, String metodoDePago) throws Exception {
		inventario.buscarPieza(titulo);

		Pieza vPieza = Pieza.piezas.get(titulo);
		vPieza.setBloqueada(true);

		boolean verificado = true;

		if (cliente.getCompras().size() == 0) {
			verificado = verificar(cliente);
		}

		if (verificado) {
			boolean ocupado = true;
			int i = 0;
			Cajero cajero = null;
			while (ocupado && i < cajeros.size()) {
				Cajero actual = cajeros.get(i);
				if (!actual.isOcupado()) {
					cajero = actual;
					ocupado = false;
				}
				i++;
			}

			if (ocupado) {
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
}
