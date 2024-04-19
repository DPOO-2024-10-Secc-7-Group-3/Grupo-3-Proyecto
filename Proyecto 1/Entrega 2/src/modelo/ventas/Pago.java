package modelo.ventas;

import org.json.JSONObject;

public class Pago {

	private String tipo;
	private int monto;

	public Pago(String tipo, int monto) {
		this.tipo = tipo;
		this.monto = monto;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
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
		// Agregar los atributos de la clase
		jsonObject.put("tipo", this.getTipo());
		jsonObject.put("monto", this.getMonto());
		return jsonObject;
	}
}
