package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cajero;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import modelo.usuarios.Operador;
import modelo.ventas.Pago;
import modelo.ventas.Subasta;
import modelo.ventas.Venta;
import persistencia.CentralPersistencia;

public class Main {
	// @SuppressWarnings("static-access")
	public static void main(String[] args) {
		try {

			Inventario inventario = new Inventario(new ArrayList<String>(), new ArrayList<String>());
			Administrador admin1 = new Administrador("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN, inventario,
					new ArrayList<Cliente>(), new ArrayList<Cajero>(), new ArrayList<Operador>());

			// admin1.crearUsuario("a.bolivarc", "123", "Andres", 315, Usuario.ADMIN);

			Cajero cajero1 = new Cajero("s.lievanom", "456", "Santiago", 312, Usuario.CAJERO, new ArrayList<Pago>(),
					false);
			cajero1.nuevoPago("efectivo", 15000);
			// admin1.crearUsuario("s.lievanom", "456", "Santiago", 312, Usuario.CAJERO);

			admin1.agregarCajero(cajero1);

			admin1.crearUsuario("je.aguirreo1", "789", "Juan", 322, Usuario.CLIENTE);
			admin1.crearUsuario("f.ortizp", "123", "Fabio", 314, Usuario.CLIENTE);
			admin1.crearUsuario("jm.perezb1", "456", "Manuel", 310, Usuario.CLIENTE);
			admin1.crearUsuario("lm.rojasa12", "789", "Mariana", 317, Usuario.CLIENTE);
			admin1.crearUsuario("jg.bernalc1", "123", "Gabriel", 300, Usuario.CLIENTE);
			admin1.crearUsuario("je.sandovals1", "123", "Jesus", 300, Usuario.OPERADOR);

			Cliente cliente1 = (Cliente) Usuario.iniciarSesion("je.aguirreo1", "789");
			Cliente cliente2 = (Cliente) Usuario.iniciarSesion("f.ortizp", "123");
			Cliente cliente3 = (Cliente) Usuario.iniciarSesion("jm.perezb1", "456");
			Cliente cliente4 = (Cliente) Usuario.iniciarSesion("lm.rojasa12", "789");
			Cliente cliente5 = (Cliente) Usuario.iniciarSesion("jg.bernalc1", "123");
			Operador operador1 = (Operador) Usuario.iniciarSesion("je.sandovals1", "123");

			cliente1.crearPieza("La mona lisa", 2000, "Italia", 1000000, 700000, 77, 53, "Brillante", 500000,
					"pintura");
			cliente2.crearPieza("Infinity", 2007, "EEUU", 2000000, 1000000, 7, 1000000, "video");
			cliente3.crearPieza("Los obreros en la viga", 1932, "EEUU", 700000, 400000, 69, 48, 1080, "fotografía",
					500000, "imagen");
			ArrayList<String> materiales = new ArrayList<String>();
			materiales.add("marmol");
			cliente4.crearPieza("El David", 1501, "Italia", 3000000, 1500000, 517.0, 200, 100, materiales, false,
					2000000, "escultura");
			cliente5.crearPieza("Tremor", 2010, "Alemania", 1500000, 1000000, 4, 1250000, "video");

			System.out.println("exhibidas");
			for (String titulo : admin1.getInventario().getExhibidas()) {
				System.out.println(titulo);
			}
			System.out.println("almacenadas");
			for (String titulo : admin1.getInventario().getAlmacenadas()) {
				System.out.println(titulo);
			}

			System.out.println(
					"Casos.\n0) Salir\n1) Iniciar sesión\n2) Crear usuario\n3) Crear pieza\n4) Entregar pieza\n5) Devolver pieza\n6) Vender pieza\n7) Guardar datos\n8) Cargar datos\n9) Iniciar subasta\n10) Ofertar en subasta\n11) Ver subastas activas\n12) Mostrar historia pieza\n13) Mostras historia artista\n14) Mostrar historia cliente\n");
			System.out.print("Elija cuál caso desea probar" + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input = reader.readLine();

			while (!input.equals("0")) {
				if (input.equals("1")) {
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = reader1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					String password = reader2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");
				} else if (input.equals("2")) {
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

					admin1.crearUsuario(usuario, password, name, telefono, rol);
					for (String login : Usuario.logins.keySet()) {
						System.out.println(login);
					}
				} else if (input.equals("3")) {
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = read1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader read2 = new BufferedReader(new InputStreamReader(System.in));
					String password = read2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");

					System.out.print("Ingrese el tipo de pieza" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String tipo = reader1.readLine();

					if (!tipo.equalsIgnoreCase("escultura") && !tipo.equalsIgnoreCase("imagen")
							&& !tipo.equalsIgnoreCase("pintura") && !tipo.equalsIgnoreCase("video")) {
						throw new Exception("El tipo de pieza no se contempla en la galería.");
					} else {
						System.out.print("Ingrese el título" + ": ");
						BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
						String titulo = reader2.readLine();

						System.out.print("Ingrese el año" + ": ");
						BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
						int anio = Integer.parseInt(reader3.readLine());

						System.out.print("Ingrese el lugar de creación" + ": ");
						BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
						String lugar = reader4.readLine();

						System.out.print("Ingrese el valor minimo" + ": ");
						BufferedReader reader5 = new BufferedReader(new InputStreamReader(System.in));
						int valorMinimo = Integer.parseInt(reader5.readLine());

						System.out.print("Ingrese el valor inicial" + ": ");
						BufferedReader reader6 = new BufferedReader(new InputStreamReader(System.in));
						int valorInicial = Integer.parseInt(reader6.readLine());

						System.out.print("Ingrese el precio de venta" + ": ");
						BufferedReader reader7 = new BufferedReader(new InputStreamReader(System.in));
						int precio = Integer.parseInt(reader7.readLine());

						if (tipo.equalsIgnoreCase("escultura")) {
							System.out.print("Ingrese el alto de la escultura" + ": ");
							BufferedReader reader8 = new BufferedReader(new InputStreamReader(System.in));
							double alto = Double.parseDouble(reader8.readLine());

							System.out.print("Ingrese el ancho de la escultura" + ": ");
							BufferedReader reader9 = new BufferedReader(new InputStreamReader(System.in));
							double ancho = Double.parseDouble(reader9.readLine());

							System.out.print("Ingrese la profundidad de la escultura" + ": ");
							BufferedReader reader10 = new BufferedReader(new InputStreamReader(System.in));
							double profundidad = Double.parseDouble(reader10.readLine());

							System.out.print("Ingrese el material principal de la escultura" + ": ");
							BufferedReader reader11 = new BufferedReader(new InputStreamReader(System.in));
							String material = (reader11.readLine());
							ArrayList<String> nMateriales = new ArrayList<String>();
							nMateriales.add(material);

							System.out.print("La escultura necesita electricidad? (S/N)" + ": ");
							BufferedReader reader12 = new BufferedReader(new InputStreamReader(System.in));
							String elect = (reader12.readLine());
							boolean nElectricidad;
							if (elect.equalsIgnoreCase("s")) {
								nElectricidad = true;
							} else {
								nElectricidad = false;
							}

							((Cliente) user1).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
									profundidad, nMateriales, nElectricidad, precio, "escultura");
						} else if (tipo.equalsIgnoreCase("pintura")) {
							System.out.print("Ingrese el alto de la pintura" + ": ");
							BufferedReader reader8 = new BufferedReader(new InputStreamReader(System.in));
							double alto = Double.parseDouble(reader8.readLine());

							System.out.print("Ingrese el ancho de la pintura" + ": ");
							BufferedReader reader9 = new BufferedReader(new InputStreamReader(System.in));
							double ancho = Double.parseDouble(reader9.readLine());

							System.out.print("Ingrese la textura de la pintura" + ": ");
							BufferedReader reader12 = new BufferedReader(new InputStreamReader(System.in));
							String textura = (reader12.readLine());

							((Cliente) user1).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
									textura, precio, "pintura");
						} else if (tipo.equalsIgnoreCase("video")) {
							System.out.print("Ingrese la duración del video" + ": ");
							BufferedReader reader8 = new BufferedReader(new InputStreamReader(System.in));
							int duracion = Integer.parseInt(reader8.readLine());
							((Cliente) user1).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, duracion,
									precio, "video");
						} else {
							System.out.print("Ingrese el alto de la imagen" + ": ");
							BufferedReader reader8 = new BufferedReader(new InputStreamReader(System.in));
							double alto = Double.parseDouble(reader8.readLine());

							System.out.print("Ingrese el ancho de la imagen" + ": ");
							BufferedReader reader9 = new BufferedReader(new InputStreamReader(System.in));
							double ancho = Double.parseDouble(reader9.readLine());

							System.out.print("Ingrese la resolución de la imagen" + ": ");
							BufferedReader reader10 = new BufferedReader(new InputStreamReader(System.in));
							int resolucion = Integer.parseInt(reader10.readLine());

							System.out.print("Ingrese el tipo de imagen" + ": ");
							BufferedReader reader11 = new BufferedReader(new InputStreamReader(System.in));
							String nTipo = (reader11.readLine());

							((Cliente) user1).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
									resolucion, nTipo, precio, "imagen");
						}

						for (String nTitulo : ((Cliente) user1).getActuales()) {
							System.out.println(nTitulo);
						}
					}
				} else if (input.equals("4")) {

					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = read1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader read2 = new BufferedReader(new InputStreamReader(System.in));
					String password = read2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");

					System.out.print("Ingrese el titulo" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String titulo = reader1.readLine();

					System.out.print("Elija si desea que su pieza se exhiba (S/N)" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					String exhibir = reader2.readLine();
					boolean nExhibir;
					if (exhibir.equalsIgnoreCase("s")) {
						nExhibir = true;
					} else {
						nExhibir = false;
					}

					System.out.print("Elija si su pieza se va subastar(S/N)" + ": ");
					BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
					String subasta = reader3.readLine();
					boolean nSubasta;
					if (subasta.equalsIgnoreCase("s")) {
						nSubasta = true;
					} else {
						nSubasta = false;
					}

					System.out.print("Indique la fecha de consginación (AAAA-MM-DD)" + ": ");
					BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
					String fecha = reader4.readLine();

					((Cliente) user1).entregarPieza(titulo, nExhibir, nSubasta, LocalDate.parse(fecha));

					System.out.println("Inventario almacenadas:");
					for (String nTitulo : inventario.getAlmacenadas()) {
						System.out.println(nTitulo);
					}
					System.out.println("Inventario exhibidas:");
					for (String nTitulo : inventario.getExhibidas()) {
						System.out.println(nTitulo);
					}
				} else if (input.equals("5")) {
					System.out.println(
							"Para devolver una pieza, primero un cliente tuvo que haber entregado al menos una pieza.");
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = read1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader read2 = new BufferedReader(new InputStreamReader(System.in));
					String password = read2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");

					System.out.print("Ingrese el titulo de la obra a devolver" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nTitulo = reader1.readLine();

					if (!user1.getClass().toString().equals("class modelo.usuarios.Administrador")) {
						throw new Exception("Únicamente los administradores pueden devolver piezas");
					}

					admin1.devolverPieza(nTitulo);

					System.out.println("Estado de la pieza " + nTitulo + ": " + Pieza.piezas.get(nTitulo).getEstado());

					System.out.println("Inventario almacenadas:");
					for (String titulo : inventario.getAlmacenadas()) {
						System.out.println(titulo);
					}
					System.out.println("Inventario exhibidas:");
					for (String titulo : inventario.getExhibidas()) {
						System.out.println(titulo);
					}

				} else if (input.equals("6")) {
					System.out.println(
							"Para comprar una pieza, primero un cliente tuvo que haber entregado al menos una pieza.");
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = read1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader read2 = new BufferedReader(new InputStreamReader(System.in));
					String password = read2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");

					System.out.print("Ingrese el título de la pieza que desea comprar" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String titulo = reader1.readLine();

					System.out.print("Ingrese el método de pago" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					String metodo = reader2.readLine();

					if (!metodo.equalsIgnoreCase("tarjeta de crédito") && !metodo.equalsIgnoreCase("transferencia")
							&& !metodo.equalsIgnoreCase("efectivo")) {
						throw new Exception("El método de pago " + metodo + " no está permitido.");
					}

					((Cliente) user1).comprar(titulo, metodo);

					System.out.println("Compras de " + (((Cliente) user1).getNombre()));
					for (String nTitulo : ((Cliente) user1).getCompras()) {
						System.out.println(nTitulo);
					}

				} else if (input.equals("7")) {
					System.out.print("Ingrese el nombre del archivo (escribalo sin .json)" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nArchivo = reader1.readLine();

					CentralPersistencia cp = new CentralPersistencia();

					cp.guardarDatos(nArchivo);

					System.out.println("El archivo " + nArchivo + " ha sido guardado con exito");
					System.out.println("\nSe guardaron los siguientes usuarios:");
					for (String login : Usuario.logins.keySet()) {
						System.out.println(login);
					}
					System.out.println("\nSe guardaron las siguientes piezas:");
					for (Pieza pieza : Pieza.piezas.values()) {
						System.out.println(pieza.getTitulo());
					}
					System.out.println("\nSe cargaron los movimientos de las siguientes piezas:");
					for (Venta venta : Venta.ventas) {
						if (venta.getComprador() == null) {
							System.out.println("Se entrego: " + venta.getPieza());
						} else {
							System.out.println(venta.getComprador().getNombre() + " compro: " + venta.getPieza());
						}
					}
					System.out.println("Fin :)\n");
				} else if (input.equals("8")) {
					System.out.print("Ingrese el nombre del archivo" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nArchivo = reader1.readLine();

					CentralPersistencia cp = new CentralPersistencia();

					cp.cargarDatos(nArchivo);

					System.out.println("El archivo " + nArchivo + " ha sido cargado con exito");
					System.out.println("\nSe cargaron los siguientes usuarios:");
					for (String login : Usuario.logins.keySet()) {
						System.out.println(login);
					}
					System.out.println("\nSe cargaron las siguientes piezas:");
					for (Pieza pieza : Pieza.piezas.values()) {
						System.out.println(pieza.getTitulo());
					}
					System.out.println("\nSe cargaron los movimientos de las siguientes piezas:");
					for (Venta venta : Venta.ventas) {
						if (venta.getComprador() == null) {
							System.out.println("Se entrego: " + venta.getPieza());
						} else {
							System.out.println(venta.getComprador().getNombre() + " compro: " + venta.getPieza());
						}
					}
					System.out.println("Fin :)\n");
				} else if (input.equals("9")) {
					System.out.print("Ingrese el nombre de la pieza a subastar" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nTitulo = reader1.readLine();

					operador1.iniciarSubasta(nTitulo, admin1);

					System.out.println("La subasta por " + nTitulo + " ha iniciado con exito");
					System.out.println("\nEstan activas las subastas por los siguientes objetos:");
					for (Operador operador : admin1.getOperadores()) {
						for (Subasta subasta : operador.getSubastas()) {
							System.out.println(subasta.getPieza());
						}
					}
				} else if (input.equals("10")) {
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = read1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader read2 = new BufferedReader(new InputStreamReader(System.in));
					String password = read2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");

					System.out.print("Ingrese el nombre de la pieza a ofertar" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String nTitulo = reader1.readLine();

					System.out.print("Ingrese el monto a ofertar" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					Integer oferta = Integer.parseInt(reader2.readLine());

					System.out.print("Ingrese el método de pago" + ": ");
					BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
					String metodo = reader3.readLine();

					if (!metodo.equalsIgnoreCase("tarjeta de crédito") && !metodo.equalsIgnoreCase("transferencia")
							&& !metodo.equalsIgnoreCase("efectivo")) {
						throw new Exception("El método de pago " + metodo + " no está permitido.");
					}

					operador1.ofertarPieza((Cliente) user1, oferta, nTitulo, metodo);

					System.out.println("Se oferto con exito");
					System.out.println("\nEstan activas las subastas por los siguientes objetos:");
					for (Operador operador : admin1.getOperadores()) {
						for (Subasta subasta : operador.getSubastas()) {
							System.out.println(subasta.getPieza());
						}
					}
				} else if (input.equals("11")) {

					System.out.println("\nEstan activas las subastas por los siguientes objetos:");
					for (Operador operador : admin1.getOperadores()) {
						for (Subasta subasta : operador.getSubastas()) {
							System.out.println(subasta.toString());
							System.out.println("================================");
						}
					}
				}
				else if (input.equals("12"))
				{
					System.out.print("Ingrese la pieza que desea consultar" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String titulo = read1.readLine();
					
					HashMap<String,String> info = cliente1.infoPieza(titulo);
					
					for (String key:info.keySet())
					{
						System.out.print(key+": "+info.get(key)+"\n");
					}
				}
				else if (input.equals("13"))
				{
					System.out.println("Artista: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String art = read1.readLine();
					
					HashMap<String,ArrayList<String>> info = cliente1.infoArtista(art);
					
					for (String key:info.keySet())
					{
						System.out.println("creacion: "+Pieza.piezas.get(key).getAnio()+"\n");
						System.out.println("ventas: \n");
						for (String infoParcial:info.get(key))
						{
							System.out.println(infoParcial);
						}
					}
				}
				else if (input.equals("14"))
				{
					System.out.println("Cliente: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String cliente = read1.readLine();
					
					HashMap<String,ArrayList<String>> info = admin1.infoCliente(cliente);
					
					for (String key:info.keySet())
					{
						System.out.println(key+":\n");
						for (String infoParcial:info.get(key))
						{
							System.out.println(infoParcial+"\n");
						}
					}
					
					System.out.println("Valor: ");
					int monto = 0;
					ArrayList<String> actuales = info.get("actuales");
					for (String titulo:actuales)
					{
						Pieza nPieza = Pieza.piezas.get(titulo);
						monto += nPieza.getPrecio();
					}
					System.out.println(""+monto);
				}
				System.out.println(
						"Casos.\n0) Salir\n1) Iniciar sesión\n2) Crear usuario\n3) Crear pieza\n4) Entregar pieza\n5) Devolver pieza\n6) Vender pieza\n7) Guardar datos\n8) Cargar datos\n9) Iniciar subasta\n10) Ofertar en subasta\n11) Ver subastas activas\n");
				System.out.print("Elija cuál caso desea probar" + ": ");
				reader = new BufferedReader(new InputStreamReader(System.in));
				input = reader.readLine();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
