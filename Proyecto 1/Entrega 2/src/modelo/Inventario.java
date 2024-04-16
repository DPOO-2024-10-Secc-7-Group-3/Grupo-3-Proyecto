package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.PiezaNoExistenteException;
import exceptions.VentaNoFijaException;
import modelo.piezas.Pieza;

public class Inventario {

	private HashMap<String,Pieza> exhibidas;
	private HashMap<String,Pieza> almacenadas;

	public Inventario(HashMap<String,Pieza> exhibidas, HashMap<String,Pieza> almacenadas) {
		this.exhibidas = exhibidas;
		this.almacenadas = almacenadas;
	}

	public HashMap<String,Pieza> getExhibidas() {
		return exhibidas;
	}

	public void setExhibidas(HashMap<String,Pieza> exhibidas) {
		this.exhibidas = exhibidas;
	}

	public HashMap<String,Pieza> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(HashMap<String,Pieza> almacenadas) {
		this.almacenadas = almacenadas;
	}
	
	public void agregarPieza(Pieza nPieza, boolean exhibir)
	{
		if(exhibir)
		{
			exhibidas.put(nPieza.getTitulo(),nPieza);
		}
		else
		{
			almacenadas.put(nPieza.getTitulo(),nPieza);
		}
	}
	
	public Pieza sacarPieza(String titulo) throws PiezaNoExistenteException
	{
		Pieza nPieza = exhibidas.remove(titulo);
		
		if (nPieza == null)
		{
			nPieza = almacenadas.remove(titulo);
			
			if (nPieza == null)
			{
				throw new PiezaNoExistenteException(titulo);
			}
		}
		
		return nPieza;
	}
	
	public Pieza bloquear (String titulo) throws VentaNoFijaException, PiezaNoExistenteException
	{
		
		Pieza nPieza = exhibidas.get(titulo);
		
		if (nPieza == null)
		{
			nPieza = almacenadas.get(titulo);
			
			if (nPieza == null)
			{
				throw new PiezaNoExistenteException(titulo);
			}
			else
			{
				if (!nPieza.getDisponibilidad().equals(Pieza.VENTA))
				{
					throw new VentaNoFijaException(titulo);
				}
				else
				{
					nPieza.setBloqueada(true);
					almacenadas.put(titulo, nPieza);
					return nPieza;
				}
			}
		}
		else
		{
			if (!nPieza.getDisponibilidad().equals(Pieza.VENTA))
			{
				throw new VentaNoFijaException(titulo);
			}
			else
			{
				nPieza.setBloqueada(true);
				exhibidas.put(titulo, nPieza);
				return nPieza;
			}
		}
	}
	
	public void desBloquear (Pieza pieza)
	{
		Pieza nPieza = exhibidas.get(pieza.getTitulo());
		
		if (nPieza == null)
		{
			nPieza = almacenadas.get(pieza.getTitulo());
			nPieza.setBloqueada(false);
			almacenadas.put(nPieza.getTitulo(), nPieza);
		}
		else
		{
			nPieza.setBloqueada(false);
			exhibidas.put(nPieza.getTitulo(), nPieza);
		}
	}
	
	public Pieza buscarPieza(String titulo) throws PiezaNoExistenteException
	{
		boolean encontrado = false;
		Pieza nPieza = null;
		
		for (int i = 0; i<exhibidas.size() && !(encontrado); i++)
		{
			if (exhibidas.get(i).getTitulo().equals(titulo))
			{
				encontrado = true;
				nPieza = exhibidas.get(i);
			}
		}
		
		if (!encontrado)
		{
			for (int i = 0; i<almacenadas.size() && !(encontrado); i++)
			{
				if (almacenadas.get(i).getTitulo().equals(titulo))
				{
					encontrado = true;
					nPieza = almacenadas.get(i);
				}
			}
		}
		
		if (!encontrado)
		{
			throw new PiezaNoExistenteException(titulo);
		}
		else
		{
			return nPieza;
		}
	}
}
