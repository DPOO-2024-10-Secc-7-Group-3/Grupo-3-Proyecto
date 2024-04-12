package modelo.usuarios;

import exceptions.UserDuplicatedException;
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
	
	public void crearUsuario(String nLogin, String nPassword, String nNombre, int nTelefono, String nTipo) throws UserDuplicatedException
	{
		
		if (estaDuplicado(nLogin))
		{
			throw new UserDuplicatedException(nLogin);
		}
		else
		{
			Usuario newCliente;
			if(nTipo.equals(Usuario.CLIENTE)){
				 newCliente = new Cliente(nLogin, nPassword,nNombre,nTelefono,nTipo);
			}	
			else if(nTipo.equals(Usuario.OPERADOR)){
				 newCliente = new Operador(nLogin, nPassword,nNombre,nTelefono,nTipo);
			}	
			else if(nTipo.equals(Usuario.CAJERO)){
				 newCliente = new Cajero(nLogin, nPassword,nNombre,nTelefono,nTipo);
			}	
			else {
				 newCliente = new Administrador(nLogin, nPassword,nNombre,nTelefono,nTipo);
			}	
			
			logins.put(nLogin,newCliente);
		}
	}
	
}
