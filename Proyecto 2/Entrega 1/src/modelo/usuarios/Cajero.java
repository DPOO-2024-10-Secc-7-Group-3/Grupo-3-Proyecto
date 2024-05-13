package modelo.usuarios;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.UserDuplicatedException;
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
	
	public void aniadirPago(Pago pago) {
		pagos.add(pago);
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

	public static ArrayList<Cajero> fromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		JSONArray jsonCajeros = jsonObject.getJSONArray("cajeros");
		ArrayList<Cajero> cajeros = new ArrayList<Cajero>();
		for (Object obj : jsonCajeros) {
			JSONObject cajeroJson = (JSONObject) obj;
			Cajero cajero = Cajero.loadCajeroFromJSON(cajeroJson, administrador);
			cajeros.add(cajero);
		}
		return cajeros;
	}
	
	public static Cajero loadCajeroFromJSON(JSONObject cajeroJson, Administrador administrador) throws Exception {
		Usuario.loadUserFromJSON(cajeroJson, administrador);
		JSONArray pagosJson = cajeroJson.getJSONArray("pagos");
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		for (Object pagoObj : pagosJson) {
			JSONObject pagoJson = (JSONObject) pagoObj;
			Pago pago = Pago.fromJSON(pagoJson);
			pagos.add(pago);
		}
		boolean ocupado = cajeroJson.getBoolean("ocupado");
		String login = cajeroJson.getString("login");
		Cajero cajero = administrador.getCajero(login);
		cajero.setOcupado(ocupado);
		cajero.setPagos(pagos);
		return cajero;
	}
	
	public boolean equalsJSON(Cajero cajero) {
		boolean user = ((Usuario) cajero).equalsUser((Usuario) this);
		boolean boo = this.ocupado == (cajero.isOcupado());
		boolean pag = Pago.equalsArray(this.pagos, cajero.getPagos());
		return user && boo && pag;
	}
}
