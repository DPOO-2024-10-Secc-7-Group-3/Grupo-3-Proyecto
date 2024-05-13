package tests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import exceptions.UserDuplicatedException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;
import persistencia.CentralPersistencia;

class PersistenciaPiezasTest {
	
	private static Administrador admin1;
	private static Cliente cliente1;
	private static CentralPersistencia cp;
	
	@BeforeAll
	static void setUp() throws Exception {
		
		cp = new CentralPersistencia();
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);
		cliente1 = admin1.getCliente("userPrueba");
		
		ArrayList<String> materiales = new ArrayList<String>();
		materiales.add("marmol");
		cliente1.crearPieza("pieza1", 1501, "Italia", 3000000, 1500000, 517.0, 200, 100, materiales, false, 2000000,
				"escultura");
		cliente1.crearPieza("pieza2", 1932, "EEUU", 700000, 400000, 69, 48, 1080, "fotografía", 500000,
				"imagen");
		cliente1.crearPieza("pieza3", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		cliente1.crearPieza("pieza4", 2007, "EEUU", 2000000, 1000000, 7, 1000000, "video");
	}
	
	@Test
	void testPiezaToJSON() {
		String jsonString = "{\"piezas\":[{\"tiempoConsignacion\":\"2024-05-13\",\"propietarios\":[{\"valorMaximo\":20000,\"compras\":[],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"pieza1\",\"pieza2\",\"pieza3\",\"pieza4\"],\"antiguas\":[],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}],\"estado\":\"fuera\",\"lugarCreacion\":\"Italia\",\"pieza\":\"pintura\",\"titulo\":\"pieza3\",\"textura\":\"Brillante\",\"alto\":53,\"precio\":5,\"ancho\":77,\"valorInicial\":700000,\"bloqueada\":false,\"valorMinimo\":1000000,\"anio\":2000},{\"tiempoConsignacion\":\"2024-05-13\",\"propietarios\":[{\"valorMaximo\":20000,\"compras\":[],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"pieza1\",\"pieza2\",\"pieza3\",\"pieza4\"],\"antiguas\":[],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}],\"estado\":\"fuera\",\"precio\":1000000,\"lugarCreacion\":\"EEUU\",\"valorInicial\":1000000,\"pieza\":\"video\",\"duracion\":7,\"titulo\":\"pieza4\",\"bloqueada\":false,\"valorMinimo\":2000000,\"anio\":2007},{\"tiempoConsignacion\":\"2024-05-13\",\"propietarios\":[{\"valorMaximo\":20000,\"compras\":[],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"pieza1\",\"pieza2\",\"pieza3\",\"pieza4\"],\"antiguas\":[],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}],\"estado\":\"fuera\",\"lugarCreacion\":\"Italia\",\"pieza\":\"escultura\",\"titulo\":\"pieza1\",\"alto\":200,\"precio\":2000000,\"ancho\":517,\"profundidad\":100,\"valorInicial\":1500000,\"electricidad\":false,\"bloqueada\":false,\"materiales\":[\"marmol\"],\"valorMinimo\":3000000,\"anio\":1501},{\"tiempoConsignacion\":\"2024-05-13\",\"propietarios\":[{\"valorMaximo\":20000,\"compras\":[],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"pieza1\",\"pieza2\",\"pieza3\",\"pieza4\"],\"antiguas\":[],\"login\":\"userPrueba\",\"telefono\":322,\"nombre\":\"Juan\"}],\"tipo\":\"fotografía\",\"estado\":\"fuera\",\"lugarCreacion\":\"EEUU\",\"resolucion\":1080,\"pieza\":\"imagen\",\"titulo\":\"pieza2\",\"alto\":48,\"precio\":500000,\"ancho\":69,\"valorInicial\":400000,\"bloqueada\":false,\"valorMinimo\":700000,\"anio\":1932}]}";
		JSONObject piezasJSON = new JSONObject();
		cp.guardarPiezas(piezasJSON);
		if (!piezasJSON.toString().equals(jsonString)) {
			fail("Deben ser iguales");
		}
		
		String ruta = "dataTest/piezasTest.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(piezasJSON.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testPiezaFromJSON() throws Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/piezasTest.json")));
			JSONObject piezasJSON = new JSONObject(contenido);
			Set<String> piezasInit = new HashSet<>(Pieza.piezas.keySet());
			Pieza.piezas.clear();
			cp.cargarPiezas(piezasJSON);
			if (!Pieza.piezas.keySet().equals(piezasInit)) {
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
