package modelo.ventas;

import modelo.piezas.Pieza;
import modelo.usuarios.Comprador;

public abstract class Venta {

	private int precioVenta;
	private Comprador comprador;
	private Pieza pieza;
	private Pago pago;
	
	public Venta(int precioVenta, Comprador comprador, Pieza pieza, Pago pago) {
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

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public Pieza getPieza() {
		return pieza;
	}

	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}
}
