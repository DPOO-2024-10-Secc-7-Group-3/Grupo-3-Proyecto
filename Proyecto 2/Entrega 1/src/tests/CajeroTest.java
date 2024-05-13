package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Usuario;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;

class CajeroTest {

	private static Administrador admin1;
	private static Cajero cajero1;

	@BeforeAll
	static void setUp() throws Exception {
		// Crear usuarios para probar
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);
		cajero1 = admin1.getCajero("cajeroPrueba");
	}

	@Test
	void testNuevoPago() {
		/*
		 * Este test prueba el metodo nuevoPago de la clase Cajero. Debe de actualizar
		 * el ArrayList de pagos del cajero
		 */
		Pago pago = cajero1.nuevoPago("efectivo", 5);
		if (!cajero1.getPagos().contains(pago)) {
			fail("Debió agregar el pago al ArrayList");
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
