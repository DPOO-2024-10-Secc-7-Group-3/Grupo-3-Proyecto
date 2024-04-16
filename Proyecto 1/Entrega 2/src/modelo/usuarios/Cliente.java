package modelo.usuarios;

import java.util.ArrayList;

import exceptions.OfertaInvalidaException;
import exceptions.PiezaNoExistenteException;
import modelo.piezas.Pieza;

public class Cliente extends Usuario {
	
	private ArrayList<Pieza> actuales;
	private ArrayList<Pieza> antiguas;
	private ArrayList<Pieza> compras;
	private Administrador admin;
	private int valorMaximo;

	public Cliente(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Pieza>actuales, ArrayList<Pieza>antiguas,ArrayList<Pieza>compras, Administrador admin) {
		super(login, password, nombre, telefono, tipo);
		this.actuales = actuales;
		this.antiguas = antiguas;
		this.compras = compras;
		this.admin = admin;
	}

	public ArrayList<Pieza> getActuales() {
		return actuales;
	}

	public void setActuales(ArrayList<Pieza> actuales) {
		this.actuales = actuales;
	}

	public ArrayList<Pieza> getAntiguas() {
		return antiguas;
	}

	public void setAntiguas(ArrayList<Pieza> antiguas) {
		this.antiguas = antiguas;
	}

	public ArrayList<Pieza> getCompras() {
		return compras;
	}

	public void setCompras(ArrayList<Pieza> compras) {
		this.compras = compras;
	}

	public void entregarPieza(String titulo, boolean exhibir, boolean subasta) throws PiezaNoExistenteException
	{
		Pieza ePieza = null;
		for (Pieza nPieza:actuales)
		{
			if (nPieza.getTitulo().equals(titulo))
			{
				ePieza = nPieza;
			}
		}
		
		if (ePieza == null)
		{
			throw new PiezaNoExistenteException(titulo);
		}
		else
		{
			if (subasta)
			{
				ePieza.setDisponibilidad("subasta");
			}
			else
			{
				ePieza.setDisponibilidad("venta");
			}
			admin.agregarPieza(ePieza,exhibir);
		}
	}
	
	public void devolverPieza(Pieza pieza)
	{
		for (Pieza nPieza:actuales)
		{
			if (nPieza.equals(pieza))
			{
				nPieza.setDisponibilidad("devuelta");
			}
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
				compras.add(nueva);
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
		for (int i = 0; i<actuales.size();i++)
		{
			if (actuales.get(i).equals(nPieza))
			{
				Pieza vendida = actuales.remove(i);
				antiguas.add(vendida);
			}
		}
	}
}
