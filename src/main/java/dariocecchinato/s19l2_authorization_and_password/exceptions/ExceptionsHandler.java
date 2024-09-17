package dariocecchinato.s19l2_authorization_and_password.exceptions;


import dariocecchinato.s19l2_authorization_and_password.payloads.ErrorPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayloadDTO badRequest(BadRequestException e){
        return new ErrorPayloadDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorPayloadDTO handleNotFound(NotFoundException ex){
        return new ErrorPayloadDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorPayloadDTO handleGenericErrors(Exception ex){
        ex.printStackTrace();
        return new ErrorPayloadDTO("Problema del server, riprova tra qualche minuto", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnauthorizedException unauthorizedException(UnauthorizedException e){
        return  new UnauthorizedException(e.getMessage());
    }
}
