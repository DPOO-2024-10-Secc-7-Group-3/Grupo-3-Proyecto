package modelo.ventas;

import org.json.JSONObject;

import exceptions.UserDuplicatedException;
import modelo.pagos.Pago;
import modelo.usuarios.Administrador;
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
	
	public static Fija fromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		Fija fija = new Fija(1, null, null, null);
		Venta.loadSaleFromJSON(jsonObject, administrador, fija);
		return fija;
	}
}
