package bombermandistribue.bomberman.exceptions;

public class LoadLevelException extends BombermanException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LoadLevelException() {
	}
	
	public LoadLevelException(String str) {
		super(str);
		
	}
	
	public LoadLevelException(String str, Throwable cause) {
		super(str, cause);
		
	}
	
	public LoadLevelException(Throwable cause) {
		super(cause);
		
	}
	
}
