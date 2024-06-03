package modelo.pagos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class Pago {

	private String tipo;
	private int monto;
	private boolean approved;
	public static List<String> gateways = Pago.loadGateways("data/gateways.txt");

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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public abstract boolean pagar(String numTarjeta, String nombre, String fechaVen, String ccv, String documento);

	public abstract void saveInfoToFile() throws IOException;

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Agregar los atributos de la clase
		jsonObject.put("tipo", this.getTipo());
		jsonObject.put("monto", this.getMonto());
		jsonObject.put("approved", this.isApproved());
		return jsonObject;
	}

	public void addTarjetaAtributes(JSONObject pagoJSON, String numTarjeta, String nombre, String fechaVen, String ccv,
			String documento) {
		pagoJSON.put("numTarjeta", numTarjeta);
		pagoJSON.put("nombre", nombre);
		pagoJSON.put("fechaVen", fechaVen);
		pagoJSON.put("ccv", ccv);
		pagoJSON.put("documento", documento);
	}

	public static Pago fromJSON(JSONObject jsonObject) throws Exception {
		String tipo = jsonObject.getString("tipo");
		int monto = jsonObject.getInt("monto");
		Pago pago = Pago.cargaDinamica(tipo, monto);
		if (pago != null && jsonObject.has("approved")) {
			pago.setApproved(jsonObject.getBoolean("approved"));
		}
		return pago;
	}

	public static Pago cargaDinamica(String tipo, int monto) throws Exception {
		Pago pago = null;
		String clasePago = null;
		for (String gateway : Pago.gateways) {
			if (gateway.toLowerCase().contains(tipo.toLowerCase())) {
				clasePago = gateway;
				break;
			}
		}
		if (tipo.equals("efectivo")) {
			clasePago = "modelo.pagos.PayPal";
		}
		if (clasePago == null) {
			throw new Exception("El método de pago " + tipo + " no está permitido.");
		}
		try {
			Class<?> claseFija = Class.forName(clasePago);
			pago = (Pago) claseFija.getDeclaredConstructor(String.class, int.class).newInstance(tipo, monto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pago;
	}

	public static boolean equalsArray(ArrayList<Pago> pagos1, ArrayList<Pago> pagos2) {
		for (int i = 0; i < pagos1.size(); i += 1) {
			if (!pagos1.get(i).equalsPago(pagos2.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean equalsPago(Pago pago) {
		if ((this.tipo.equals(pago.getTipo())) && (this.monto == pago.getMonto())) {
			return true;
		} else {
			return false;
		}
	}

	public static List<String> loadGateways(String filePath) {
		List<String> gateways = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				gateways.add(line.trim());
			}
		} catch (Exception e) {
			System.out.println("No existe el directorio: " + filePath);
		}
		return gateways;
	}

	public static void saveAllPayments() {
		Class<?>[] subclasses = Pago.class.getDeclaredClasses();
		for (Class<?> subclass : subclasses) {
			try {
				Method saveInfoToFile = subclass.getMethod("saveInfoToFile");
				saveInfoToFile.invoke(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
