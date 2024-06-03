package modelo.piezas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.UserDuplicatedException;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;

public abstract class Pieza {

	protected String titulo;
	protected int anio;
	protected String lugarCreacion;
	protected String estado;
	protected LocalDate tiempoConsignacion;
	protected Venta disponibilidad;
	protected boolean bloqueada;
	protected int valorMinimo;
	protected int valorInicial;
	protected int precio;
	protected ArrayList<Cliente> propietarios;
	protected ArrayList<Cliente> historicos;
	protected ArrayList<LocalDateTime> fechas;
	protected ArrayList<Integer> montos;
	protected Cliente original;
	public static final String EXHIBIDA = "exhibida";
	public static final String ALMACENADA = "almacenada";
	public static final String FUERA = "fuera";
	public static HashMap<String, Pieza> piezas = new HashMap<String, Pieza>();
	public static final String ESCULTURA = "escultura";
	public static final String IMAGEN = "imagen";
	public static final String PINTURA = "pintura";
	public static final String VIDEO = "video";
	private String pieza;

	public Pieza(String titulo, int anio, String lugarCreacion, String estado, LocalDate tiempoConsignacion,
			Venta disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, ArrayList<Cliente> propietarios,
			int precio, String pieza, Cliente original) {
		this.titulo = titulo;
		this.anio = anio;
		this.lugarCreacion = lugarCreacion;
		this.estado = estado;
		this.tiempoConsignacion = tiempoConsignacion;
		this.disponibilidad = disponibilidad;
		this.bloqueada = bloqueada;
		this.valorMinimo = valorMinimo;
		this.valorInicial = valorInicial;
		this.propietarios = propietarios;
		this.precio = precio;
		this.pieza = pieza;

		this.historicos = new ArrayList<Cliente>();
		this.fechas = new ArrayList<LocalDateTime>();
		this.montos = new ArrayList<Integer>();

		this.original = original;
	}

	public Cliente getOriginal() {
		return original;
	}

	public void setOriginal(Cliente original) {
		this.original = original;
	}

	public ArrayList<Cliente> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(ArrayList<Cliente> historicos) {
		this.historicos = historicos;
	}

	public ArrayList<LocalDateTime> getFechas() {
		return fechas;
	}

	public void setFechas(ArrayList<LocalDateTime> fechas) {
		this.fechas = fechas;
	}

	public ArrayList<Integer> getMontos() {
		return montos;
	}

	public void setMontos(ArrayList<Integer> montos) {
		this.montos = montos;
	}

	public String getTipoPieza() {
		return pieza;
	}

	public void setTipoPieza(String pieza) {
		this.pieza = pieza;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getLugarCreacion() {
		return lugarCreacion;
	}

	public void setLugarCreacion(String lugarCreacion) {
		this.lugarCreacion = lugarCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getTiempoConsignacion() {
		return tiempoConsignacion;
	}

	public void setTiempoConsignacion(LocalDate tiempoConsignacion) {
		this.tiempoConsignacion = tiempoConsignacion;
	}

	public Venta getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Venta disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public int getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(int valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(int valorInicial) {
		this.valorInicial = valorInicial;
	}

	public ArrayList<Cliente> getPropietarios() {
		return propietarios;
	}

	public void setPropietarios(ArrayList<Cliente> propietarios) {
		this.propietarios = propietarios;
	}

	public static Pieza getPieza(String nTitulo) {
		Pieza pieza = Pieza.piezas.get(nTitulo);
		return pieza;
	}

	public static void agregarAtributos(JSONObject jsonObject, Pieza pieza) {
		// Agregar todos los atributos al JSONObject principal
		jsonObject.put("titulo", pieza.getTitulo());
		jsonObject.put("anio", pieza.getAnio());
		jsonObject.put("lugarCreacion", pieza.getLugarCreacion());
		jsonObject.put("estado", pieza.getEstado());
		jsonObject.put("tiempoConsignacion", pieza.getTiempoConsignacion());
		if (pieza.getDisponibilidad() != null) {
			jsonObject.put("disponibilidad", pieza.getDisponibilidad().toJSON());
		}
		jsonObject.put("bloqueada", pieza.isBloqueada());
		jsonObject.put("valorMinimo", pieza.getValorMinimo());
		jsonObject.put("valorInicial", pieza.getValorInicial());
		jsonObject.put("precio", pieza.getPrecio());
		jsonObject.put("pieza", pieza.getTipoPieza());
		JSONArray jsonPropietarios = new JSONArray();
		for (Cliente propietario : pieza.getPropietarios()) {
			JSONObject jsonCliente = propietario.toJSON();
			jsonPropietarios.put(jsonCliente);
		}
		jsonObject.put("propietarios", jsonPropietarios);
		JSONArray jsonHistoricos = new JSONArray();
		for (Cliente historico : pieza.getHistoricos()) {
			String login = historico.getLogin();
			jsonHistoricos.put(login);
		}
		jsonObject.put("historicos", jsonHistoricos);
		JSONArray jsonFechas = new JSONArray();
		for (LocalDateTime fecha : pieza.getFechas()) {
			jsonFechas.put(fecha);
		}
		jsonObject.put("fechas", jsonFechas);
		JSONArray jsonMontos = new JSONArray();
		for (int monto : pieza.getMontos()) {
			jsonMontos.put(monto);
		}
		jsonObject.put("montos", jsonMontos);
		String jsonOriginal = pieza.getOriginal().getLogin();
		jsonObject.put("original", jsonOriginal);
	}

	public abstract JSONObject toJSON();

	@SuppressWarnings("null")
	public void addAtributesOnLoad(JSONObject jsonObject, Administrador admin)
			throws UserDuplicatedException, Exception {
		if (jsonObject.has("disponibilidad")) {
			JSONObject disponibilidad = jsonObject.getJSONObject("disponibilidad");
			if (disponibilidad.has("ofertas")) {
				Subasta ventaPieza = Subasta.fromJSON(disponibilidad, admin);
				this.setDisponibilidad(ventaPieza);
			} else {
				Fija ventaPieza = Fija.fromJSON(disponibilidad, admin);
				this.setDisponibilidad(ventaPieza);
			}
		} else {
			Venta ventaPieza = null;
			this.setDisponibilidad(ventaPieza);
		}
		if (jsonObject.has("tiempoConsignacion")) {
			String tiempo = jsonObject.getString("tiempoConsignacion");
			LocalDate tiempoConsignacion = LocalDate.parse(tiempo);
			this.setTiempoConsignacion(tiempoConsignacion);
		} else {
			LocalDate tiempoConsignacion = null;
			this.setTiempoConsignacion(tiempoConsignacion);
		}
		String estado = jsonObject.getString("estado");
		boolean bloqueada = jsonObject.getBoolean("bloqueada");
		this.setEstado(estado);
		this.setBloqueada(bloqueada);
		if (jsonObject.has("original")) {
			Cliente original = (Cliente) Usuario.logins.get(jsonObject.get("original"));
			this.setOriginal(original);
		}
		this.setHistoricos(new ArrayList<Cliente>());
		if (jsonObject.has("historicos")) {
			JSONArray historicos = jsonObject.getJSONArray("historicos");
			for (int i = 0; i < historicos.length(); i++) {
				String login = historicos.getString(i);
				Cliente historico = (Cliente) Usuario.logins.get(login);
				this.historicos.add(historico);
			}
		}
		this.fechas = new ArrayList<LocalDateTime>();
		if (jsonObject.has("fechas")) {
			JSONArray fechas = jsonObject.getJSONArray("fechas");
			for (int i = 0; i < fechas.length(); i++) {
				String fecha = fechas.getString(i);
				LocalDateTime nFecha = LocalDateTime.parse(fecha);
				this.fechas.add(nFecha);
			}
		}
		this.montos = new ArrayList<Integer>();
		if (jsonObject.has("montos")) {
			JSONArray montos = jsonObject.getJSONArray("montos");
			for (int i = 0; i < montos.length(); i++) {
				int monto = montos.getInt(i);
				this.montos.add(monto);
			}
		}
	}
}
