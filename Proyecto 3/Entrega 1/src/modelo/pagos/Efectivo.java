package modelo.pagos;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Efectivo extends Pago {
	public static JSONArray pagos = new JSONArray();

	public Efectivo(String tipo, int monto) {
		super(tipo, monto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pagar(String numTarjeta, String nombre, String fechaVen, String ccv, String documento) {
		boolean aprobado;
		try {
			aprobado = true; // Aqui se implementaria la logica de cada pasarela
		} catch (Exception e) {
			aprobado = false;
		}
		this.setApproved(aprobado);
		JSONObject pagoJSON = this.toJSON();
		Efectivo.pagos.put(pagoJSON);
		return aprobado;
	}
	
	@Override
	public void saveInfoToFile() throws IOException {
        try (FileWriter file = new FileWriter("data/Efectivo.json")) {
            file.write(Efectivo.pagos.toString(2)); // Pretty print JSON with 2-space indentation
        }
    }
}
