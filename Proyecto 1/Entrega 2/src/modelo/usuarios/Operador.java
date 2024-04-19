package modelo.usuarios;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.ventas.Subasta;

public class Operador extends Usuario {
	
	private ArrayList<Subasta> subastas;

	public Operador(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Subasta> subastas) {
		super(login, password, nombre, telefono, tipo);
		this.subastas = subastas;
	}

	public ArrayList<Subasta> getSubastas() {
		return subastas;
	}

	public void setSubastas(ArrayList<Subasta> subastas) {
		this.subastas = subastas;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver los cajeros en un JSONObject y ponerlos en el JSONObject principal
		JSONArray jsonSubastas = new JSONArray();
		for (Subasta subasta : this.getSubastas()) {
			JSONObject subastaJSON = subasta.toJSON();
			jsonSubastas.put(subastaJSON);
		}
		// Aniadir al JSONObject principal
		jsonObject.put("subastas", jsonSubastas);
		Usuario.agregarAtributos(jsonObject, this);
		return jsonObject;
	}	
}
