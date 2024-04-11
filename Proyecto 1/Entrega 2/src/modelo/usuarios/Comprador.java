package modelo.usuarios;

import java.util.ArrayList;
import modelo.piezas.Pieza;

public class Comprador extends Usuario {

	private int valorMaximo;
	private ArrayList<Pieza> compras;

	public Comprador(String login, String password, String nombre, int telefono, String tipo, int valorMaximo,
			ArrayList<Pieza> compras) {
		super(login, password, nombre, telefono, tipo);
		this.valorMaximo = valorMaximo;
		this.compras = compras;
	}

	public int getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(int valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public ArrayList<Pieza> getCompras() {
		return compras;
	}

	public void setCompras(ArrayList<Pieza> compras) {
		this.compras = compras;
	}
}
