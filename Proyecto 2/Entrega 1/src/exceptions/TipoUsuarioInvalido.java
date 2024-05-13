package exceptions;

@SuppressWarnings("serial")
public class TipoUsuarioInvalido extends Exception{

	private String tipo;

	public TipoUsuarioInvalido(String nTipo) {
		super();
		this.tipo = nTipo;
	}

	public String getMessage() {
		return "El tipo de usuario '" + this.tipo + "' no existe.";
	}
}
