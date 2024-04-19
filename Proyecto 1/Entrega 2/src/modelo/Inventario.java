package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.PiezaNoExistenteException;
import exceptions.VentaNoFijaException;
import modelo.piezas.Pieza;

public class Inventario {

	private ArrayList<String> exhibidas;
	private ArrayList<String> almacenadas;

	public Inventario(ArrayList<String> exhibidas, ArrayList<String> almacenadas) {
		this.exhibidas = exhibidas;
		this.almacenadas = almacenadas;
	}

	public ArrayList<String> getExhibidas() {
		return exhibidas;
	}

	public void setExhibidas(ArrayList<String> exhibidas) {
		this.exhibidas = exhibidas;
	}

	public ArrayList<String> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(ArrayList<String> almacenadas) {
		this.almacenadas = almacenadas;
	}

	public void agregarPieza(String titulo, boolean exhibir) {
		if (exhibir) {
			exhibidas.add(titulo);
		} else {
			almacenadas.add(titulo);
		}
	}

	public boolean sacarPieza(String titulo) {
		boolean eliminada = exhibidas.remove(titulo);
		
		if (eliminada)
		{
			return eliminada;
		}
		else
		{
			eliminada = almacenadas.remove(titulo);
			if (eliminada)
			{
				return eliminada;
			}
			else
			{
				return false;
			}
		}
	}

	public String buscarPieza(String titulo) throws PiezaNoExistenteException, Exception {
		boolean encontrado = false;
		
		for (int i = 0; i<exhibidas.size() && !encontrado; i++)
		{
			if (exhibidas.get(i).equals(titulo))
			{
				encontrado = true;
			}
		}
		
		if (encontrado)
		{
			return Pieza.EXHIBIDA;
		}
		else
		{
			int a = almacenadas.size();
			for (int i = 0; i<almacenadas.size() && !encontrado; i++)
			{
				if (almacenadas.get(i).equals(titulo))
				{
					encontrado = true;
				}
			}
			
			if (encontrado)
			{
				return Pieza.ALMACENADA;
			}
			else
			{
				throw new Exception ("La pieza "+titulo+" no está en el inventario.");
			}
		}
	}
}
