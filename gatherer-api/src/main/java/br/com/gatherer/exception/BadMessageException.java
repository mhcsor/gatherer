package br.com.gatherer.exception;

public class BadMessageException extends RuntimeException {

	private static final long serialVersionUID = 1725488307274816758L;

	public BadMessageException(String message) {
		super(message);
	}

}