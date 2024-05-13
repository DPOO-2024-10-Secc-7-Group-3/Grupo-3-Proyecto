package persistencia;

import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.ventas.Fija;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.UserDuplicatedException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class CentralPersistencia {

	public void guardarDatos(String nArchivo) {
		JSONObject jsonObject = new JSONObject();
		guardarAdministradores(jsonObject);
		guardarPiezas(jsonObject);
		guardarVentas(jsonObject);
		String ruta = "data/" + nArchivo + ".json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarDatos(String nArchivo) {
		String ruta = "data/" + nArchivo + ".json";
		try {
			String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
			JSONObject jsonObject = new JSONObject(contenido);
			cargarAdministradores(jsonObject);
			cargarPiezas(jsonObject);
			cargarVentas(jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UserDuplicatedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarAdministradores(JSONObject jsonObject) {
		if (Administrador.administradores.size() == 0) {
			jsonObject.put("administradores", new JSONArray());
		} else {
			JSONArray jsonAdministradores = new JSONArray();
			for (Administrador administrador : Administrador.administradores) {
				JSONObject administradorJSON = administrador.toJSON();
				jsonAdministradores.put(administradorJSON);
			}
			jsonObject.put("administradores", jsonAdministradores);
		}
	}

	public void cargarAdministradores(JSONObject jsonObject) throws UserDuplicatedException, Exception {
		JSONArray administradores = jsonObject.getJSONArray("administradores");
		if (administradores.length() == 0) {
			System.out.println("No hay administradores que cargar");
		} else {
			for (int i = 0; i < administradores.length(); i++) {
				JSONObject adminObj = administradores.getJSONObject(i);
				Administrador.fromJSON(adminObj);
			}
		}
	}

	public void guardarPiezas(JSONObject jsonObject) {
		if (Pieza.piezas.size() == 0) {
			jsonObject.put("pieza", new JSONArray());
		} else {
			JSONArray jsonObjectMap = new JSONArray();
			for (Map.Entry<String, Pieza> entry : Pieza.piezas.entrySet()) {
				jsonObjectMap.put(entry.getValue().toJSON());
			}
			jsonObject.put("piezas", jsonObjectMap);
		}
	}

	public void cargarPiezas(JSONObject jsonObject) throws UserDuplicatedException, Exception {
		JSONArray piezas = jsonObject.getJSONArray("piezas");
		if (piezas.length() == 0) {
			System.out.println("No hay piezas que cargar");
		} else {
			for (int i = 0; i < piezas.length(); i++) {
				JSONObject piezaObj = piezas.getJSONObject(i);
				String tipoPieza = piezaObj.getString("pieza");
				JSONArray propietariosJson = piezaObj.getJSONArray("propietarios");
				int indicePropietario = propietariosJson.length() - 1;
				JSONObject propietarioJson = propietariosJson.getJSONObject(indicePropietario);
				String nLoginProp = propietarioJson.getString("login");
				Administrador admin = null;
				for (Administrador administrador : Administrador.administradores) {
					for (Cliente cliente : administrador.getClientes()) {
						if (cliente.getLogin().equals(nLoginProp)) {
							admin = administrador;
							break;
						}
					}
					if (admin != null) {
						break;
					}
				}
				Cliente propietario = admin.getCliente(nLoginProp);
				String titulo = piezaObj.getString("titulo");
				int anio = piezaObj.getInt("anio");
				String lugarCreacion = piezaObj.getString("lugarCreacion");
				int valorMinimo = piezaObj.getInt("valorMinimo");
				int valorInicial = piezaObj.getInt("valorInicial");
				int precio = piezaObj.getInt("precio");
				if (Pieza.IMAGEN.equals(tipoPieza)) {
					double ancho = piezaObj.getDouble("ancho");
					double alto = piezaObj.getDouble("alto");
					int resolucion = piezaObj.getInt("resolucion");
					String tipo = piezaObj.getString("tipo");
					propietario.crearPieza(titulo, anio, lugarCreacion, valorMinimo, valorInicial, ancho, alto,
							resolucion, tipo, precio, Pieza.IMAGEN);
				} else if (Pieza.PINTURA.equals(tipoPieza)) {
					double ancho = piezaObj.getDouble("ancho");
					double alto = piezaObj.getDouble("alto");
					String textura = piezaObj.getString("textura");
					propietario.crearPieza(titulo, anio, lugarCreacion, valorMinimo, valorInicial, ancho, alto, textura,
							precio, Pieza.PINTURA);
				} else if (Pieza.VIDEO.equals(tipoPieza)) {
					int duracion = piezaObj.getInt("duracion");
					propietario.crearPieza(titulo, anio, lugarCreacion, valorMinimo, valorInicial, duracion, precio,
							Pieza.VIDEO);
				} else {
					double ancho = piezaObj.getDouble("ancho");
					double alto = piezaObj.getDouble("alto");
					double profundidad = piezaObj.getDouble("profundidad");
					boolean electricidad = piezaObj.getBoolean("electricidad");
					ArrayList<String> materiales = new ArrayList<String>();
					JSONArray materialesJson = piezaObj.getJSONArray("materiales");
					for (int j = 0; j < materialesJson.length(); j++) {
						String material = materialesJson.getString(j);
						materiales.add(material);
					}
					propietario.crearPieza(titulo, anio, lugarCreacion, valorMinimo, valorInicial, ancho, alto,
							profundidad, materiales, electricidad, precio, tipoPieza);
				}
				Pieza pieza = Pieza.getPieza(titulo);
				pieza.addAtributesOnLoad(piezaObj, admin);
			}
		}
	}

	public void guardarVentas(JSONObject jsonObject) {
		if (Venta.ventas.size() == 0) {
			jsonObject.put("ventas", new JSONArray());
		} else {
			JSONArray jsonVentas = new JSONArray();
			for (Venta venta : Venta.ventas) {
				JSONObject ventaJSON = venta.toJSON();
				jsonVentas.put(ventaJSON);
			}
			jsonObject.put("ventas", jsonVentas);
		}
	}

	public void cargarVentas(JSONObject jsonObject) throws UserDuplicatedException, Exception {
		JSONArray ventas = jsonObject.getJSONArray("ventas");
		if (ventas.length()==0)
		{
			System.out.println("No hay ventas para cargar.");
		}
		else
		{
			for (int i = 0; i < ventas.length(); i++) {
				JSONObject ventaObj = ventas.getJSONObject(i);
				String pieza = ventaObj.getString("pieza");
				if (ventaObj.has("ofertas")) {
					Pieza piezaIns = Pieza.piezas.get(pieza);
					Cliente cliente = piezaIns.getPropietarios().get(piezaIns.getPropietarios().size()-1);
					for (Administrador admin : Administrador.administradores) {
						if (admin.getClientes().contains(cliente)) {
							Subasta.fromJSON(ventaObj, admin);
						}else {
						}
					}
				} else {
					int precioVenta = ventaObj.getInt("precioVenta");
					Pago pago = null;
					Cliente cliente = null;
					try {
						if (ventaObj.getJSONObject("pago") != (new JSONObject())) {
							JSONObject pagoJson = ventaObj.getJSONObject("pago");
							pago = Pago.fromJSON(pagoJson);
						}
					} catch (Exception e) {
						pago = null;
					}
					try {
						if (ventaObj.getJSONObject("comprador") != (new JSONObject())) {
							JSONObject compradorJson = ventaObj.getJSONObject("comprador");
							String nLogin = compradorJson.getString("login");
							for (Administrador admin : Administrador.administradores) {
								cliente = admin.getCliente(nLogin);
							}
						}
					} catch (Exception e) {
						cliente = null;
					}
					Venta venta = new Fija(precioVenta, cliente, pieza, pago);
					Venta.ventas.add(venta);
				}
			}
		}
	}
}
