package modelo.usuarios;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.OfertaInvalidaException;
import exceptions.PiezaNoExistenteException;
import modelo.piezas.Pieza;

public class Cliente extends Usuario {
	
	private HashMap<String,Pieza> actuales;
	private HashMap<String,Pieza> antiguas;
	private HashMap<String,Pieza> compras;
	private Administrador admin;
	private int valorMaximo;

	public Cliente(String login, String password, String nombre, int telefono, String tipo,
			HashMap<String,Pieza>actuales, HashMap<String,Pieza>antiguas,HashMap<String,Pieza>compras, Administrador admin) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
		this.compras = compras;
		this.admin = admin;
	}

	public HashMap<String,Pieza> getActuales() {
		return actuales;
	}

	public void setActuales(HashMap<String,Pieza> actuales) {
		this.actuales = actuales;
	}

	public HashMap<String,Pieza> getAntiguas() {
		return antiguas;
	}

	public void setAntiguas(HashMap<String,Pieza> antiguas) {
		this.antiguas = antiguas;
	}

	public HashMap<String,Pieza> getCompras() {
		return compras;
	}

	public void setCompras(HashMap<String,Pieza> compras) {
		this.compras = compras;
	}
	
	public void setActual(Pieza nPieza)
	{
		actuales.put(nPieza.getTitulo(), nPieza);
	}
	
	public void setAntiguas(Pieza nPieza)
	{
		antiguas.put(nPieza.getTitulo(), nPieza);
	}
	
	public void setCompras(Pieza nPieza)
	{
		compras.put(nPieza.getTitulo(), nPieza);
	}

	public void entregarPieza(String titulo, boolean exhibir, boolean subasta) throws PiezaNoExistenteException
	{
		Pieza ePieza = actuales.get(titulo);
		
		if (ePieza == null)
		{
			throw new PiezaNoExistenteException(titulo);
		}
		else
		{
			if (subasta)
			{
				ePieza.setDisponibilidad(Pieza.SUBASTA);
			}
			else
			{
				ePieza.setDisponibilidad(Pieza.VENTA);
			}
			
			if (exhibir)
			{
				ePieza.setEstado(Pieza.EXHIBIDA);
			}
			else
			{
				ePieza.setEstado(Pieza.ALMACENADA);
			}
			
			admin.agregarPieza(ePieza,exhibir);
			
		}
	}
	
	public void comprar(String titulo) throws Exception
	{
		try
		{
			Pieza encontrada = admin.nuevaOferta(titulo);
			boolean verificado = admin.verificar(encontrada,this.valorMaximo);
			if (verificado)
			{
				Pieza nueva = admin.venderPieza(encontrada);
				compras.put(nueva.getTitulo(),nueva);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
			
	}
	
	public void ofrecerSubasta(String titulo, int oferta) throws OfertaInvalidaException, PiezaNoExistenteException
	{
		try
		{
			Pieza nPieza = admin.obtenerPieza(titulo);
			
			if (oferta > this.valorMaximo)
			{
				throw new OfertaInvalidaException();
			}
			else
			{
				if (oferta<nPieza.getValorInicial())
				{
					throw new OfertaInvalidaException();
				}
			}
		}
		catch (PiezaNoExistenteException e)
		{
			throw e;
		}
	}
	
	public void piezaVendida(Pieza nPieza)
	{
		Pieza vendida = actuales.remove(nPieza.getTitulo());
		antiguas.put(vendida.getTitulo(), vendida);
	}
}
