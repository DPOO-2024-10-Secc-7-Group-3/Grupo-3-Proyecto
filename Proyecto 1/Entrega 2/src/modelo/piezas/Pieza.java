package modelo.piezas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.usuarios.Cliente;
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
	public static final String EXHIBIDA = "exhibida";
	public static final String ALMACENADA = "almacenada";
	public static final String FUERA = "fuera";
	public static HashMap<String,Pieza> piezas = new HashMap<String, Pieza>();

	public Pieza(String titulo, int anio, String lugarCreacion, String estado, LocalDate tiempoConsignacion,
			Venta disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, ArrayList<Cliente> propietarios,int precio) {
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

	/*
	public static JSONObject toJson(Pieza pieza) {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar todos los atributos al JSONObject principal
		jsonObject.put("titulo", pieza.getTitulo());
		jsonObject.put("anio", pieza.getAnio());
		jsonObject.put("lugarCreacion", pieza.getLugarCreacion());
		jsonObject.put("estado", pieza.getEstado());
		jsonObject.put("tiempoConsignacion", pieza.getTiempoConsignacion());
		jsonObject.put("disponibilidad", pieza.getDisponibilidad());
		jsonObject.put("bloqueada", pieza.isBloqueada());
		jsonObject.put("valorMinimo", pieza.getValorMinimo());
		jsonObject.put("valorInicial", pieza.getValorInicial());
		jsonObject.put("precio", pieza.getPrecio());
		JSONArray jsonPropietarios = new JSONArray();
		for (Cliente propietario : pieza.getPropietarios()) {
			JSONObject jsonCliente = Cliente.toJson(propietario);
			jsonPropietarios.put(jsonCliente);
		}
		jsonObject.put("propietarios", jsonPropietarios);
		agregarAtributos(jsonObject);
		return jsonObject;
	}
	*/
	
	public static void agregarAtributos(JSONObject jsonObject, Pieza pieza) {
		// Agregar todos los atributos al JSONObject principal
		jsonObject.put("titulo", pieza.getTitulo());
		jsonObject.put("anio", pieza.getAnio());
		jsonObject.put("lugarCreacion", pieza.getLugarCreacion());
		jsonObject.put("estado", pieza.getEstado());
		jsonObject.put("tiempoConsignacion", pieza.getTiempoConsignacion());
		jsonObject.put("disponibilidad", pieza.getDisponibilidad());
		jsonObject.put("bloqueada", pieza.isBloqueada());
		jsonObject.put("valorMinimo", pieza.getValorMinimo());
		jsonObject.put("valorInicial", pieza.getValorInicial());
		jsonObject.put("precio", pieza.getPrecio());
		JSONArray jsonPropietarios = new JSONArray();
		for (Cliente propietario : pieza.getPropietarios()) {
			JSONObject jsonCliente = Cliente.toJson(propietario);
			jsonPropietarios.put(jsonCliente);
		}
		jsonObject.put("propietarios", jsonPropietarios);
	}
	
	public abstract JSONObject toJson(); 
}
