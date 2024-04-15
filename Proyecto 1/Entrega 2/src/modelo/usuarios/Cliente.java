package modelo.usuarios;

import java.util.ArrayList;

import modelo.piezas.Pieza;

public class Cliente extends Usuario {
	
	private ArrayList<Pieza> actuales;
	private ArrayList<Pieza> antiguas;
	private ArrayList<Pieza> compras;

	public Cliente(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Pieza>actuales, ArrayList<Pieza>antiguas,ArrayList<Pieza>compras) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
		this.compras = compras;
	}

	public ArrayList<Pieza> getActuales() {
		return actuales;
	}

	public void setActuales(ArrayList<Pieza> actuales) {
		this.actuales = actuales;
	}

	public ArrayList<Pieza> getAntiguas() {
		return antiguas;
	}

	public void setAntiguas(ArrayList<Pieza> antiguas) {
		this.antiguas = antiguas;
	}

	public ArrayList<Pieza> getCompras() {
		return compras;
	}

	public void setCompras(ArrayList<Pieza> compras) {
		this.compras = compras;
	}

	
}
