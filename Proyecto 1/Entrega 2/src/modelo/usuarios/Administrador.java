package modelo.usuarios;

import java.util.ArrayList;

import exceptions.UserDuplicatedException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;

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
				 newCliente = new Cliente(nLogin, nPassword,nNombre,nTelefono,nTipo,new ArrayList<Pieza>(),new ArrayList<Pieza>(),new ArrayList<Pieza>());
			}	
			else if(nTipo.equals(Usuario.OPERADOR)){
				 newCliente = new Operador(nLogin, nPassword,nNombre,nTelefono,nTipo,new ArrayList<Subasta>());
			}	
			else if(nTipo.equals(Usuario.CAJERO)){
				 newCliente = new Cajero(nLogin, nPassword,nNombre,nTelefono,nTipo, new ArrayList<Pago>());
			}	
			else {
				 newCliente = new Administrador(nLogin, nPassword,nNombre,nTelefono,nTipo, new Inventario(new ArrayList<Pieza>(),new ArrayList<Pieza>()));
			}	
			
			logins.put(nLogin,newCliente);
		}
	}
	
	public boolean estaDuplicado(String login)
	{
		if(Usuario.logins.containsKey(login))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
