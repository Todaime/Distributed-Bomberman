package bombermandistribue.bomberman.exceptions;

public class BombermanException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BombermanException() {
	}
	
	public BombermanException(String str) {
		super(str);
		
	}
	
	public BombermanException(String str, Throwable cause) {
		super(str, cause);
		
	}
	
	public BombermanException(Throwable cause) {
		super(cause);
		
	}

}
