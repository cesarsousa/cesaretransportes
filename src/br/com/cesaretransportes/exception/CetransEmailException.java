package br.com.cesaretransportes.exception;

public class CetransEmailException extends Throwable{
	
	private static final long serialVersionUID = 1L;
	
	public CetransEmailException(String s){
		super(s);
	}
	
	public CetransEmailException(Exception e){
		super(e);
	}

}
