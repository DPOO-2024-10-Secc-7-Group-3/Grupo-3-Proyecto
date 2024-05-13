package tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

class ClienteTest {

	private static Administrador admin1;
	private static Cliente cliente1;

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
		cliente1 = admin1.getCliente("userPrueba");
		cliente1.crearPieza("piezaPrueba", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		cliente1.crearPieza("piezaPruebaEntregar", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		cliente.crearPieza("piezaPruebaComprar", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 5, "pintura");
		Pieza.piezas.get("piezaPrueba").setDisponibilidad(new Fija(5, cliente, "piezaPrueba", new Pago("efectivo", 5)));
		Pieza.piezas.get("piezaPruebaComprar")
				.setDisponibilidad(new Fija(5, cliente, "piezaPrueba", new Pago("efectivo", 5)));
		admin1.agregarPieza("piezaPrueba", false);
		admin1.agregarPieza("piezaPruebaComprar", false);
		admin1.nuevaCompra("piezaPrueba", cliente, "efectivo");
	}

	@ParameterizedTest
	@ValueSource(strings = { "piezaPrueba", "piezaInexistente" })
	void testBuscarPieza(String candidate) {
		/*
		 * Este test prueba el metodo buscarPieza de la clase Cliente. Dabe de fallar al
		 * intentar buscar una pieza inexistente. De lo contrario, no debe fallar.
		 */
		try {
			cliente1.buscarPieza(candidate);
			if (!candidate.equals("piezaPrueba")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if (candidate.equals("piezaPrueba")) {
				fail("No debió fallar");
			}
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "piezaPrueba", "piezaInexistente", "piezaPruebaEntregar" })
	void testEntregarPieza(String candidate) {
		/*
		 * Este test prueba el metodo entregarPieza de la clase Cliente. Debe de
		 * funcionar si la pieza existe y la tiene el cliente. De lo contrario, debe
		 * fallar.
		 */
		try {
			cliente1.entregarPieza(candidate, false, false, null);
			if (!candidate.equals("piezaPruebaEntregar")) {
				fail("Debió fallar");
			}
		} catch (Exception e) {
			if (candidate.equals("piezaPruebaEntregar")) {
				fail("No debió fallar");
			}
		}
	}

	@Test
	void testComprar() throws Exception {
		/*
		 * Este test prueba el metodo comprar de la clase Cliente. Debe de actualizar el
		 * ArrayList de compras del cliente.
		 */
		cliente1.comprar("piezaPruebaComprar", "efectivo");
		if (!cliente1.getCompras().contains("piezaPruebaComprar")) {
			fail("Debió de agregar la nueva compra");
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
