package br.com.mercadolivre.url.service.exception;

public class InvalidUrlException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidUrlException(String msg) {
		super(msg);
	}

}
