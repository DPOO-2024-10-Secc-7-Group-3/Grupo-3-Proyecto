package persistencia;

import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.ventas.Venta;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CentralPersistencia {

	public void guardarDatos(String nArchivo) {
		JSONObject jsonObject = new JSONObject();
		guardarAdministradores(jsonObject);
		guardarPiezas(jsonObject);
		guardarVentas(jsonObject);
		String ruta = "data/" + nArchivo + ".json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void guardarAdministradores(JSONObject jsonObject) {
		if (Administrador.administradores.size() == 0) {
			System.out.println("No hay administradores que guardar");
		} else {
			JSONArray jsonAdministradores = new JSONArray();
			for (Administrador administrador : Administrador.administradores) {
				JSONObject administradorJSON = administrador.toJSON();
				jsonAdministradores.put(administradorJSON);
			}
			jsonObject.put("administradores", jsonAdministradores);
		}
	}

	public void guardarPiezas(JSONObject jsonObject) {
		if (Pieza.piezas.size() == 0) {
			System.out.println("No hay piezas que guardar");
		} else {
			JSONArray jsonObjectMap = new JSONArray();
			for (Map.Entry<String, Pieza> entry : Pieza.piezas.entrySet()) {
				jsonObjectMap.put(entry.getValue().toJSON());
			}
			jsonObject.put("piezas", jsonObjectMap);
		}
	}

	public void guardarVentas(JSONObject jsonObject) {
		if (Venta.ventas.size() == 0) {
			System.out.println("No hay ventas que guardar");
		} else {
			JSONArray jsonVentas = new JSONArray();
			for (Venta venta : Venta.ventas) {
				JSONObject ventaJSON = venta.toJSON();
				jsonVentas.put(ventaJSON);
			}
			jsonObject.put("ventas", jsonVentas);
		}
	}

	public void cargarDatos(String nArchivo) {

	}
	
	public void cargarAdministradores() {
		
	}
	
	public void cargarPiezas() {
		
	}
	
	public void cargarVentas() {
		
	}
}
