package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Cliente;
import modelo.usuarios.Operador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Subasta;

public class MainCliente extends Main
{
	public MainCliente(Usuario user)
	{
		super(user);
	}
	
	public void ejecutar() throws Exception
	{
		try
		{
			boolean working = true;
			
			while (working)
			{
				printMenu();
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String input = reader.readLine();
				if (input.equals("1"))
				{
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

							((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
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

							((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
									textura, precio, "pintura");
						} else if (tipo.equalsIgnoreCase("video")) {
							System.out.print("Ingrese la duración del video" + ": ");
							BufferedReader reader8 = new BufferedReader(new InputStreamReader(System.in));
							int duracion = Integer.parseInt(reader8.readLine());
							((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, duracion,
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

							((Cliente) user).crearPieza(titulo, anio, lugar, valorMinimo, valorInicial, ancho, alto,
									resolucion, nTipo, precio, "imagen");
						}

						for (String nTitulo : ((Cliente) user).getActuales()) {
							System.out.println(nTitulo);
						}
					}
				}
				else if (input.equals("2"))
				{
					System.out.print("Ingrese el titulo de la pieza a entregar" + ": ");
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

					((Cliente) user).entregarPieza(titulo, nExhibir, nSubasta, LocalDate.parse(fecha));
				}
				else if (input.equals("3"))
				{
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

					((Cliente) user).comprar(titulo, metodo);

					System.out.println("Compras de " + (((Cliente) user).getNombre()));
					for (String nTitulo : ((Cliente) user).getCompras()) {
						System.out.println(nTitulo);
					}
				}
				else if(input.equals("4"))
				{
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

					Operador operador1 = null;
					Subasta buffer = null;
					
					for (String login:Usuario.logins.keySet())
					{
						Usuario actual = Usuario.logins.get(login);
						
						if (actual instanceof Operador)
						{
							buffer = ((Operador)actual).getSubasta(nTitulo);
						}
						
						if (!(buffer == null))
						{
							operador1 = (Operador) actual;
						}
					}
					
					operador1.ofertarPieza((Cliente) user, oferta, nTitulo, metodo);

					System.out.println("Se oferto con exito");
				}
				else if (input.equals("5"))
				{
					System.out.println("\nEstan activas las subastas de los siguientes objetos:");
					for (String login:Usuario.logins.keySet())
					{
						Usuario actual = Usuario.logins.get(login);
						if (actual instanceof Administrador)
						{
							Inventario inventario = ((Administrador)actual).getInventario();
							for (String titulo:inventario.getAlmacenadas())
							{
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Subasta)
								{
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
							
							for (String titulo:inventario.getExhibidas())
							{
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Subasta)
								{
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
						}
					}
				}
				else if (input.equals("6"))
				{
					System.out.println("\nEstan activas las ventas de los siguientes objetos:");
					for (String login:Usuario.logins.keySet())
					{
						Usuario actual = Usuario.logins.get(login);
						if (actual instanceof Administrador)
						{
							Inventario inventario = ((Administrador)actual).getInventario();
							for (String titulo:inventario.getAlmacenadas())
							{
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Fija)
								{
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
							
							for (String titulo:inventario.getExhibidas())
							{
								Pieza pieza = Pieza.piezas.get(titulo);
								if (pieza.getDisponibilidad() instanceof Fija)
								{
									System.out.println(titulo);
									System.out.println("================================");
								}
							}
						}
					}
				}
				else if (input.equals("7"))
				{
					System.out.print("Ingrese la pieza que desea consultar" + ": ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String titulo = read1.readLine();
					
					HashMap<String,String> info = Usuario.infoPieza(titulo);
					
					for (String key:info.keySet())
					{
						System.out.print(key+": "+info.get(key)+"\n");
					}
				}
				else if (input.equals("8"))
				{
					System.out.println("Artista: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String art = read1.readLine();
					
					HashMap<String,ArrayList<String>> info = Usuario.infoArtista(art);
					
					for (String key:info.keySet())
					{
						System.out.println("Pieza: "+key);
						System.out.println("creacion: "+Pieza.piezas.get(key).getAnio()+"\n");
						System.out.println("ventas: \n");
						for (String infoParcial:info.get(key))
						{
							System.out.println(infoParcial);
						}
					}
				}
				else if(input.equals("9"))
				{
					System.out.println("Nueva contraseña: ");
					BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
					String password = read1.readLine();
					
					user.setPassword(password);
				}
				else if (input.equals("0"))
				{
					working = false;
				}
				else
				{
					System.out.println("\nElija una opción válida.\n");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			ejecutar();
		}
	}
	
	public static void printMenu()
	{
		System.out.println("0) Salir.\n"
				+ "1) Crear pieza.\n"
				+ "2) Entregar pieza.\n"
				+ "3) Comprar pieza.\n"
				+ "4) Ofertar subasta.\n"
				+ "5) Ver subastas activas.\n"
				+ "6) Ver piezas a la venta.\n"
				+ "7) Ver historial de pieza.\n"
				+ "8) Ver historial de artista.\n"
				+ "9) Cambiar contraseña.\n");
	}
}
