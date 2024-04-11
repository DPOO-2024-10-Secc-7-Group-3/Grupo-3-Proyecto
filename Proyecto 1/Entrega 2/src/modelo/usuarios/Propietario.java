package modelo.usuarios;

import java.util.ArrayList;
import modelo.piezas.Pieza;

public class Propietario extends Usuario {

	private ArrayList<Pieza> actuales;
	private ArrayList<Pieza> antiguas;

	public Propietario(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Pieza> actuales, ArrayList<Pieza> antiguas) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
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
}
