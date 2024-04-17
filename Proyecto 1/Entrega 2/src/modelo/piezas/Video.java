package modelo.piezas;

import org.json.JSONObject;

import modelo.usuarios.Cliente;

public class Video extends Pieza {

	private int duracion;

	public Video(String titulo, int anio, String lugarCreacion, String estado, int tiempoConsignacion,
			String disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, Cliente propietario,
			int duracion) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietario);
		this.duracion = duracion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public JSONObject toJson() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase, incluyendo los de Pieza
		jsonObject.put("duracion", this.duracion);
		Pieza.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
