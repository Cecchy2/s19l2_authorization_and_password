package dariocecchinato.s19l2_authorization_and_password.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
	public NotFoundException(UUID id){
		super("L'elemento con id " + id + " non è stato trovato!");
	}

	public NotFoundException(String username){
		super("L'elemento con username " + username + " non è stato trovato!");
	}
}
