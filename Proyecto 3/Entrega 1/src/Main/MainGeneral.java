package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import modelo.usuarios.Usuario;
import persistencia.CentralPersistencia;

public class MainGeneral 
{
	private static Main consola;
	private static final String ARCHIVO = "ensayo";
	
	public static void main(String[] args) {
		CentralPersistencia cp = new CentralPersistencia();
		cp.cargarDatos(ARCHIVO);
		menu();

		cp.guardarDatos(ARCHIVO);
	}
	
	public static void menu()
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
					System.out.print("Ingrese el usuario" + ": ");
					BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
					String usuario = reader1.readLine();

					System.out.print("Ingrese la contraseña" + ": ");
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
					String password = reader2.readLine();

					Usuario user1 = Usuario.iniciarSesion(usuario, password);
					System.out.println("El usuario " + user1.getNombre() + " ha iniciado sesión");
					
					if (user1.getTipo().equals(Usuario.ADMIN))
					{
						consola = new MainAdmin(user1);
					}
					else if (user1.getTipo().equals(Usuario.CAJERO))
					{
						consola = new MainCajero(user1);
					}
					else if (user1.getTipo().equals(Usuario.CLIENTE))
					{
						consola = new MainCliente(user1);
					}
					else if (user1.getTipo().equals(Usuario.OPERADOR))
					{
						consola = new MainOperador(user1);
					}
					else
					{
						throw new Exception("El tipo de usuario "+user1.getTipo()+" no es válido.");
					}
					
					consola.ejecutar();
				}
				else if (input.equals("2"))
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
			menu();
		}
	}
	
	public static void printMenu()
	{
		System.out.println("1) Iniciar sesión.\n2) Salir.\nElija una opción: ");
	}
}
