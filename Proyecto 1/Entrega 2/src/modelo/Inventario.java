package modelo;

import java.util.ArrayList;
import modelo.piezas.Pieza;

public class Inventario {

	private ArrayList<Pieza> exhibidas;
	private ArrayList<Pieza> almacenadas;

	public Inventario(ArrayList<Pieza> exhibidas, ArrayList<Pieza> almacenadas) {
		this.exhibidas = exhibidas;
		this.almacenadas = almacenadas;
	}

	public ArrayList<Pieza> getExhibidas() {
		return exhibidas;
	}

	public void setExhibidas(ArrayList<Pieza> exhibidas) {
		this.exhibidas = exhibidas;
	}

	public ArrayList<Pieza> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(ArrayList<Pieza> almacenadas) {
		this.almacenadas = almacenadas;
	}
}
