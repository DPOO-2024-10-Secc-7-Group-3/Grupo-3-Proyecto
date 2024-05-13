package exceptions;

@SuppressWarnings("serial")
public class InsuficienteTiempoConsignacionException extends Exception {
	private String titulo;

	public InsuficienteTiempoConsignacionException(String titulo) {
		super();
		this.titulo = titulo;
	}

	@Override
	public String getMessage() {
		return "La pieza " + this.titulo + " aún no ha terminado su tiempo de consignación.";
	}

}
