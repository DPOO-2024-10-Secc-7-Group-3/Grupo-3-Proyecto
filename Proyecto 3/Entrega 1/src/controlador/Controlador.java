package controlador;

import java.util.ArrayList;
import java.util.HashMap;

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
}
