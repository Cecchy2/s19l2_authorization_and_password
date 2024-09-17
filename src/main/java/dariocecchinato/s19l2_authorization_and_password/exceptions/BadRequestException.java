package dariocecchinato.s19l2_authorization_and_password.exceptions;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String msg){
		super(msg);
	}
}
