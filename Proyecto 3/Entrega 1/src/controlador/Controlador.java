package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import modelo.usuarios.Administrador;
import modelo.usuarios.Usuario;
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

	public void devolverPieza(Usuario user, String nTitulo) throws Exception{
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
}
