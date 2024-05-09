package modelo.ventas;

import java.util.ArrayList;

import org.json.JSONObject;

import exceptions.UserDuplicatedException;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;

public abstract class Venta {

	private int precioVenta;
	private Cliente comprador;
	private String pieza;
	private Pago pago;
	public static ArrayList<Venta> ventas = new ArrayList<Venta>();

	public Venta(int precioVenta, Cliente comprador, String pieza, Pago pago) {
		this.precioVenta = precioVenta;
		this.comprador = comprador;
		this.pieza = pieza;
		this.pago = pago;
		Venta.ventas.add(this);
	}

	public int getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(int precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Cliente getComprador() {
		return comprador;
	}

	public void setComprador(Cliente comprador) {
		this.comprador = comprador;
	}

	public String getPieza() {
		return pieza;
	}

	public void setPieza(String pieza) {
		this.pieza = pieza;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public static void agregarAtributos(JSONObject jsonObject, Venta venta) {
		// Agregar todos los atributos al JSONObject principal
		jsonObject.put("precioVenta", venta.getPrecioVenta());
		jsonObject.put("pieza", venta.getPieza());
		if (venta.getPago() != null) {
			jsonObject.put("pago", venta.getPago().toJSON());
		} else {
			jsonObject.put("pago", "");
		}
		if (venta.getComprador() != null) {
			jsonObject.put("comprador", venta.getComprador().toJSON());
		} else {
			jsonObject.put("comprador", "");
		}
	}

	public abstract JSONObject toJSON();

	public static void loadSaleFromJSON(JSONObject jsonObject, Administrador administrador, Venta venta)
			throws UserDuplicatedException, Exception {
		int precioVenta = jsonObject.getInt("precioVenta");
		String pieza = jsonObject.getString("pieza");
		Pago pago = null;
		if (jsonObject.has("pago")) {
			JSONObject pagoJson = jsonObject.getJSONObject("pago");
			pago = Pago.fromJSON(pagoJson);
		}
		Cliente comprador = null;
		if (jsonObject.has("comprador")) {
			JSONObject compradorJson = jsonObject.getJSONObject("comprador");
			String login = compradorJson.getString("login");
			comprador = administrador.getCliente(login);
		}
		venta.setComprador(comprador);
		venta.setPago(pago);
		venta.setPieza(pieza);
		venta.setPrecioVenta(precioVenta);
	}

	public void removeVenta(Venta venta) {
		ventas.remove(venta);
	}
}
