package dariocecchinato.s19l2_authorization_and_password.controllers;

import dariocecchinato.s19l2_authorization_and_password.exceptions.BadRequestException;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendenteLoginResponseDTO;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendentePayloadDTO;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendenteResponseDTO;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendentiLoginDTO;
import dariocecchinato.s19l2_authorization_and_password.services.AuthorizationsService;
import dariocecchinato.s19l2_authorization_and_password.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authorizations")
public class AuthorizationsController {
    @Autowired
    AuthorizationsService authorizationsService;
    @Autowired
    private DipendentiService dipendentiService;

@PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody DipendentiLoginDTO body){
    return new DipendenteLoginResponseDTO(this.authorizationsService.checkCredenzialiEGeneraToken(body));
}


}
