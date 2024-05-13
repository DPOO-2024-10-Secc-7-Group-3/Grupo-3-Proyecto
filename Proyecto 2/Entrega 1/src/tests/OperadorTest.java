package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import exceptions.OfertaInvalidaException;
import exceptions.PiezaNoExistenteException;
import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;

class OperadorTest {

	private static Administrador admin1;
	private static Operador operador1;
	private static Cliente cliente1;

	@BeforeAll
	static void setUp() throws Exception {
		// Crear usuarios para probar
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN);
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
		admin1.setInventario(inventario);
		admin1.crearUsuario("operadorPrueba", "789", "Juan", 322, Usuario.OPERADOR);
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);
		operador1 = admin1.getOperador("operadorPrueba");
		cliente1 = admin1.getCliente("userPrueba");
		cliente1.crearPieza("piezaPrueba", 2000, "Italia", 8, 5, 77, 53, "Brillante", 5, "pintura");
		cliente1.crearPieza("piezaPruebaSubastaVacia", 2000, "Italia", 8, 5, 77, 53, "Brillante", 5, "pintura");
		operador1.iniciarSubasta("piezaPruebaSubastaVacia", admin1);
		cliente1.crearPieza("piezaPruebaSubastaOferta", 2000, "Italia", 8, 5, 77, 53, "Brillante", 5, "pintura");
		operador1.iniciarSubasta("piezaPruebaSubastaOferta", admin1);
		operador1.ofertarPieza(cliente1, 8, "piezaPruebaSubastaOferta", "efectivo");
		admin1.agregarPieza("piezaPrueba", false);
		admin1.agregarPieza("piezaPruebaSubastaVacia", false);
		admin1.agregarPieza("piezaPruebaSubastaOferta", false);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "piezaPrueba", "piezaInexistente" })
	void testIniciarSubasta(String candidate) {
		/*
		 * Este test prueba el metodo iniciarSubasta de la clase Operador. Debe de
		 * fallar si se inicia una subasta sobre una pieza inexistente. Debe de cambiar
		 * la disponibilidad de la pieza y agregar la subasta al ArrayList subastas del
		 * operador.
		 */
		try {
			operador1.iniciarSubasta(candidate, admin1);
			if (!candidate.equals("piezaPrueba")) {
				fail("Debió fallar");
			}
			Subasta subasta = (Subasta) Pieza.piezas.get("piezaPrueba").getDisponibilidad();
			if (!operador1.getSubastas().contains(subasta)) {
				fail("Debió actualizar el ArrayList");
			}
		} catch (PiezaNoExistenteException e) {
			if (candidate.equals("piezaPrueba")) {
				fail("No debió fallar");
			}
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "piezaPrueba", "piezaInexistente" })
	void testOfertarPieza(String candidate) {
		/*
		 * Este test prueba el metodo ofertarPieza de la clase Operador. Debe de fallar
		 * si se oferta por una pieza inexistente. Debe de actualizar el ultimo monto de
		 * la subasta.
		 */
		try {
			int monto = 6;
			operador1.ofertarPieza(cliente1, monto, candidate, "efectivo");
			if (!candidate.equals("piezaPrueba")) {
				fail("Debió fallar");
			}
			Subasta subasta = Subasta.getSubasta(candidate);
			if (subasta.getUltimoMonto() != monto) {
				fail("No actualizo el ultimo monto");
			}
		} catch (OfertaInvalidaException e) {
			if (candidate.equals("piezaPrueba")) {
				fail("No debió fallar");
			}
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "piezaPruebaSubastaOferta", "piezaInexistente", "piezaPruebaSubastaVacia" })
	void testCheckSubastaDuracion(String candidate) {
		/*
		 * Este test prueba el metodo checkSubastaDuracion de la clase Operador. Debe de
		 * fallar si no existe una subasta por una pieza especifica. Debe de eliminar la
		 * subasta del HashMap de subastas.
		 */
		try {
			operador1.checkSubastaDuracion(candidate, admin1);
			if (Subasta.subastas.containsKey(candidate)) {
				fail("No elimino la subasta");
			}
		} catch (Exception e) {
			if(!candidate.equals("piezaInexistente")) {
				fail("No debió fallar");
			}
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
