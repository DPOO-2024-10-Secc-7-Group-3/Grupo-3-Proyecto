package modelo.piezas;

import java.util.ArrayList;
import modelo.usuarios.Propietario;

public abstract class Pieza {

	protected String titulo;
	protected int anio;
	protected String lugarCreacion;
	protected String estado;
	protected int tiempoConsignacion;
	protected String disponibilidad;
	protected boolean bloqueada;
	protected int valorMinimo;
	protected int valorInicial;
	protected ArrayList<Propietario> propietarios;

	public Pieza(String titulo, int anio, String lugarCreacion, String estado, int tiempoConsignacion,
			String disponibilidad, boolean bloqueada, int valorMinimo, int valorInicial, Propietario propietario) {
		this.titulo = titulo;
		this.anio = anio;
		this.lugarCreacion = lugarCreacion;
		this.estado = estado;
		this.tiempoConsignacion = tiempoConsignacion;
		this.disponibilidad = disponibilidad;
		this.bloqueada = bloqueada;
		this.valorMinimo = valorMinimo;
		this.valorInicial = valorInicial;
		this.propietarios.add(propietario);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getLugarCreacion() {
		return lugarCreacion;
	}

	public void setLugarCreacion(String lugarCreacion) {
		this.lugarCreacion = lugarCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getTiempoConsignacion() {
		return tiempoConsignacion;
	}

	public void setTiempoConsignacion(int tiempoConsignacion) {
		this.tiempoConsignacion = tiempoConsignacion;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public int getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(int valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(int valorInicial) {
		this.valorInicial = valorInicial;
	}

	public ArrayList<Propietario> getPropietarios() {
		return propietarios;
	}

	public void setPropietarios(ArrayList<Propietario> propietarios) {
		this.propietarios = propietarios;
	}
}
