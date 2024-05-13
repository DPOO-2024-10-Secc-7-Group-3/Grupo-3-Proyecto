package modelo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.PiezaNoExistenteException;
import modelo.piezas.Pieza;

public class Inventario {

	private ArrayList<String> exhibidas;
	private ArrayList<String> almacenadas;

	public Inventario(ArrayList<String> exhibidas, ArrayList<String> almacenadas) {
		this.exhibidas = exhibidas;
		this.almacenadas = almacenadas;
	}

	public ArrayList<String> getExhibidas() {
		return exhibidas;
	}

	public void setExhibidas(ArrayList<String> exhibidas) {
		this.exhibidas = exhibidas;
	}

	public ArrayList<String> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(ArrayList<String> almacenadas) {
		this.almacenadas = almacenadas;
	}

	public void agregarPieza(String titulo, boolean exhibir) {
		if (exhibir) {
			exhibidas.add(titulo);
		} else {
			almacenadas.add(titulo);
		}
	}

	public boolean sacarPieza(String titulo) {
		boolean eliminada = exhibidas.remove(titulo);

		if (eliminada) {
			return eliminada;
		} else {
			eliminada = almacenadas.remove(titulo);
			if (eliminada) {
				return eliminada;
			} else {
				return false;
			}
		}
	}

	public boolean containsPieza(String nTitulo) {
		if (almacenadas.contains(nTitulo) | exhibidas.contains(nTitulo)) {
			return true;
		}
		return false;
	}

	public String buscarPieza(String titulo) throws PiezaNoExistenteException {
		boolean encontrado = false;

		for (int i = 0; i < exhibidas.size() && !encontrado; i++) {
			if (exhibidas.get(i).equals(titulo)) {
				encontrado = true;
			}
		}

		if (encontrado) {
			return Pieza.EXHIBIDA;
		} else {
			int a = almacenadas.size();
			for (int i = 0; i < a && !encontrado; i++) {
				if (almacenadas.get(i).equals(titulo)) {
					encontrado = true;
				}
			}

			if (encontrado) {
				return Pieza.ALMACENADA;
			} else {
				throw new PiezaNoExistenteException(titulo);
			}
		}
	}

	public static JSONObject toJSON(Inventario inventario) {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver las exhibidas en un JSONObject
		JSONArray jsonObjectExhibidas = new JSONArray();
		for (String titulo : inventario.getExhibidas()) {
			jsonObjectExhibidas.put(titulo);
		}
		// Volver las almacenadas en un JSONObject
		JSONArray jsonObjectAlmacenadas = new JSONArray();
		for (String titulo : inventario.getAlmacenadas()) {
			jsonObjectExhibidas.put(titulo);
		}
		// Aniadir ambas al JSONObject principal
		jsonObject.put("exhibidas", jsonObjectExhibidas);
		jsonObject.put("almacenadas", jsonObjectAlmacenadas);
		return jsonObject;
	}

	public static Inventario fromJSON(JSONObject jsonObject) {
		JSONObject inventarioJson = jsonObject.getJSONObject("inventario");
		JSONArray exhibidasJson = inventarioJson.getJSONArray("exhibidas");
		ArrayList<String> exhibidas = new ArrayList<>();
		for (Object titulo : exhibidasJson) {
			exhibidas.add((String) titulo);
		}
		JSONArray almacenadasJson = inventarioJson.getJSONArray("almacenadas");
		ArrayList<String> almacenadas = new ArrayList<>();
		for (Object titulo : almacenadasJson) {
			almacenadas.add((String) titulo);
		}
		Inventario inventario = new Inventario(exhibidas, almacenadas);
		return inventario;
	}
}
