package modelo.piezas;

import java.util.ArrayList;

import modelo.usuarios.Propietario;

public class Escultura extends Pieza {

	private double ancho;
	private double alto;
	private double profundidad;
	private ArrayList<String> materiales;
	private boolean electricidad;

	public Escultura(String titulo, int anio, String lugarCreacion, String estado, int tiempoConsignacion,
			String disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, Propietario propietario,
			double ancho, double alto, double profundidad, ArrayList<String> materiales, boolean electricidad) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietario);
		this.ancho = ancho;
		this.alto = alto;
		this.profundidad = profundidad;
		this.materiales = materiales;
		this.electricidad = electricidad;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(double profundidad) {
		this.profundidad = profundidad;
	}

	public ArrayList<String> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<String> materiales) {
		this.materiales = materiales;
	}

	public boolean isElectricidad() {
		return electricidad;
	}

	public void setElectricidad(boolean electricidad) {
		this.electricidad = electricidad;
	}
}
