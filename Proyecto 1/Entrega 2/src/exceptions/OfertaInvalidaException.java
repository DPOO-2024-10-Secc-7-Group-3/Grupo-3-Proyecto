package exceptions;

@SuppressWarnings("serial")
public class OfertaInvalidaException extends Exception {

	public OfertaInvalidaException(){
        super( );
    }

	@Override
	public String getMessage() {
		return "La oferta no es válida";
	}
}