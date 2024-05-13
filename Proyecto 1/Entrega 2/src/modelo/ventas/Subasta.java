package modelo.ventas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import exceptions.UserDuplicatedException;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;

public class Subasta extends Venta {

	private HashMap<String, Integer> ofertas = new HashMap<String, Integer>();
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaUltimaOferta;
	private Duration tiempoMaximoOferta;
	private String ultimoMetodo;
	private int ultimoMonto;
	private HashMap<String, Integer> ultimaOferta = new HashMap<String, Integer>();

	public Subasta(int precioVenta, Cliente comprador, String pieza, Pago pago, HashMap<String, Integer> ofertas) {
		super(precioVenta, comprador, pieza, pago);
		this.ofertas = ofertas;
		this.fechaInicio = LocalDateTime.now();
		this.fechaUltimaOferta = LocalDateTime.now();
		this.ultimaOferta = new HashMap<String, Integer>();
		this.tiempoMaximoOferta = Duration.ofSeconds(10);
	}

	public HashMap<String, Integer> getUltimaOferta() {
		return ultimaOferta;
	}

	public void setUltimaOferta(HashMap<String, Integer> ultimaOferta) {
		this.ultimaOferta = ultimaOferta;
	}
	
	

	public int getUltimoMonto() {
		return ultimoMonto;
	}

	public void setUltimoMonto(int ultimoMonto) {
		this.ultimoMonto = ultimoMonto;
	}

	public String getUltimoMetodo() {
		return ultimoMetodo;
	}

	public void setUltimoMetodo(String ultimoMetodo) {
		this.ultimoMetodo = ultimoMetodo;
	}

	public void setFechaUltimaOferta(LocalDateTime fechaUltimaOferta) {
		this.fechaUltimaOferta = fechaUltimaOferta;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Duration getTiempoMaximoOferta() {
		return tiempoMaximoOferta;
	}

	public boolean revisarVigencia() {
		LocalDateTime ahora = LocalDateTime.now();
		Duration tiempoTrancurrido = Duration.between(fechaUltimaOferta, ahora);
		if (tiempoTrancurrido.compareTo(tiempoMaximoOferta) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setTiempoMaximoOferta(Duration tiempoMaximoOferta) {
		this.tiempoMaximoOferta = tiempoMaximoOferta;
	}

	public LocalDateTime getFechaUltimaOferta() {
		return fechaUltimaOferta;
	}

	public void setFechaUltimaOferta() {
		this.fechaUltimaOferta = LocalDateTime.now();
	}

	public void setFechaUltimaOfertaLoad(LocalDateTime fecha) {
		this.fechaUltimaOferta = fecha;
	}

	public HashMap<String, Integer> getOfertas() {
		return ofertas;
	}

	public void setOfertas(HashMap<String, Integer> ofertas) {
		this.ofertas = ofertas;
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObjectMap = new JSONObject();
		HashMap<String,Integer> nOfertas = this.getOfertas();
		if (nOfertas != null)
		{
			for (Map.Entry<String, Integer> entry : this.getOfertas().entrySet()) {
				jsonObjectMap.put(entry.getKey(), entry.getValue());
			}
		}
		jsonObject.put("ofertas", jsonObjectMap);
		jsonObject.put("fechaInicio", this.getFechaInicio());
		jsonObject.put("fechaUltimaOferta", this.getFechaUltimaOferta());
		Duration duracion = this.getTiempoMaximoOferta();
		if (duracion != null)
		{
			String duracionComoString = String.valueOf(this.getTiempoMaximoOferta().getSeconds());
			jsonObject.put("tiempoMaximoOferta", duracionComoString);
		}
		// Agregar los atributos de la clase, incluyendo los de Venta
		Venta.agregarAtributos(jsonObject, this);
		return jsonObject;
	}

	public static Subasta fromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		HashMap<String, Integer> ofertas = new HashMap<String, Integer>();
		JSONObject jsonObjectMap = new JSONObject();
		jsonObjectMap = jsonObject.getJSONObject("ofertas");
		for (String clave : jsonObjectMap.keySet()) {
			Integer valor = (Integer) jsonObjectMap.getInt(clave);
			ofertas.put(clave, valor);
		}
		Subasta subasta = new Subasta(0, null, null, null, ofertas);

		String fecha = jsonObject.getString("fechaInicio");
		LocalDateTime fechaInicio = LocalDateTime.parse(fecha);
		subasta.setFechaInicio(fechaInicio);
		String fechaUltima = jsonObject.getString("fechaUltimaOferta");
		LocalDateTime fechaUltimaOferta = LocalDateTime.parse(fechaUltima);
		subasta.setFechaUltimaOfertaLoad(fechaUltimaOferta);
		Duration tiempoMaximoOferta = null;
		if (jsonObject.has("tiempoMaximoOferta")) 
		{
			String tiempoMaximo = jsonObject.getString("tiempoMaximoOferta");
			tiempoMaximoOferta = Duration.ofSeconds(Long.parseLong(tiempoMaximo));
		}
		subasta.setTiempoMaximoOferta(tiempoMaximoOferta);
		Venta.loadSaleFromJSON(jsonObject, administrador, subasta);
		return subasta;
	}
	
	public String toString() {
		String ofertante = "";
		for(String o : ultimaOferta.keySet()) {
			ofertante = o;
		}
		return "Pieza: "+this.getPieza()+"\nUltima oferta: "+this.ultimoMonto+"\nUltimo ofertante: "+ofertante;
	}
}
