package modelo.usuarios;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.ventas.Pago;

public class Cajero extends Usuario {

	private ArrayList<Pago> pagos;
	private boolean ocupado;

	public Cajero(String login, String password, String nombre, int telefono, String tipo, ArrayList<Pago> pagos,
			boolean ocupado) {
		super(login, password, nombre, telefono, tipo);
		this.pagos = pagos;
		this.ocupado = ocupado;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public ArrayList<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList<Pago> pagos) {
		this.pagos = pagos;
	}

	public Pago nuevoPago(String metodoDePago, int monto) {
		Pago nuevo = new Pago(metodoDePago, monto);
		pagos.add(nuevo);
		return nuevo;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver los pagos en un JSONObject
		JSONArray jsonObjectPagos = new JSONArray();
		for (Pago pago : this.getPagos()) {
			jsonObjectPagos.put(pago.toJSON());
		}
		// Aniadir al JSONObject principal
		jsonObject.put("pagos", jsonObjectPagos);
		// Agregar los atributos de la clase, incluyendo los de Usuario
		jsonObject.put("ocupado", this.isOcupado());
		Usuario.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
