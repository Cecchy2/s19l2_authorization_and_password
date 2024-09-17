package dariocecchinato.s19l2_authorization_and_password.controllers;

import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import dariocecchinato.s19l2_authorization_and_password.exceptions.BadRequestException;
import dariocecchinato.s19l2_authorization_and_password.exceptions.NotFoundException;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendentePayloadDTO;
import dariocecchinato.s19l2_authorization_and_password.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendentiService dipendentiService;

            @GetMapping
            @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendente> findAllDipendenti(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "nome")String sortby){
        return this.dipendentiService.findAll(page, size, sortby);
            }

            @GetMapping("/{dipendenteId}")
            @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findById(@PathVariable UUID dipendenteId){
                Dipendente found = this.dipendentiService.findDipendenteById(dipendenteId);
                if (found == null )throw new NotFoundException(dipendenteId);
                return found;
            }
            @PutMapping("/{dipendenteId}")
            @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findByIdAndUpdate(@PathVariable UUID dipendenteId,@RequestBody @Validated DipendentePayloadDTO body, BindingResult validationResult){
                if (validationResult.hasErrors()){
                    String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
                    throw new BadRequestException("Ci sono errore con il payload " + message);
                }else {
                    return this.dipendentiService.findByIdAndUpdate(dipendenteId,body);
                }
            }

            @DeleteMapping("/{dipendenteId}")
            @PreAuthorize("hasAuthority('ADMIN')")
            @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID dipendenteId){this.dipendentiService.findByIdAndDeleteDipendente(dipendenteId);}

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticateDipendente){
        return currentAuthenticateDipendente;
    }

    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticateDipendente, @RequestBody DipendentePayloadDTO body) {
        return this.dipendentiService.findByIdAndUpdate(currentAuthenticateDipendente.getId(), body);
    }


    }




