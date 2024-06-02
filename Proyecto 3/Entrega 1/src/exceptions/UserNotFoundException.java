package exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	
	private String login;

	public UserNotFoundException(String nLogin) {
		super();
		this.login = nLogin;
	}

	@Override
	public String getMessage() {
		return "El usuario '" + this.login + "' no existe.";
	}
}
