package Main;

import modelo.usuarios.Usuario;

public abstract class Main 
{
	public Usuario user;
	
	public abstract void ejecutar() throws Exception;
	
	public Main(Usuario user)
	{
		this.user = user;
	}
}
