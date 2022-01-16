package db;

public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Excecao personalizada
	public DbException (String msg) {
		super(msg);
	}
}
