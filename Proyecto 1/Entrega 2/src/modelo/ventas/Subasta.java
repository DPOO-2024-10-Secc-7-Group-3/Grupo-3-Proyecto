package modelo.ventas;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import modelo.usuarios.Cliente;

public class Subasta extends Venta {

	private HashMap<String, Integer> ofertas;

	public Subasta(int precioVenta, Cliente comprador, String pieza, Pago pago, HashMap<String, Integer> ofertas) {
		super(precioVenta, comprador, pieza, pago);
		this.ofertas = ofertas;
	}

	public HashMap<String, Integer> getOfertas() {
		return ofertas;
	}

	public void setOfertas(HashMap<String, Integer> ofertas) {
		this.ofertas = ofertas;
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObjectMap = new JSONObject();
		for (Map.Entry<String, Integer> entry : this.getOfertas().entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
		jsonObject.put("ofertas", jsonObjectMap);
		// Agregar los atributos de la clase, incluyendo los de Venta
		Venta.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
