package modelo.ventas;

public class Pago {
	
	private String tipo;
	private int monto;

	public Pago(String tipo,int monto) {
		this.tipo = tipo;
		this.monto = monto;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
