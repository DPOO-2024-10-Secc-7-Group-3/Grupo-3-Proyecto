package modelo.usuarios;

import modelo.Inventario;

public class Administrador extends Usuario {

	private Inventario inventario;

	public Administrador(String login, String password, String nombre, int telefono, String tipo,
			Inventario inventario) {
		super(login, password, nombre, telefono, tipo);
		this.inventario = inventario;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}
	
	public Usuario crearUsuario(String nLogin, String nPassword, String nNombre, int nTelefono, String nTipo) {
		
		Usuario newUser = Usuario(String nLogin, String nPassword, String nNombre, int nTelefono, String nTipo);
		return null;
	}
}
