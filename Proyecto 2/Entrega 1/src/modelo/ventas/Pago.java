package modelo.ventas;

import java.util.ArrayList;

import org.json.JSONObject;

public class Pago {

	private String tipo;
	private int monto;

	public Pago(String tipo, int monto) {
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

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase
		jsonObject.put("tipo", this.getTipo());
		jsonObject.put("monto", this.getMonto());
		return jsonObject;
	}

	public static Pago fromJSON(JSONObject jsonObject) {
		String tipo = jsonObject.getString("tipo");
		int monto = jsonObject.getInt("monto");
		Pago pago = new Pago(tipo, monto);
		return pago;
	}
	
	public static boolean equalsArray(ArrayList<Pago> pagos1, ArrayList<Pago> pagos2) {
		for(int i=0;i<pagos1.size();i+=1) {
			if(!pagos1.get(i).equalsPago(pagos2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equalsPago(Pago pago) {
		if((this.tipo.equals(pago.getTipo())) && (this.monto == pago.getMonto())) {
			return true;
		}else {			
			return false;
		}
	}
}
