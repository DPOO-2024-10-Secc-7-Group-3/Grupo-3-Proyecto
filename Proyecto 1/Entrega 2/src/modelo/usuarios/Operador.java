package modelo.usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.OfertaInvalidaException;
import exceptions.PiezaNoExistenteException;
import exceptions.SubastaInexistente;
import exceptions.UserDuplicatedException;
import modelo.piezas.Pieza;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;

public class Operador extends Usuario {

	private ArrayList<Subasta> subastas;

	public Operador(String login, String password, String nombre, int telefono, String tipo,
			ArrayList<Subasta> subastas) {
		super(login, password, nombre, telefono, tipo);
		this.subastas = subastas;
	}

	public ArrayList<Subasta> getSubastas() {
		return subastas;
	}

	public void setSubastas(ArrayList<Subasta> subastas) {
		this.subastas = subastas;
	}

	public Subasta getSubasta(String nTitulo) {
		for (Subasta subasta : subastas) {
			if (subasta.getPieza().equals(nTitulo)) {
				return subasta;
			}
		}
		return null;
	}

	public JSONObject toJSON() {
		// Definir el JSONObject principal
		JSONObject jsonObject = new JSONObject();
		// Volver los cajeros en un JSONObject y ponerlos en el JSONObject principal
		JSONArray jsonSubastas = new JSONArray();
		for (Subasta subasta : this.getSubastas()) {
			JSONObject subastaJSON = subasta.toJSON();
			jsonSubastas.put(subastaJSON);
		}
		// Aniadir al JSONObject principal
		jsonObject.put("subastas", jsonSubastas);
		Usuario.agregarAtributos(jsonObject, this);
		return jsonObject;
	}

	public static ArrayList<Operador> fromJSON(JSONObject jsonObject, Administrador administrador)
			throws UserDuplicatedException, Exception {
		JSONArray jsonOperadores = jsonObject.getJSONArray("operadores");
		ArrayList<Operador> operadores = new ArrayList<Operador>();
		for (Object obj : jsonOperadores) {
			JSONObject operadorJson = (JSONObject) obj;
			Usuario.loadUserFromJSON(operadorJson, administrador);
			JSONArray subastasJson = operadorJson.getJSONArray("subastas");
			ArrayList<Subasta> subastas = new ArrayList<Subasta>();
			for (Object subastaObj : subastasJson) {
				JSONObject subastaJson = (JSONObject) subastaObj;
				Subasta subastaAdd = Subasta.fromJSON(subastaJson, administrador);
				subastas.add(subastaAdd);
			}
			String nLogin = operadorJson.getString("login");
			Operador operador = administrador.getOperador(nLogin);
			operador.setSubastas(subastas);
			operadores.add(operador);
		}
		return operadores;
	}

	public void iniciarSubasta(String nTitulo, Administrador administrador) throws PiezaNoExistenteException {
		Pieza pieza = Pieza.getPieza(nTitulo);
		if (pieza == null) {
			throw new PiezaNoExistenteException(nTitulo);
		}
		pieza.setBloqueada(true);
		int valorInicial = pieza.getValorInicial();
		Subasta subasta = new Subasta(valorInicial, null, nTitulo, null, null);
		this.subastas.add(subasta);
	}

	public void ofertarPieza(Cliente cliente, Integer oferta, String pieza, String metodo)
			throws OfertaInvalidaException {
		Subasta subasta = getSubasta(pieza);
		if ((subasta == null) && ((Integer) cliente.getValorMaximo() < oferta)) {
			throw new OfertaInvalidaException();
		}
		int precio = subasta.getUltimoMonto();
		if (precio > oferta) {
			throw new OfertaInvalidaException();
		}
		HashMap<String, Integer> ofertaMap = subasta.getOfertas();
		HashMap<String, Integer> ultimaMap = new HashMap<String, Integer>();
		ultimaMap.put(cliente.getLogin(), oferta);
		ofertaMap.put(cliente.getLogin(), oferta);
		subasta.setFechaUltimaOferta();
		subasta.setUltimoMetodo(metodo);
		subasta.setUltimaOferta(ultimaMap);
		subasta.setUltimoMonto((int) oferta);
	}

	public void checkSubastaDuracion(String pieza, Administrador admin) throws Exception {
		Subasta subasta = getSubasta(pieza);
		if (subasta == null) {
			throw new SubastaInexistente(pieza);
		}
		boolean revisor = subasta.revisarVigencia();
		if (revisor) {
			if (subasta.getOfertas().size() == 0) {
				subasta.removeVenta(subasta);
				subastas.remove(subasta);
			} else {
				HashMap<String, Integer> ultimaMap = subasta.getUltimaOferta();
				String comprador = "";
				int precio = 0;
				for (Map.Entry<String, Integer> entry : ultimaMap.entrySet()) {
					comprador = entry.getKey();
					precio = (int) entry.getValue();
				}
				Pago pago = new Pago(subasta.getUltimoMetodo(), precio);
				subasta.setPago(pago);
				admin.cerrarSubasta(pieza, comprador, subasta);
			}
		}
	}
}
