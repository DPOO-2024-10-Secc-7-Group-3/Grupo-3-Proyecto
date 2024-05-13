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

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;
import persistencia.CentralPersistencia;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersistenciaVentasTest {

	private static Administrador admin1;
	private static CentralPersistencia cp;
	private static ArrayList<Venta> ventasInit = new ArrayList<Venta>();
	
	@BeforeAll
	static void setUp() throws Exception {
		
		cp = new CentralPersistencia();
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());
		admin1.crearUsuario("userPruebaA", "789", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("userPruebaB", "123", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);

		Cliente cliente1 = admin1.getCliente("userPruebaA");
		cliente1.crearPieza("piezaPrueba", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		Pieza.piezas.get("piezaPrueba").setDisponibilidad(new Fija(5, cliente1, "piezaPrueba", new Pago("efectivo", 5)));
		admin1.agregarPieza("piezaPrueba", false);
		ventasInit = new ArrayList<Venta>(Venta.ventas);
	}

	@Test
	@Order(1)
	void testVentasToJSON() {
		String jsonString = "{\"ventas\":[{\"pieza\":\"piezaPrueba\",\"comprador\":{\"valorMaximo\":20000,\"compras\":[],\"password\":\"789\",\"tipo\":\"cliente\",\"actuales\":[\"piezaPrueba\"],\"fechas\":[],\"antiguas\":[],\"login\":\"userPruebaA\",\"telefono\":322,\"nombre\":\"Juan\"},\"precioVenta\":5,\"pago\":{\"tipo\":\"efectivo\",\"monto\":5}}]}";
		JSONObject ventasJSON = new JSONObject();
		cp.guardarVentas(ventasJSON);
		if (!ventasJSON.toString().equals(jsonString)) {
			fail("Deben ser iguales");
		}
		
		String ruta = "dataTest/ventasTest.json";
		try (FileWriter fileWriter = new FileWriter(ruta)) {
			fileWriter.write(ventasJSON.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	void testVentasFromJSON() throws Exception {
		try {
			String contenido = new String(Files.readAllBytes(Paths.get("dataTest/ventasTest.json")));
			JSONObject ventasJSON = new JSONObject(contenido);
			cp.cargarVentas(ventasJSON);
			
			if (!Venta.equalsArray(ventasInit, Venta.ventas)) {
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
