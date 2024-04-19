package persistencia;

import modelo.Inventario;
import modelo.piezas.Pieza;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CentralPersistencia {

	public void guardarDatos(String nCarpeta, Inventario inventario) {
		guardarInventario(nCarpeta, inventario);
	}

	private void guardarInventario(String nCarpeta, Inventario inventario) {
		
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver las exhibidas en un JSONObject
		HashMap<String, Pieza> exhibidas = inventario.getExhibidas();
		JSONObject jsonObjectExhibidas = new JSONObject();
		for (Map.Entry<String, Pieza> entry : exhibidas.entrySet()) {
			jsonObjectExhibidas.put(entry.getKey(), entry.getValue().toJson());
		}
		// Volver las almacenadas en un JSONObject
		HashMap<String, Pieza> almacenadas = inventario.getAlmacenadas();
		JSONObject jsonObjectAlmacenadas = new JSONObject();
		for (Map.Entry<String, Pieza> entry : almacenadas.entrySet()) {
			jsonObjectAlmacenadas.put(entry.getKey(), entry.getValue().toJson());
		}
		// Aniadir ambas al JSONObject principal
		jsonObject.put("exhibidas", jsonObjectExhibidas);
		jsonObject.put("almacenadas", jsonObjectAlmacenadas);
		// Definir la ruta y el nombre del archivo y guardarlos en la carpeta data/
		String ruta = "data/" + nCarpeta + "/inventario.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarDatos() {

	}
}
