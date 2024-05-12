package modelo.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import exceptions.IncorrectPasswordException;
import exceptions.UserDuplicatedException;
import exceptions.UserNotFoundException;
import modelo.piezas.Pieza;

public abstract class Usuario {

	protected String login;
	protected String password;
	protected String nombre;
	protected int telefono;
	protected String tipo;
	public static HashMap<String, Usuario> logins = new HashMap<String, Usuario>();
	public static final String CLIENTE = "cliente";
	public static final String ADMIN = "administrador";
	public static final String CAJERO = "cajero";
	public static final String OPERADOR = "operador";

	public Usuario(String login, String password, String nombre, int telefono, String tipo) {
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.telefono = telefono;
		this.tipo = tipo;
		logins.put(login, this);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static Usuario iniciarSesion(String nLogin, String nPassword)
			throws UserNotFoundException, IncorrectPasswordException {
		Usuario nUsuario = logins.get(nLogin);
		if (nUsuario == null) {
			throw new UserNotFoundException(nLogin);
		} else {
			if (!nUsuario.password.equals(nPassword)) {
				throw new IncorrectPasswordException(nPassword);
			} else {
				return nUsuario;
			}
		}
	}

	public HashMap<String, String> infoPieza(String titulo) {
		Pieza bPieza = Pieza.piezas.get(titulo);
		HashMap<String, String> r = new HashMap<String, String>();

		r.put("titulo", bPieza.getTitulo());
		r.put("anio", "" + bPieza.getAnio());
		r.put("lugar de creacion", bPieza.getLugarCreacion());

		String propietarios = "";

		ArrayList<Cliente> nPropietarios = bPieza.getPropietarios();
		for (Cliente propietario : nPropietarios) {
			propietarios += (" " + propietario.getNombre());
		}
		r.put("actuales", propietarios);

		String historicos = "";

		ArrayList<Cliente> nHistoricos = bPieza.getHistoricos();
		for (Cliente historico : nHistoricos) {
			historicos += (" " + historico.getNombre());
		}
		r.put("historicos", historicos);

		String ventas = "";

		ArrayList<LocalDateTime> fechas = bPieza.getFechas();
		ArrayList<Integer> montos = bPieza.getMontos();

		for (int i = 0; i < fechas.size(); i++) {
			String sub = "";
			sub += ("" + fechas.get(i).getYear() + "-" + fechas.get(i).getMonthValue() + "-"
					+ fechas.get(i).getDayOfMonth() + ":");
			sub += montos.get(i);
			ventas += (sub + "\n");
		}

		r.put("ventas", ventas);

		return r;
	}

	public HashMap<String, ArrayList<String>> infoArtista(String usuario) {
		Cliente cliente = (Cliente) logins.get(usuario);

		ArrayList<String> actuales = cliente.getActuales();
		ArrayList<String> antiguas = cliente.getAntiguas();

		HashMap<String, ArrayList<String>> r = new HashMap<String, ArrayList<String>>();

		for (String titulo : antiguas) {
			Pieza nPieza = Pieza.piezas.get(titulo);

			if (nPieza.getOriginal().equals(cliente)) {

				ArrayList<LocalDateTime> nFechas = nPieza.getFechas();
				ArrayList<Integer> nMontos = nPieza.getMontos();
				ArrayList<String> info = new ArrayList<String>();

				for (int i = 0; i < nFechas.size(); i++) {
					info.add("" + nFechas.get(i).getYear() + "-" + nFechas.get(i).getMonthValue() + "-"
							+ nFechas.get(i).getDayOfMonth() + ":" + nMontos.get(i));
				}

				r.put(titulo, info);
			}
		}

		for (String titulo : actuales) {
			Pieza nPieza = Pieza.piezas.get(titulo);

			if (nPieza.getOriginal().equals(cliente)) {
				ArrayList<LocalDateTime> nFechas = nPieza.getFechas();
				ArrayList<Integer> nMontos = nPieza.getMontos();
				ArrayList<String> info = new ArrayList<String>();

				for (int i = 0; i < nFechas.size(); i++) {
					info.add("" + nFechas.get(i) + ":" + nMontos.get(i));
				}

				r.put(titulo, info);
			}
		}

		return r;
	}
	
	public static void agregarAtributos(JSONObject jsonObject, Usuario usuario) {
		jsonObject.put("login", usuario.getLogin());
		jsonObject.put("password", usuario.getPassword());
		jsonObject.put("nombre", usuario.getNombre());
		jsonObject.put("telefono", usuario.getTelefono());
		jsonObject.put("tipo", usuario.getTipo());
	}
	
	public static void loadUserFromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		String login = jsonObject.getString("login");
		String password = jsonObject.getString("password");
		String nombre = jsonObject.getString("nombre");
		int telefono = jsonObject.getInt("telefono");
		String tipo = jsonObject.getString("tipo");
		administrador.crearUsuario(login, password, nombre, telefono, tipo);
	}
}
