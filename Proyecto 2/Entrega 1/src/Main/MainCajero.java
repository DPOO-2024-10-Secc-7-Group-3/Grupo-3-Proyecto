package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.Inventario;
import modelo.piezas.Pieza;
import modelo.usuarios.Administrador;
import modelo.usuarios.Usuario;
import modelo.ventas.Fija;
import modelo.ventas.Subasta;

public class MainCajero extends Main
{
	public MainCajero(Usuario user)
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
				else if (input.equals("2"))
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
				else if (input.equals("3"))
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
				else if (input.equals("4"))
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
				else if (input.equals("5"))
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
				+ "1) Ver subastas activas.\n"
				+ "2) Ver piezas a la venta.\n"
				+ "3) Ver historial de pieza.\n"
				+ "4) Ver historial de artista.\n"
				+ "5) Cambiar contraseña.\n");
	}
}

