package modelo.piezas;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONObject;

import modelo.usuarios.Cliente;
import modelo.ventas.Venta;

public class Pintura extends Pieza {

	private double ancho;
	private double alto;
	private String textura;

	public Pintura(String titulo, int anio, String lugarCreacion, String estado, LocalDate tiempoConsignacion,
			Venta disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, ArrayList<Cliente> propietarios,
			double ancho, double alto, String textura, int precio, String pieza, Cliente original) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietarios, precio, pieza, original);
		this.ancho = ancho;
		this.alto = alto;
		this.textura = textura;
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

	public String getTextura() {
		return textura;
	}

	public void setTextura(String textura) {
		this.textura = textura;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase, incluyendo los de Pieza
		jsonObject.put("ancho", this.ancho);
		jsonObject.put("alto", this.alto);
		jsonObject.put("textura", this.textura);
		Pieza.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
