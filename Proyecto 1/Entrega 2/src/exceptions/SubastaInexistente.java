package exceptions;

@SuppressWarnings("serial")
public class SubastaInexistente extends Exception
{
	private String titulo;

	public SubastaInexistente(String titulo){
        super( );
        this.titulo = titulo;
    }

	@Override
	public String getMessage() {
		return "No existe una subasta por la pieza '" + this.titulo + "'.";
	}
}
