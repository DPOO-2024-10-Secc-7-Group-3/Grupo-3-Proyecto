package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import exceptions.PiezaNoExistenteException;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Subasta;
import persistencia.CentralPersistencia;

public class Controlador {

	private CentralPersistencia cp = new CentralPersistencia();

	public Controlador(String archivo) {
		cargarDatos(archivo);
	}

	private void cargarDatos(String archivo) {
		cp.cargarDatos(archivo);
	}

	@SuppressWarnings("unused")
	private void guardarDatos(String archivo) {
		cp.guardarDatos(archivo);
	}

	public HashMap<String, ArrayList<String>> infoCliente(Usuario user, String cliente) {
		return ((Administrador) user).infoCliente(cliente);
	}

	public void setPassoword(Usuario user, String password) {
		user.setPassword(password);
	}

	public void devolverPieza(Usuario user, String nTitulo) throws Exception {
		((Administrador) user).devolverPieza(nTitulo);
	}

	public ArrayList<String> getAlmacenadas(Usuario user) {
		return ((Administrador) user).getInventario().getAlmacenadas();
	}

	public ArrayList<String> getExhibidas(Usuario user) {
		return ((Administrador) user).getInventario().getExhibidas();
	}

	public Set<Entry<String, Usuario>> logins() {
		return Usuario.logins.entrySet();
	}

	public void cerrarSubasta(Usuario user, String subasta) throws Exception {
		Subasta buffer = null;
		Operador operador1 = null;

		for (Operador operador : ((Administrador) user).getOperadores()) {
			buffer = operador.getSubasta(subasta);

			if (!(buffer == null)) {
				operador1 = operador;
			}
		}

		operador1.checkSubastaDuracion(subasta, (Administrador) user);
		System.out.println("Se cerró la subasta de " + subasta + " con éxito.");
	}

	public Pieza getPieza(String pieza) {
		return Pieza.piezas.get(pieza);
	}

	public void crearPieza(Usuario user, String titulo, int anio, String lugar, int valorMinimo, int valorInicial,
			double ancho, double alto, double profundidad, ArrayList<String> nMateriales, boolean electricidad,
			int precio, String string) throws Exception {
		((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, profundidad,
				nMateriales, electricidad, precio, "escultura");
	}

	public void crearPieza(Usuario user, String titulo, int anio, String lugar, int valorMinimo, int valorInicial,
			double ancho, double alto, String textura, int precio, String string) throws Exception {
		((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, textura, precio,
				"pintura");
	}

	public void crearPieza(Usuario user, String titulo, int anio, String lugar, int valorMinimo, int valorInicial,
			int duracion, int precio, String string) throws Exception {
		((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, duracion, precio, "video");
	}

	public void crearPieza(Usuario user, String titulo, int anio, String lugar, int valorMinimo, int valorInicial,
			double ancho, double alto, int resolucion, String nTipo, int precio, String string) throws Exception {
		((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto, resolucion, nTipo,
				precio, "imagen");
	}

	public void entregarPieza(Usuario user, String titulo, boolean nExhibir, boolean nSubasta) throws PiezaNoExistenteException, Exception {
		((Cliente) user).entregarPieza(titulo, nExhibir, nSubasta, LocalDate.now());
	}

	public void comprar(Usuario user, String titulo, String metodo) throws Exception {
		((Cliente) user).comprar(titulo, metodo);
	}
}
