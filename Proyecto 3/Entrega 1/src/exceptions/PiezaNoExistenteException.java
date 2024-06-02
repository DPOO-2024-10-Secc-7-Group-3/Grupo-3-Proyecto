package exceptions;

@SuppressWarnings("serial")
public class PiezaNoExistenteException extends Exception
{
	private String titulo;

	public PiezaNoExistenteException(String titulo){
        super( );
        this.titulo = titulo;
    }

	@Override
	public String getMessage() {
		return "La pieza de título '" + this.titulo + "' no existe.";
	}
}
