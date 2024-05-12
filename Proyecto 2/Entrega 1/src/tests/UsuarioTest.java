package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Pago;

class UsuarioTest {

	private static Administrador admin1;

	@BeforeAll
	static void setUp() throws Exception {
		// Crear usuarios para probar
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("userPruebaFallarContraseña", "123", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);
		// Crear piezas para probar
		Cliente cliente = admin1.getCliente("userPruebaFallarContraseña");
		Cliente cliente1 = admin1.getCliente("userPrueba");
		cliente1.crearPieza("piezaPrueba", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		Pieza.piezas.get("piezaPrueba").setDisponibilidad(new Fija(5, cliente, "piezaPrueba", new Pago("efectivo", 5)));
		admin1.agregarPieza("piezaPrueba", false);
		admin1.nuevaCompra("piezaPrueba", cliente, "efectivo");
	}

	@ParameterizedTest
	@ValueSource(strings = { "userPrueba", "userInexistente", "userPruebaFallarContraseña" })
	void testIniciarSeion(String candidate) throws UserNotFoundException, IncorrectPasswordException {
		/*
		 * Este test prueba la funcion iniciarSesion de la clase Usuario. Debe de fallar
		 * si la contraseña no es correcta o el usuario no existe. De lo contrario, no
		 * debe de fallar.
		 */
		try {
			Usuario.iniciarSesion(candidate, "789");
			if (!candidate.equals("userPrueba")) {
				fail("Debió fallar");
			}
		} catch (UserNotFoundException e) {
			if (!candidate.equals("userInexistente")) {
				fail("No debió fallar, o no por eso");
			}
		} catch (IncorrectPasswordException e) {
			if (!candidate.equals("userPruebaFallarContraseña")) {
				fail("No debió fallar, o no por eso");
			}
		}
	}

	@Test
	void testInfoPieza() {
		/*
		 * Este test prueba la funcion infoPieza de la clase Usuario. La salida de la
		 * funcion debe de ser igual al diccionario prueba.
		 */
		HashMap<String, String> prueba = new HashMap<>();
		prueba.put("historicos", " Juan");
		prueba.put("lugar de creacion", "Italia");
		prueba.put("actuales", "Juan ");
		prueba.put("titulo", "piezaPrueba");
		prueba.put("ventas", "2024-5-12:5\n");
		prueba.put("anio", "2000");
		assertEquals("Ambos diccionarios deben de ser identicos", prueba, admin1.infoPieza("piezaPrueba"));
	}

	@Test
	void testInfoArtista() {
		/*
		 * Este test prueba la funcion infoArtista de la clase Usuario. La funcion no
		 * debe de fallar.
		 */
		admin1.infoArtista("userPrueba");
	}

}
