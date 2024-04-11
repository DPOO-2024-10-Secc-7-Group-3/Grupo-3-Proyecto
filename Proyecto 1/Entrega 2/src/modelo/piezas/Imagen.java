package modelo.piezas;

import modelo.usuarios.Propietario;

public abstract class Imagen extends Pieza {

	protected double ancho;
	protected double alto;
	protected int resolucion;

	public Imagen(String titulo, int anio, String lugarCreacion, String estado, int tiempoConsignacion,
			String disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, Propietario propietario,
			double ancho, double alto, int resolucion) {
		super(titulo, anio, lugarCreacion, estado, tiempoConsignacion, disponibilidad, bloqueada, valorMinimo,
				valorInicial, propietario);
		this.ancho = ancho;
		this.alto = alto;
		this.resolucion = resolucion;
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

	public int getResolucion() {
		return resolucion;
	}

	public void setResolucion(int resolucion) {
		this.resolucion = resolucion;
	}
}
