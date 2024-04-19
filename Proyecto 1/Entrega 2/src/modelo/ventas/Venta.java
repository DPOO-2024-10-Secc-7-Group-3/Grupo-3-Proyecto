package modelo.ventas;

import java.util.ArrayList;

import org.json.JSONObject;

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
		jsonObject.put("pago", venta.getPago().toJSON());
		jsonObject.put("comprador", venta.getComprador().toJSON());
	}
	
	public abstract JSONObject toJSON();
}
