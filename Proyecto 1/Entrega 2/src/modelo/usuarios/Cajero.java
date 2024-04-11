package modelo.usuarios;

import java.util.ArrayList;
import modelo.ventas.Pago;

public class Cajero extends Usuario {

	private ArrayList<Pago> pagos;

	public Cajero(String login, String password, String nombre, int telefono, String tipo, ArrayList<Pago> pagos) {
		super(login, password, nombre, telefono, tipo);
		this.pagos = pagos;
	}

	public ArrayList<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList<Pago> pagos) {
		this.pagos = pagos;
	}
}
