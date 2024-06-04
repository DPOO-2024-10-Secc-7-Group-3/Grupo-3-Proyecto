package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Subasta;

public class MainAdmin extends Main {
	public MainAdmin(Usuario user) {
		super(user);
	}

	public void ejecutar() throws Exception {
		try {
			boolean working = true;

			while (working) {
				printMenu();
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String input = reader.readLine();

				if (input.equals("1")) {
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = reader1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					String password = reader2.readLine();

					System.out.print("Ingrese el nombre" + ": ");
					BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
					String name = reader3.readLine();

					System.out.print("Ingrese el número de teléfono" + ": ");
					BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
					int telefono = Integer.parseInt(reader4.readLine());

					System.out.print("Ingrese su rol" + ": ");
					BufferedReader reader5 = new BufferedReader(new InputStreamReader(System.in));
					String rol = reader5.readLine();

					((Administrador) user).crearUsuario(usuario, password, name, telefono, rol);
					System.out.println("El usuario " + usuario + " se ha creado con éxito.");
				} else if (input.equals("2")) {
					System.out.print("Ingrese el titulo de la obra a devolver" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nTitulo = reader1.readLine();

					((Administrador) user).devolverPieza(nTitulo);

					System.out.println("Estado de la pieza " + nTitulo + ": " + Pieza.piezas.get(nTitulo).getEstado());

					System.out.println("Inventario almacenadas:");
					for (String titulo : ((Administrador) user).getInventario().getAlmacenadas()) {
						System.out.println(titulo);
					}
					System.out.println("Inventario exhibidas:");
					for (String titulo : ((Administrador) user).getInventario().getExhibidas()) {
						System.out.println(titulo);
					}
				} else if (input.equals("3")) {
					System.out.println("\nEstan activas las subastas de los siguientes objetos:");
					for (String login : Usuario.logins.keySet()) {
						Usuario actual = Usuario.logins.get(login);
						if (actual instanceof Administrador) {
							Inventario inventario = ((Administrador) actual).getInventario();
							for (String titulo : inventario.getAlmacenadas()) {
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Subasta) {
									System.out.println(titulo);
									System.out.println("================================");
								}
							}

							for (String titulo : inventario.getExhibidas()) {
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Subasta) {
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
						}
					}
				} else if (input.equals("4")) {
					System.out.print("Ingrese el nombre de la pieza para cerrar la subasta" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nTitulo = reader1.readLine();

					Subasta buffer = null;
					Operador operador1 = null;

					for (Operador operador : ((Administrador) user).getOperadores()) {
						buffer = operador.getSubasta(nTitulo);

						if (!(buffer == null)) {
							operador1 = operador;
						}
					}

					operador1.checkSubastaDuracion(nTitulo, (Administrador) user);
					System.out.println("Se cerró la subasta de " + nTitulo + " con éxito.");
				} else if (input.equals("5")) {
					System.out.println("\nEstan activas las ventas de los siguientes objetos:");
					for (String login : Usuario.logins.keySet()) {
						Usuario actual = Usuario.logins.get(login);
						if (actual instanceof Administrador) {
							Inventario inventario = ((Administrador) actual).getInventario();
							for (String titulo : inventario.getAlmacenadas()) {
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Fija) {
									System.out.println(titulo);
									System.out.println("================================");
								}
							}

							for (String titulo : inventario.getExhibidas()) {
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Fija) {
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
						}
					}
				} else if (input.equals("6")) {
					System.out.print("Ingrese la pieza que desea consultar" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String titulo = read1.readLine();

					HashMap<String, String> info = Usuario.infoPieza(titulo);

					for (String key : info.keySet()) {
						System.out.print(key + ": " + info.get(key) + "\n");
					}
				} else if (input.equals("7")) {
					System.out.println("Artista: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String art = read1.readLine();

					HashMap<String, ArrayList<String>> info = Usuario.infoArtista(art);

					for (String key : info.keySet()) {
						System.out.println("Pieza: " + key);
						System.out.println("creacion: " + Pieza.piezas.get(key).getAnio() + "\n");
						System.out.println("ventas: \n");
						for (String infoParcial : info.get(key)) {
							System.out.println(infoParcial);
						}
					}
				} else if (input.equals("8")) {
					System.out.println("Cliente: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String cliente = read1.readLine();

					HashMap<String, ArrayList<String>> info = ((Administrador) user).infoCliente(cliente);

					for (String key : info.keySet()) {
						System.out.println(key + ":\n");
						for (String infoParcial : info.get(key)) {
							System.out.println(infoParcial + "\n");
						}
					}

					System.out.println("Valor: ");
					int monto = 0;
					ArrayList<String> actuales = info.get("actuales");
					for (String titulo : actuales) {
						Pieza nPieza = Pieza.piezas.get(titulo);
						monto += nPieza.getPrecio();
					}
					System.out.println("" + monto);
				} else if (input.equals("9")) {
					System.out.println("Usuarios:\n");
					for (String login : Usuario.logins.keySet()) {
						System.out.println(login);
					}
				} else if (input.equals("10")) {
					System.out.println("Nueva contraseña: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String password = read1.readLine();

					user.setPassword(password);
				} else if (input.equals("0")) {
					working = false;
				} else {
					System.out.println("\nElija una opción válida.\n");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			;
			ejecutar();
		}
	}

	public static void printMenu() {
		System.out.println("0) Salir.\n" + "1) Crear usuario.\n" + "2) Devolver pieza.\n" + "3) Ver subastas activas.\n"
				+ "4) Cerrar subasta.\n" + "5) Ver piezas a la venta.\n" + "6) Ver historial de pieza.\n"
				+ "7) Ver historial de artista.\n" + "8) Ver historial de cliente.\n" + "9) Ver usuarios.\n"
				+ "10) Cambiar contraseña.\n");
	}
}
