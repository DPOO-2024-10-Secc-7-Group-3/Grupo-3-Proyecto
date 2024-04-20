package modelo.piezas;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.usuarios.Cliente;
import modelo.ventas.Venta;

public class Escultura extends Pieza {

	private double ancho;
	private double alto;
	private double profundidad;
	private ArrayList<String> materiales;
	private boolean electricidad;

	public Escultura(String titulo, int anio, String lugarCreacion, String estado, LocalDate tiempoConsignacion,
			Venta disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, ArrayList<Cliente> propietarios,
			double ancho, double alto, double profundidad, ArrayList<String> materiales, boolean electricidad,
			int precio, String pieza) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietarios, precio, pieza);
		this.ancho = ancho;
		this.alto = alto;
		this.profundidad = profundidad;
		this.materiales = materiales;
		this.electricidad = electricidad;
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

	public double getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(double profundidad) {
		this.profundidad = profundidad;
	}

	public ArrayList<String> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<String> materiales) {
		this.materiales = materiales;
	}

	public boolean isElectricidad() {
		return electricidad;
	}

	public void setElectricidad(boolean electricidad) {
		this.electricidad = electricidad;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver los materiales en un JSONObject y ponerlas en el JSONObject principal
		JSONArray jsonMateriales = new JSONArray(this.materiales);
		jsonObject.put("materiales", jsonMateriales);
		// Agregar los demas atributos de la clase, incluyendo los de Pieza
		jsonObject.put("ancho", this.ancho);
		jsonObject.put("alto", this.alto);
		jsonObject.put("profundidad", this.profundidad);
		jsonObject.put("electricidad", this.electricidad);
		Pieza.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
