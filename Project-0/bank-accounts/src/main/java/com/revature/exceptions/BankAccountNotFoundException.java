package com.revature.exceptions;

public class BankAccountNotFoundException extends Exception {

	public BankAccountNotFoundException() {
		super();
	}

	public BankAccountNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BankAccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankAccountNotFoundException(String message) {
		super(message);
	}

	public BankAccountNotFoundException(Throwable cause) {
		super(cause);
	}

	
	
}
