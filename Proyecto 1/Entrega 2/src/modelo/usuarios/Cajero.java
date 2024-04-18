package modelo.usuarios;

import java.util.ArrayList;
import modelo.ventas.Pago;

public class Cajero extends Usuario {

	private ArrayList<Pago> pagos;
	private boolean ocupado; 

	public Cajero(String login, String password, String nombre, int telefono, String tipo, ArrayList<Pago> pagos,boolean ocupado) {
		super(login, password, nombre, telefono, tipo);
		this.pagos = pagos;
		this.ocupado = ocupado;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public ArrayList<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList<Pago> pagos) {
		this.pagos = pagos;
	}
	
	public Pago nuevoPago(String metodoDePago,int monto)
	{
		Pago nuevo = new Pago(metodoDePago,monto);
		pagos.add(nuevo);
		return nuevo;
	}

}
