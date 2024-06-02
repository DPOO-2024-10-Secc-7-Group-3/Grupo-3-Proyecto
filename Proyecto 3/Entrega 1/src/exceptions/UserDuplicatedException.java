package exceptions;

@SuppressWarnings("serial")
public class UserDuplicatedException extends Exception{
	
	private String login;

	public UserDuplicatedException(String nLogin) {
		super();
		this.login = nLogin;
	}

	@Override
	public String getMessage() {
		return "El usuario '" + this.login + "' ya existe.";
	}
}
