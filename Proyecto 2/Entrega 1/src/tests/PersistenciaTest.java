package tests;

import static org.junit.Assert.fail;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import exceptions.UserDuplicatedException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;
import persistencia.CentralPersistencia;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersistenciaTest {

	private static Administrador admin1;
	private static Inventario inventario;
	private static Cliente cliente1;
	private static Cajero cajero1;
	private static Operador operador1;
	private static CentralPersistencia cp;

	@BeforeAll
	static void setUp() throws Exception {
		
		cp = new CentralPersistencia();
		ArrayList<String> exhibidas = new ArrayList<String>();
		exhibidas.add("piezaExhibida1");
		exhibidas.add("piezaExhibida2");
		ArrayList<String> almacenadas = new ArrayList<String>();
		almacenadas.add("piezaAlmacenada3");
		almacenadas.add("piezaAlmacenada4");
		inventario = new Inventario(exhibidas, almacenadas);
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);
		cliente1 = admin1.getCliente("userPrueba");
		cliente1.setActuales(almacenadas);
		cliente1.setAntiguas(exhibidas);
		ArrayList<String> compras = new ArrayList<String>();
		compras.add("piezaCompras5");
		cliente1.setCompras(compras);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);
		cajero1 = admin1.getCajero("cajeroPrueba");
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		Pago pago = new Pago("efectivo", 5);
		pagos.add(pago);
		cajero1.setPagos(pagos);
		admin1.crearUsuario("operadorPrueba", "789", "Juan", 322, Usuario.OPERADOR);
		operador1 = admin1.getOperador("operadorPrueba");
		ArrayList<Subasta> subastas = new ArrayList<Subasta>();
		HashMap<String, Integer> ofertas = new HashMap<String, Integer>();
		ofertas.put("userPrueba", 10);
		Subasta subasta = new Subasta(10, cliente1, "piezaExhibida1", pago, ofertas);
		subastas.add(subasta);
		operador1.setSubastas(subastas);
	}

	@Test
	@Order(1)
	void testInventarioToJSON() {
		JSONObject inventarioJSON = Inventario.toJSON(inventario);
		String jsonString = "{\"almacenadas\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"exhibidas\":[\"piezaExhibida1\",\"piezaExhibida2\"]}";
		if (!inventarioJSON.toString().equals(jsonString)) {
			fail("Deben ser iguales");
		}

		String ruta = "dataTest/inventarioTest.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("inventario", inventarioJSON);
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	void testInventarioFromJSON() {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/inventarioTest.json")));
			JSONObject inventarioJSON = new JSONObject(contenido);
			Inventario inventarioTest = Inventario.fromJSON(inventarioJSON);
			if (!inventarioTest.equals(inventario)) {
				fail("Deben de ser iguales");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(3)
	void testClienteToJSON() {
		JSONObject cliente1JSON = cliente1.toJSON();
		String jsonString = "{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}";
		if (!cliente1JSON.toString().equals(jsonString)) {
			fail("Deben ser iguales");
		}

		String ruta = "dataTest/cliente1Test.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonClientes = new JSONArray();
			jsonClientes.put(cliente1JSON);
			jsonObject.put("clientes", jsonClientes);
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(4)
	void testClienteFromJSON() throws UserDuplicatedException, Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/cliente1Test.json")));
			JSONObject clientesJSON = new JSONObject(contenido);
			JSONArray jsonClientes = clientesJSON.getJSONArray("clientes");
			Cliente cliente1Test = null;
			for (Object obj : jsonClientes) {
				JSONObject cliente1Json = (JSONObject) obj;
				cliente1Json.put("login", "loginPruebaCliente"); // Se cambia el nombre para evitar la excepcion
				cliente1Test = Cliente.loadClientFromJSON(cliente1Json, admin1);
			}
			if (!cliente1Test.equalsJSON(cliente1)) {
				fail("Deben de ser iguales");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(5)
	void testCajeroToJSON() {
		JSONObject cliente1JSON = cajero1.toJSON();
		String jsonString = "{\"password\":\"789\",\"tipo\":\"cajero\",\"ocupado\":false,\"login\":\"cajeroPrueba\",\"telefono\":322,\"pagos\":[{\"tipo\":\"efectivo\",\"monto\":5}],\"nombre\":\"Juan\"}";

		if (!cliente1JSON.toString().equals(jsonString)) {
			fail("Deben ser iguales");
		}

		String ruta = "dataTest/cajero1Test.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonClientes = new JSONArray();
			jsonClientes.put(cliente1JSON);
			jsonObject.put("cajeros", jsonClientes);
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(6)
	void testCajeroFromJSON() throws UserDuplicatedException, Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/cajero1Test.json")));
			JSONObject cajerosJSON = new JSONObject(contenido);
			JSONArray jsonCajeros = cajerosJSON.getJSONArray("cajeros");
			Cajero cajero1Test = null;
			for (Object obj : jsonCajeros) {
				JSONObject cajero1Json = (JSONObject) obj;
				cajero1Json.put("login", "loginPruebaCajero"); // Se cambia el nombre para evitar la excepcion
				cajero1Test = Cajero.loadCajeroFromJSON(cajero1Json, admin1);
			}
			if (!cajero1Test.equalsJSON(cajero1)) {
				fail("Deben de ser iguales");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(7)
	void testOperadorToJSON() {
		String jsonString = "{\"password\":\"789\",\"tipo\":\"operador\",\"subastas\":[{\"ofertas\":{\"userPrueba\":10},\"pieza\":\"piezaExhibida1\",\"comprador\":{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"},\"precioVenta\":10,\"pago\":{\"tipo\":\"efectivo\",\"monto\":5},\"tiempoMaximoOferta\":\"0\"}],\"login\":\"operadorPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}";
		JSONObject jsonObject1 = new JSONObject(jsonString);
		JSONObject operador1JSON = operador1.toJSON();

		String ruta = "dataTest/operador1Test.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonOperadores = new JSONArray();
			jsonOperadores.put(operador1JSON);
			jsonObject.put("operadores", jsonOperadores);
			fileWriter.write(jsonObject.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}

		CentralPersistencia.removeDateKeys(jsonObject1);
		CentralPersistencia.removeDateKeys(operador1JSON);
		if (!jsonObject1.similar(operador1JSON)) {
			fail("Deben ser iguales");
		}
	}

	@Test
	@Order(8)
	void testOperadorFromJSON() throws UserDuplicatedException, Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/operador1Test.json")));
			JSONObject operadoresJSON = new JSONObject(contenido);
			JSONArray jsonCajeros = operadoresJSON.getJSONArray("operadores");
			Operador operador1Test = null;
			for (Object obj : jsonCajeros) {
				JSONObject operador1Json = (JSONObject) obj;
				operador1Json.put("login", "loginPruebaOperador"); // Se cambia el nombre para evitar la excepcion
				operador1Test = Operador.loadOperadorFromJSON(operador1Json, admin1);
			}
			if (!operador1Test.equalsJSON(operador1)) {
				fail("Deben de ser iguales");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(9)
	void testAdministradorToJSON() {
		String jsonString = "{\"password\":\"123\",\"tipo\":\"administrador\",\"operadores\":[{\"password\":\"789\",\"tipo\":\"operador\",\"subastas\":[{\"ofertas\":{\"userPrueba\":10},\"pieza\":\"piezaExhibida1\",\"comprador\":{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"},\"precioVenta\":10,\"pago\":{\"tipo\":\"efectivo\",\"monto\":5},\"tiempoMaximoOferta\":\"0\"}],\"login\":\"operadorPrueba\",\"telefono\":322,\"nombre\":\"Juan\"},{\"password\":\"789\",\"tipo\":\"operador\",\"subastas\":[{\"ofertas\":{\"userPrueba\":10},\"pieza\":\"piezaExhibida1\",\"comprador\":{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"},\"precioVenta\":10,\"pago\":{\"tipo\":\"efectivo\",\"monto\":5},\"tiempoMaximoOferta\":\"0\"}],\"login\":\"loginPruebaOperador\",\"telefono\":322,\"nombre\":\"Juan\"}],\"cajeros\":[{\"password\":\"789\",\"tipo\":\"cajero\",\"ocupado\":false,\"login\":\"cajeroPrueba\",\"telefono\":322,\"pagos\":[{\"tipo\":\"efectivo\",\"monto\":5}],\"nombre\":\"Juan\"},{\"password\":\"789\",\"tipo\":\"cajero\",\"ocupado\":false,\"login\":\"loginPruebaCajero\",\"telefono\":322,\"pagos\":[{\"tipo\":\"efectivo\",\"monto\":5}],\"nombre\":\"Juan\"}],\"clientes\":[{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"},{\"valorMaximo\":20000,\"compras\":[\"piezaCompras5\"],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"fechas\":[],\"antiguas\":[\"piezaExhibida1\",\"piezaExhibida2\"],\"login\":\"loginPruebaCliente\",\"telefono\":322,\"nombre\":\"Juan\"}],\"login\":\"a.bolivarc\",\"telefono\":315,\"inventario\":{\"almacenadas\":[\"piezaAlmacenada3\",\"piezaAlmacenada4\"],\"exhibidas\":[\"piezaExhibida1\",\"piezaExhibida2\"]},\"nombre\":\"Andres\"}";
		JSONObject jsonObject1 = new JSONObject(jsonString);
		JSONObject admin1JSON = admin1.toJSON();

		String ruta = "dataTest/admin1Test.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(admin1JSON.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}

		CentralPersistencia.removeDateKeysAdmin(admin1JSON);
		CentralPersistencia.removeDateKeysAdmin(jsonObject1);
		if (!admin1JSON.similar(jsonObject1)) {
			fail("Deben ser iguales");
		}
	}

	@Test
	@Order(10)
	void testAdministradorFromJSON() throws UserDuplicatedException, Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/admin1Test.json")));
			JSONObject adminJSON = new JSONObject(contenido);
			HashMap<String, Usuario> prelog = new HashMap<String, Usuario>(Usuario.logins);
			Usuario.logins.clear();
			Administrador.fromJSON(adminJSON);
			HashMap<String, Usuario> afterlog = new HashMap<String, Usuario>(Usuario.logins);
			if (!prelog.keySet().equals(afterlog.keySet())) {
				fail("Deben de ser iguales");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	static void teardown() {
		admin1.setInventario(null);
		admin1.getCajeros().clear();
		admin1.getClientes().clear();
		admin1.getOperadores().clear();
		admin1 = null;
		Administrador.administradores.clear();
		Pieza.piezas.clear();
		Venta.ventas.clear();
		Usuario.logins.clear();
		Subasta.subastas.clear();
	}
}
