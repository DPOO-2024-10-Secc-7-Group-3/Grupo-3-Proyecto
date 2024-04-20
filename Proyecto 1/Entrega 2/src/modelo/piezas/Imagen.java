package modelo.piezas;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONObject;

import modelo.usuarios.Cliente;
import modelo.ventas.Venta;

public class Imagen extends Pieza {

	protected double ancho;
	protected double alto;
	protected int resolucion;
	private String tipo;

	public Imagen(String titulo, int anio, String lugarCreacion, String estado, LocalDate tiempoConsignacion,
			Venta disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, ArrayList<Cliente> propietarios,
			double ancho, double alto, int resolucion, String tipo, int precio, String pieza) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietarios, precio, pieza);
		this.ancho = ancho;
		this.alto = alto;
		this.resolucion = resolucion;
		this.tipo = tipo;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public int getResolucion() {
		return resolucion;
	}

	public void setResolucion(int resolucion) {
		this.resolucion = resolucion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase, incluyendo los de Pieza
		jsonObject.put("ancho", this.ancho);
		jsonObject.put("alto", this.alto);
		jsonObject.put("resolucion", this.resolucion);
		jsonObject.put("tipo", this.tipo);
		Pieza.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
