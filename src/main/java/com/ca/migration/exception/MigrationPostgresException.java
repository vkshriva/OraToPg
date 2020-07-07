package com.ca.migration.exception;

public class MigrationPostgresException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MigrationPostgresException(String msg){
		super(msg);
	}
}
