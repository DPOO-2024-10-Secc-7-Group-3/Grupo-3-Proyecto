package exceptions;

@SuppressWarnings("serial")
public class VentaNoFijaException extends Exception
{
	private String titulo;

	public VentaNoFijaException(String titulo){
        super( );
        this.titulo = titulo;
    }

	@Override
	public String getMessage() {
		return "La pieza de título '" + this.titulo + "' no está en venta de precio fijo.";
	}
}
