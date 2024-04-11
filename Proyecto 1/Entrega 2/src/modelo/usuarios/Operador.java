package modelo.usuarios;

import java.util.ArrayList;
import modelo.ventas.Subasta;

public class Operador extends Usuario {
	
	private ArrayList<Subasta> subastas;

	public Operador(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Subasta> subastas) {
		super(login, password, nombre, telefono, tipo);
		this.subastas = subastas;
	}

	public ArrayList<Subasta> getSubastas() {
		return subastas;
	}

	public void setSubastas(ArrayList<Subasta> subastas) {
		this.subastas = subastas;
	}
}
