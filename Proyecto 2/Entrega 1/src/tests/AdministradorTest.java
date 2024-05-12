package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import exceptions.InsuficienteTiempoConsignacionException;
import exceptions.PiezaNoExistenteException;
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

class AdministradorTest {

	private static Administrador admin1;

	@BeforeAll
	static void setUp() throws Exception {
		// Crear usuarios para probar
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>()); //
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>()); //
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("userPruebaSubasta", "789", "Jorge", 322, Usuario.CLIENTE);
		Cliente cliente1 = admin1.getCliente("userPrueba");
		Cliente clienteSubasta = admin1.getCliente("userPruebaSubasta");
		// Crear y agregar piezas para hacer pruebas
		cliente1.crearPieza("piezaPruebaDevolver", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		cliente1.crearPieza("piezaPrueba", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		cliente1.crearPieza("piezaPruebaCara", 2007, "EEUU", 2000000, 1000000, 7, 50000, "video");
		cliente1.crearPieza("piezaPruebaMetodoDiferente", 1932, "EEUU", 700000, 400000, 69, 48, 1080, "fotografía", 5,
				"imagen");
		ArrayList<String> materiales = new ArrayList<String>();
		materiales.add("marmol");
		cliente1.crearPieza("piezaCajeroOcupado", 1501, "Italia", 3000000, 1500000, 517.0, 200, 100, materiales, false,
				2000000, "escultura");
		clienteSubasta.crearPieza("piezaSubasta", 2007, "EEUU", 2000000, 1000000, 7, 50000, "video");
		Subasta subastaPieza = new Subasta(5, cliente1, "piezaSubasta", new Pago("efectivo", 5),
				new HashMap<String, Integer>());
		Pieza.piezas.get("piezaSubasta").setDisponibilidad(subastaPieza);
		Pieza.piezas.get("piezaPrueba")
				.setDisponibilidad(new Fija(5, cliente1, "piezaPrueba", new Pago("efectivo", 5)));
		Pieza.piezas.get("piezaPruebaDevolver").setTiempoConsignacion(LocalDate.of(2023, 5, 10));
		Pieza.piezas.get("piezaPruebaCara")
				.setDisponibilidad(new Fija(50000, cliente1, "piezaPruebaCara", new Pago("efectivo", 50000)));
		admin1.agregarPieza("piezaPruebaCara", false);
		admin1.agregarPieza("piezaPruebaMetodoDiferente", false);
		admin1.agregarPieza("piezaCajeroOcupado", false);
	}

	static Stream<String[]> credentialsCrearUsuarioPorRolProvider() {
		return Stream.of(new String[] { "user1", Usuario.CLIENTE }, new String[] { "user2", Usuario.ADMIN },
				new String[] { "user3", Usuario.CAJERO }, new String[] { "user4", Usuario.OPERADOR },
				new String[] { "user5", "QWERTY" });
	}

	static Stream<String[]> credentialsGetUser() {
		return Stream.of(new String[] { "user1", Usuario.CLIENTE }, new String[] { "user3", Usuario.CAJERO },
				new String[] { "user4", Usuario.OPERADOR }, new String[] { "user5", Usuario.CLIENTE });
	}

	@ParameterizedTest
	@MethodSource("credentialsCrearUsuarioPorRolProvider")
	void testCrearUsuarioPorRol(String login, String rol) {
		/*
		 * Este test prueba la funcion crearUsuario de la clase Administrador variando
		 * los tipos de usuarios. Debe de funcionar normalmente para los 4 tipos de
		 * usuario definidos (Cliente, Admin, Cajero, Operador). Debe de fallar al
		 * intentar crear cualquier otro tipo usuario
		 */
		try {
			admin1.crearUsuario(login, "789", "Juan", 322, rol);
			if (rol.equals("QWERTY")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if (!rol.equals("QWERTY")) {
				fail("No debió fallar");
			}
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "je.sandovals1", "l.cespedes", "je.sandovals1" })
	void testCrearUsuarioNombreRepetido(String login) {
		/*
		 * Este test prueba la funcion crearUsuario de la clase Administrador variando
		 * el login. Debe de fallar al intentar crear un usuario con login repetido.
		 */
		try {
			boolean existe = Usuario.logins.containsKey("je.sandovals1");
			admin1.crearUsuario(login, "789", "Juan", 322, Usuario.CLIENTE);
			if (login.equals("je.sandovals1") && existe) {
				System.out.println("Esto deberia de ser inalcanzable");
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if ((login.equals("l.cespedes"))
					| (login.equals("je.sandovals1") && !Usuario.logins.containsKey("je.sandovals1"))) {
				fail("No debió fallar");
				System.out.println(e.getMessage());
			}
		}
	}

	@ParameterizedTest
	@MethodSource("credentialsGetUser")
	void testGetUser(String login, String rol) {
		/*
		 * Este test prueba las funciones para buscar un usuario especifico de la clase
		 * Administrador. Debe de funcionar normalmente si se busca un usuario
		 * existente. Debe fallar al buscar un usuario inexistente.
		 */
		try {
			if (rol.equals(Usuario.CLIENTE)) {
				admin1.getCliente(login);
			} else if (rol.equals(Usuario.CAJERO)) {
				admin1.getCajero(login);
			} else if (rol.equals(Usuario.OPERADOR)) {
				admin1.getOperador(login);
			}
			if (login.equals("user5")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if (!login.equals("user5")) {
				fail("No debió fallar");
			}
		}
	}

	@Test
	void testAgregarPieza() {
		/*
		 * Este test prueba la funcion agregarPieza de la clase Administrador. Debe de
		 * actualizar el inventario y agregarle la nueva pieza.
		 */
		admin1.agregarPieza("piezaPrueba", false);
		boolean seAgrego = admin1.getInventario().containsPieza("piezaPrueba");
		if (!seAgrego) {
			fail("Debio agregar la pieza");
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "piezaPrueba", "piezaInexistente", "piezaPruebaCara", "piezaPruebaMetodoDiferente",
			"piezaCajeroOcupado" })
	void testNuevaCompra(String candidate) throws Exception {
		/*
		 * Este test prueba la funcion nuevaCompra de la clase Administrador. Debe de
		 * fallar cuando la pieza no existe, es demasiado cara para el cliente, no esta
		 * disponible para venta fija o no hay cajeros disponibles. De lo contrario, no
		 * debe fallar
		 */
		try {
			Cajero cajero = admin1.getCajero("user3");
			if (candidate.equals("piezaCajeroOcupado")) {
				cajero.setOcupado(true);
			}
			Cliente cliente = admin1.getCliente("user1");
			admin1.nuevaCompra(candidate, cliente, "efectivo");
			if (!candidate.equals("piezaPrueba")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			Cajero cajero = admin1.getCajero("user3");
			if (candidate.equals("piezaCajeroOcupado")) {
				cajero.setOcupado(false);
			}
			if (candidate.equals("piezaPrueba")) {
				fail("No debió fallar");
			}
		}
	}

	@Test
	void testCalcularValor() throws Exception {
		/*
		 * Este test prueba la funcion calcularValor de la clase Administrador. Debe de
		 * devolver el dinero que el cliente ha gastado.
		 */
		admin1.crearUsuario("userPruebaCalcularValor", "789", "Juan", 322, Usuario.CLIENTE);
		Cliente cliente2 = admin1.getCliente("userPruebaCalcularValor");
		admin1.nuevaCompra("piezaPrueba", cliente2, "efectivo");
		int valor = admin1.calcularValor("userPruebaCalcularValor");
		if (valor != 5) {
			fail("Debe de sumar 5");
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "piezaPruebaDevolver", "piezaInexistente", "piezaPruebaCara" })
	void testDevolverPieza(String candidate) throws InsuficienteTiempoConsignacionException, PiezaNoExistenteException {
		/*
		 * Este test prueba la funcion devolverPieza de la clase Administrador. Debe de
		 * fallar si la pieza aun no cumple su tiempo de consigancion o si la pieza no
		 * existe. De lo contrario, no debe fallar.
		 */
		try {
			admin1.devolverPieza(candidate);
			if (!candidate.equals("piezaPruebaDevolver")) {
				fail("Debió fallar");
			}
		} catch (PiezaNoExistenteException e) {
			if (!candidate.equals("piezaInexistente")) {
				fail("No debió fallar, o al menos no por esto");
			}
		} catch (InsuficienteTiempoConsignacionException e) {
			if (!candidate.equals("piezaPruebaCara")) {
				fail("No debió fallar, o al menos no por esto");
			}
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "piezaSubasta", "piezaInexistente" })
	void testCerrarSubasta(String candidate) throws Exception {
		/*
		 * Este test prueba la funcion cerrarSubasta de la clase Administrador. Debe
		 * fallar si la pieza no existe. De lo contrario, no debe fallar.
		 */
		try {
			Subasta subasta = (Subasta) Venta.getVenta(candidate);
			admin1.cerrarSubasta(candidate, "userPruebaSubasta", subasta);
			if (candidate.equals("piezaInexistente")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if (!candidate.equals("piezaInexistente")) {
				fail("No debió fallar");
			}
		}
	}

	@Test
	void testInfoCliente() {
		/*
		 * Este test prueba la funcion infoCliente de la clase Administrador. La salida
		 * de la funcion debe de ser igual al diccionario prueba.
		 */
		ArrayList<String> compras = new ArrayList<String>();
		ArrayList<String> actuales = new ArrayList<>(List.of("piezaPruebaDevolver", "piezaPrueba", "piezaPruebaCara",
				"piezaPruebaMetodoDiferente", "piezaCajeroOcupado"));
		HashMap<String, ArrayList<String>> prueba = new HashMap<String, ArrayList<String>>();
		prueba.put("compras", compras);
		prueba.put("actuales", actuales);

		assertEquals("Ambos diccionarios deben de ser identicos", prueba, admin1.infoCliente("userPrueba"));

	}

	@AfterAll
	static void teardown() {
		admin1.setInventario(null);
		admin1.setCajeros(null);
		admin1.setClientes(null);
		admin1.setOperadores(null);
		admin1 = null;
		Administrador.administradores.clear();
		Pieza.piezas.clear();
		Venta.ventas = new ArrayList<Venta>();
		Usuario.logins.clear();
	}

}
