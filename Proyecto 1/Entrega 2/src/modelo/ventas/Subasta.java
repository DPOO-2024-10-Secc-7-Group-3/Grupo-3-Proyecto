package modelo.ventas;

import java.util.HashMap;

import modelo.piezas.Pieza;
import modelo.usuarios.Comprador;

public class Subasta extends Venta {

	private HashMap<String, Integer> ofertas;

	public Subasta(int precioVenta, Comprador comprador, Pieza pieza, Pago pago, HashMap<String, Integer> ofertas) {
		super(precioVenta, comprador, pieza, pago);
		this.ofertas = ofertas;
	}

	public HashMap<String, Integer> getOfertas() {
		return ofertas;
	}

	public void setOfertas(HashMap<String, Integer> ofertas) {
		this.ofertas = ofertas;
	}
}
