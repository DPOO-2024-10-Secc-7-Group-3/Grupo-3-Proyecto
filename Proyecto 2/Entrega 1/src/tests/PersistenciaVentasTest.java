package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;

class PersistenciaVentasTest {

	private static Administrador admin1;

	@BeforeAll
	static void setUp() throws Exception {
		Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
		admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
				new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());
		admin1.crearUsuario("userPrueba", "789", "Juan", 322, Usuario.CLIENTE);

		admin1.crearUsuario("userPruebaFallarContraseña", "123", "Juan", 322, Usuario.CLIENTE);
		admin1.crearUsuario("cajeroPrueba", "789", "Juan", 322, Usuario.CAJERO);
	}

	@Test
	void test() {
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
