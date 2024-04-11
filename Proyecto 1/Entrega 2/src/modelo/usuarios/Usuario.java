package modelo.usuarios;

import java.util.HashMap;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;

public abstract class Usuario {

	protected String login;
	protected String password;
	protected String nombre;
	protected int telefono;
	protected String tipo;
	protected static HashMap<String, Usuario> logins = new HashMap<String, Usuario>();

	public Usuario(String login, String password, String nombre, int telefono, String tipo) {
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.telefono = telefono;
		this.tipo = tipo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Usuario iniciarSesion(String nLogin, String nPassword) throws UserNotFoundException, IncorrectPasswordException {
		Usuario nUsuario = logins.get(nLogin);
		if(nUsuario == null) {
			throw new UserNotFoundException(nLogin);
		}
		else {
		if (nUsuario.password != nPassword) {
			throw new IncorrectPasswordException(nPassword);
		}
		else{
			return nUsuario;
		}
		}
	}
}
