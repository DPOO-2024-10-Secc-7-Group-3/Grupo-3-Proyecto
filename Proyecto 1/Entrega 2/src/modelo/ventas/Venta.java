package modelo.ventas;

import modelo.piezas.Pieza;
import modelo.usuarios.Cliente;

public abstract class Venta {

	private int precioVenta;
	private Cliente comprador;
	private String pieza;
	private Pago pago;
	
	public Venta(int precioVenta, Cliente comprador, String pieza, Pago pago) {
		this.precioVenta = precioVenta;
		this.comprador = comprador;
		this.pieza = pieza;
		this.pago = pago;
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
}
