package business;

import java.io.Serializable;

public class BookException extends Exception implements Serializable {

	public BookException() {
		super();
	}
	public BookException(String msg) {
		super(msg);
	}
	public BookException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 78723266036027364L;
	
}
