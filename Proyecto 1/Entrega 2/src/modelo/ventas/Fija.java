package modelo.ventas;

import org.json.JSONObject;

import modelo.usuarios.Cliente;

public class Fija extends Venta {

	public Fija(int precioVenta, Cliente comprador, String pieza, Pago pago) {
		super(precioVenta, comprador, pieza, pago);
		// TODO Auto-generated constructor stub
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase, incluyendo los de Venta
		Venta.agregarAtributos(jsonObject, this);
		return jsonObject;
	}
}
