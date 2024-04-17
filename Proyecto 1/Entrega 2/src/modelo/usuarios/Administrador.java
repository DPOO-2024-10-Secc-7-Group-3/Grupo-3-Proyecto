package modelo.usuarios;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.PiezaNoExistenteException;
import exceptions.UserDuplicatedException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;

public class Administrador extends Usuario {

	private Inventario inventario;
	private ArrayList<Cliente> clientes;

	public Administrador(String login, String password, String nombre, int telefono, String tipo,
			Inventario inventario,ArrayList<Cliente> clientes) {
		super(login, password, nombre, telefono, tipo);
		this.inventario = inventario;
		this.clientes = clientes;
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
				 newCliente = new Cliente(nLogin, nPassword,nNombre,nTelefono,nTipo,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),this);
				 clientes.add((Cliente)newCliente);
			}	
			else if(nTipo.equals(Usuario.OPERADOR)){
				 newCliente = new Operador(nLogin, nPassword,nNombre,nTelefono,nTipo,new ArrayList<Subasta>());
			}	
			else if(nTipo.equals(Usuario.CAJERO)){
				 newCliente = new Cajero(nLogin, nPassword,nNombre,nTelefono,nTipo, new ArrayList<Pago>());
			}	
			else {
				 newCliente = new Administrador(nLogin, nPassword,nNombre,nTelefono,nTipo, new Inventario(new HashMap<String,Pieza>(),new HashMap<String,Pieza>()), new ArrayList<Cliente>());
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
	
	public void agregarPieza(Pieza nPieza, boolean exhibir)
	{
		inventario.agregarPieza(nPieza,exhibir);
		
		ArrayList<Cliente> propietarios = nPieza.getPropietarios();
		
		for (Cliente propietario:propietarios)
		{
			propietario.setActual(nPieza);
		}
	}
	
	public void devolverPieza(String titulo) throws PiezaNoExistenteException
	{
		try
		{
			Pieza pieza = inventario.sacarPieza(titulo);
			
			pieza.setEstado(Pieza.FUERA);
			
			ArrayList<Cliente> propietarios = pieza.getPropietarios();
			
			for (Cliente cliente:propietarios)
			{
				cliente.setActual(pieza);
			}
		}
		catch (PiezaNoExistenteException e)
		{
			throw e;
		}
	}
	
	public Pieza nuevaOferta(String titulo) throws Exception
	{
		try
		{
			return inventario.bloquear(titulo);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public boolean verificar(Pieza encontrada, int valorMaximo)
	{
		if (encontrada.getPrecio()>valorMaximo)
		{
			inventario.desBloquear(encontrada);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Pieza venderPieza(Pieza encontrada)
	{
		try
		{
			Pieza nPieza = inventario.sacarPieza(encontrada.getTitulo());
			ArrayList<Cliente> propietarios = nPieza.getPropietarios();
			
			for (Cliente propietario:propietarios)
			{
				propietario.piezaVendida(nPieza);
			}
			
			return nPieza;
		}
		catch (PiezaNoExistenteException e)
		{
			return null;
		}
	}
	
	public Pieza obtenerPieza(String titulo) throws PiezaNoExistenteException
	{
		try
		{
			return inventario.buscarPieza(titulo);
		}
		catch (PiezaNoExistenteException e)
		{
			throw e;
		}
	}
}
