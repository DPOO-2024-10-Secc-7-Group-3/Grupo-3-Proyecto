package modelo;

import java.util.ArrayList;

import exceptions.PiezaNoExistenteException;
import exceptions.VentaNoFijaException;
import modelo.piezas.Pieza;

public class Inventario {

	private ArrayList<Pieza> exhibidas;
	private ArrayList<Pieza> almacenadas;

	public Inventario(ArrayList<Pieza> exhibidas, ArrayList<Pieza> almacenadas) {
		this.exhibidas = exhibidas;
		this.almacenadas = almacenadas;
	}

	public ArrayList<Pieza> getExhibidas() {
		return exhibidas;
	}

	public void setExhibidas(ArrayList<Pieza> exhibidas) {
		this.exhibidas = exhibidas;
	}

	public ArrayList<Pieza> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(ArrayList<Pieza> almacenadas) {
		this.almacenadas = almacenadas;
	}
	
	public void agregarPieza(Pieza nPieza, boolean exhibir)
	{
		if(exhibir)
		{
			exhibidas.add(nPieza);
		}
		else
		{
			almacenadas.add(nPieza);
		}
	}
	
	public Pieza sacarPieza(String titulo) throws PiezaNoExistenteException
	{
		boolean sacar = false;
		Pieza nPieza = null;
		
		for (int i = 0; i<exhibidas.size() && !(sacar); i++)
		{
			if (exhibidas.get(i).getTitulo().equals(titulo))
			{
				sacar = true;
				exhibidas.remove(i);
				nPieza =  exhibidas.get(i);
			}
		}
		
		if (!sacar)
		{
			for (int i = 0; i<almacenadas.size() && !(sacar); i++)
			{
				if (almacenadas.get(i).getTitulo().equals(titulo))
				{
					sacar = true;
					almacenadas.remove(i);
					nPieza = almacenadas.get(i);
				}
			}
		}
		
		if (!sacar)
		{
			throw new PiezaNoExistenteException(titulo);
		}
		else
		{
			return nPieza;
		}
	}
	
	public Pieza bloquear (String titulo) throws VentaNoFijaException, PiezaNoExistenteException
	{
		boolean encontrado = false;
		Pieza nPieza = null;
		
		for (int i = 0; i<exhibidas.size() && !(encontrado); i++)
		{
			if (exhibidas.get(i).getTitulo().equals(titulo))
			{
				encontrado = true;
				nPieza =  exhibidas.get(i);
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
			if (!nPieza.getDisponibilidad().equals("venta"))
			{
				throw new VentaNoFijaException(titulo);
			}
			else
			{
				nPieza.setBloqueada(true);
				return nPieza;
			}
		}
	}
	
	public void desBloquear (Pieza pieza)
	{
		boolean encontrado = false;
		
		for (int i = 0; i<exhibidas.size() && !(encontrado); i++)
		{
			if (exhibidas.get(i).equals(pieza))
			{
				encontrado = true;
				exhibidas.get(i).setBloqueada(false);;
			}
		}
		
		if (!encontrado)
		{
			for (int i = 0; i<almacenadas.size() && !(encontrado); i++)
			{
				if (almacenadas.get(i).equals(pieza))
				{
					encontrado = true;
					almacenadas.get(i).setBloqueada(false);;
				}
			}
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
