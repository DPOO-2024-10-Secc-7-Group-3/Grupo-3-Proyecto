package exceptions;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {
	
	private String password;

	public IncorrectPasswordException(String nPassword){
        super( );
        this.password = nPassword;
    }

	@Override
	public String getMessage() {
		return "La contraseña '" + this.password + "' es incorrecta.";
	}
}